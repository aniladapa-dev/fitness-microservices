export const authConfig = {
  clientId: import.meta.env.VITE_KEYCLOAK_CLIENT_ID,

  authorizationEndpoint:
    `${import.meta.env.VITE_KEYCLOAK_URL}/realms/${import.meta.env.VITE_KEYCLOAK_REALM}/protocol/openid-connect/auth`,

  tokenEndpoint:
    `${import.meta.env.VITE_KEYCLOAK_URL}/realms/${import.meta.env.VITE_KEYCLOAK_REALM}/protocol/openid-connect/token`,

  redirectUri: import.meta.env.VITE_REDIRECT_URI,

  scope: "openid profile email offline_access",
};
