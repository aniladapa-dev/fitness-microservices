package com.fitness.gateway;

import com.fitness.gateway.user.KeycloakSyncRequest;
import com.fitness.gateway.user.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakSyncFilter implements WebFilter {

    private final UserService userService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        KeycloakSyncRequest syncRequest = extractFromToken(authHeader);
        if (syncRequest == null) {
            return chain.filter(exchange);
        }

        String keycloakId = syncRequest.getKeycloakId();

        return userService.validateUser(keycloakId)
                .flatMap(exists -> {
                    if (!exists) {
                        return userService.syncUser(syncRequest).then();
                    }
                    return Mono.empty();
                })
                .then(Mono.defer(() -> {
                    ServerHttpRequest mutatedRequest = exchange.getRequest()
                            .mutate()
                            .header("X-USER-ID", keycloakId)
                            .build();

                    return chain.filter(
                            exchange.mutate().request(mutatedRequest).build()
                    );
                }));
    }

    private KeycloakSyncRequest extractFromToken(String token) {
        try {
            String jwt = token.replace("Bearer ", "");
            SignedJWT signedJWT = SignedJWT.parse(jwt);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            KeycloakSyncRequest req = new KeycloakSyncRequest();
            req.setKeycloakId(claims.getSubject()); // sub
            req.setEmail(claims.getClaimAsString("email"));
            req.setFirstName(claims.getClaimAsString("given_name"));
            req.setLastName(claims.getClaimAsString("family_name"));

            return req;
        } catch (Exception e) {
            log.error("Failed to parse JWT", e);
            return null;
        }
    }
}
