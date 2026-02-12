package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Analysis;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.DietRecommendation;
import com.fitness.aiservice.model.Recommendations;
import com.fitness.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {

    private final GroqService groqService;

    private final RecommendationRepository recommendationRepository;


//    public Recommendations generateRecommendation(Activity activity){
//        String prompt = createPromptForActivity(activity);
//        String aiResponse = groqService.getAnswer(prompt);
//        log.info("Response from Ai: {} ",aiResponse);
//
//        return processAiResponse(activity, aiResponse);
//    }

    public void generateRecommendation(Activity activity) {

        String prompt = createPromptForActivity(activity);
        String aiResponse = groqService.getAnswer(prompt);

        if (aiResponse == null || aiResponse.isBlank()) {
            throw new RuntimeException("AI returned empty response");
        }

        Recommendations recommendation = processAiResponse(activity, aiResponse);

        if (recommendation == null) {
            throw new RuntimeException("AI response parsing failed");
        }
    }



    public Recommendations processAiResponse(Activity activity, String aiResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(aiResponse);

            JsonNode contentNode = rootNode
                    .path("choices")
                    .path(0)
                    .path("message")
                    .path("content");

            if (!isValid(contentNode)) {
                throw new RuntimeException("AI response missing message.content");
            }

            JsonNode aiJson = mapper.readTree(contentNode.asText());


            String prettyJson = prettyPrintJson(contentNode.asText());
            log.info("AI Recommendation JSON:\n {}", prettyJson);

            Recommendations recommendation =
                    mapToRecommendationEntity(activity, aiJson);

            recommendationRepository.save(recommendation);

            log.info("Recommendation saved for activity {}", activity.getId());



            return recommendation;

        } catch (Exception e) {
            log.error("Error while processing AI response", e);
            return createDefaultRecommendation(activity);
        }
    }

    private String prettyPrintJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object obj = mapper.readValue(json, Object.class);
            return mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Failed to pretty print JSON, logging raw string");
            return json;
        }
    }

    public void saveFallbackRecommendation(Activity activity) {
        Recommendations fallback = createDefaultRecommendation(activity);
        recommendationRepository.save(fallback);

        log.warn("Fallback recommendation saved for activity {}", activity.getId());
    }



   /* private Recommendations createDefaultRecommendation(Activity activity) {

        Analysis analysis = Analysis.builder()
                .overall(
                        "We were unable to generate a detailed AI-based analysis for this activity at the moment. " +
                                "However, completing this activity still contributes positively to your overall fitness and consistency."
                )
                .pace(
                        "Focus on maintaining a comfortable and steady pace that allows you to complete the activity " +
                                "without excessive fatigue. Consistency is more important than speed."
                )
                .heartRate(
                        "Try to stay within a moderate heart rate zone where you can breathe comfortably while exercising. " +
                                "Avoid pushing too hard if you feel dizzy or overly tired."
                )
                .caloriesBurned(
                        "Calorie burn varies based on intensity, duration, and individual fitness levels. " +
                                "Regular activity over time will improve calorie efficiency."
                )
                .build();

        List<String> improvements = List.of(
                "Maintain a regular workout schedule to build long-term endurance",
                "Gradually increase duration or intensity rather than making sudden changes"
        );

        List<String> suggestions = List.of(
                "Plan your next workout based on how your body feels today",
                "Stay consistent rather than focusing on perfect performance",
                "Include rest days to support recovery"
        );

        List<String> safety = List.of(
                "Always warm up before starting your workout",
                "Stay hydrated before, during, and after exercise",
                "Listen to your body and stop if you feel pain or discomfort"
        );

        return Recommendations.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .analysis(analysis)
                .improvements(improvements)
                .suggestions(suggestions)
                .safety(safety)
                .difficultyLevel("Beginner")
                .confidenceScore(0.3) // clearly indicates low confidence / fallback
                .createdAt(LocalDateTime.now())
                .build();
    }

    */

    private Recommendations createDefaultRecommendation(Activity activity) {

        String type = activity.getType() != null
                ? activity.getType().toLowerCase()
                : "general";

        Analysis analysis;

        switch(type) {

            case "walking":
                analysis = Analysis.builder()
                        .overall("Walking improves cardiovascular health and supports endurance building.")
                        .pace("Maintain a steady pace that allows comfortable breathing.")
                        .heartRate("Stay in a moderate heart rate zone for optimal fat burn.")
                        .caloriesBurned("Calorie burn improves with longer duration and brisk pace.")
                        .build();
                break;

            case "running":
                analysis = Analysis.builder()
                        .overall("Running strengthens cardiovascular endurance and leg muscles.")
                        .pace("Avoid sprinting too early; maintain consistent pacing.")
                        .heartRate("Keep heart rate in aerobic zone unless doing interval training.")
                        .caloriesBurned("Running is a high-calorie burn activity depending on intensity.")
                        .build();
                break;

            case "cycling":
                analysis = Analysis.builder()
                        .overall("Cycling builds lower body strength and stamina.")
                        .pace("Maintain consistent cadence for better endurance gains.")
                        .heartRate("Alternate between moderate and high intensity intervals.")
                        .caloriesBurned("Cycling calorie burn depends on terrain and resistance.")
                        .build();
                break;

            default:
                analysis = Analysis.builder()
                        .overall("Regular physical activity supports long-term health and fitness.")
                        .pace("Focus on maintaining consistency in your workout routine.")
                        .heartRate("Exercise within a safe and sustainable intensity level.")
                        .caloriesBurned("Calorie burn improves with consistent effort over time.")
                        .build();
        }

        return Recommendations.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .analysis(analysis)
                .improvements(List.of(
                        "Maintain a consistent workout schedule",
                        "Gradually increase intensity over time"
                ))
                .suggestions(List.of(
                        "Focus on proper warm-up and cool-down",
                        "Stay hydrated and prioritize recovery"
                ))
                .safety(List.of(
                        "Stop immediately if you feel dizziness or pain",
                        "Avoid overexertion beyond your current fitness level"
                ))
                .difficultyLevel("Beginner")
                .confidenceScore(0.3)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public Recommendations mapToRecommendationEntity(Activity activity, JsonNode aiJson) {

        Analysis analysis = hasAnyAnalysis(aiJson)
                ? Analysis.builder()
                .overall(getText(aiJson, "analysis", "overall"))
                .pace(getText(aiJson, "analysis", "pace"))
                .heartRate(getText(aiJson, "analysis", "heartRate"))
                .caloriesBurned(getText(aiJson, "analysis", "caloriesBurned"))
                .build()
                : null;

        List<String> improvements = extractList(
                aiJson.path("improvements"),
                "recommendation"
        );


        // -------- SUGGESTIONS --------
        List<String> suggestions = new ArrayList<>();

        String nextFocus = getText(aiJson, "nextWorkoutFocus");
        if (nextFocus != null) {
            suggestions.add("Focus on improving " + nextFocus.toLowerCase());
        }

        String recoveryTip = getText(aiJson, "recovery", "tip");
        if (recoveryTip != null) {
            suggestions.add(recoveryTip);
        }

        if (suggestions.isEmpty()) {
            suggestions.add("Maintain your current routine and listen to your body.");
        }


        List<String> safety = new ArrayList<>();
        if (aiJson.has("safety") && aiJson.path("safety").isArray()) {
            aiJson.path("safety").forEach(node -> addIfValid(safety, node));
        }

        double confidence = aiJson.path("confidenceScore").asDouble(0.5);
        confidence = Math.min(1.0, Math.max(0.0, confidence));

        List<DietRecommendation> dietRecommendations = new ArrayList<>();

        JsonNode dietArray = aiJson.path("dietRecommendations");
        if (dietArray.isArray()) {
            dietArray.forEach(node -> {
                if (isValid(node.path("basedOnActivity"))) {
                    dietRecommendations.add(
                            DietRecommendation.builder()
                                    .basedOnActivity(node.path("basedOnActivity").asText())
                                    .purpose(getText(node, "purpose"))
                                    .build()
                    );
                }
            });
        }

        return Recommendations.builder()
                .activityId(activity.getId())
                .userId(activity.getUserId())
                .analysis(analysis)
                .improvements(improvements.isEmpty() ? null : improvements)
                .suggestions(suggestions)
                .dietRecommendations(dietRecommendations.isEmpty() ? null : dietRecommendations)
                .safety(safety.isEmpty() ? null : safety)
                .difficultyLevel(getText(aiJson, "difficultyLevel"))
                .confidenceScore(confidence)
                .createdAt(LocalDateTime.now())
                .build();

    }

    private boolean hasAnyAnalysis(JsonNode aiJson) {
        JsonNode analysis = aiJson.path("analysis");
        return isValid(analysis.path("overall")) ||
                isValid(analysis.path("pace")) ||
                isValid(analysis.path("heartRate")) ||
                isValid(analysis.path("caloriesBurned"));
    }


    private boolean isValid(JsonNode node) {
        return node != null
                && !node.isMissingNode()
                && !node.isNull()
                && !node.asText().trim().isEmpty()
                && !"null".equalsIgnoreCase(node.asText().trim());
    }

    private void addIfValid(List<String> list, JsonNode node) {
        if (isValid(node)) {
            list.add(node.asText().trim());
        }
    }

    private String getText(JsonNode root, String... path) {
        JsonNode current = root;
        for (String p : path) {
            current = current.path(p);
        }
        return isValid(current) ? current.asText().trim() : null;
    }

    private List<String> extractList(JsonNode arrayNode, String field) {
        List<String> result = new ArrayList<>();

        if (arrayNode != null && arrayNode.isArray()) {
            for (JsonNode node : arrayNode) {
                JsonNode value = field == null ? node : node.path(field);
                if (isValid(value)) {
                    result.add(value.asText().trim());
                }
            }
        }
        return result.isEmpty() ? null : result;
    }


    private String createPromptForActivity(Activity activity) {

        return String.format("""
    You are an expert fitness coach, performance analyst, and nutrition advisor.

    Analyze the following fitness activity and generate structured, actionable recommendations.
    The output will be consumed by a backend service and rendered in a frontend UI.

    Activity Details:
    - Activity Type: %s
    - Duration (minutes): %d
    - Calories Burned: %d
    - Additional Metrics: %s

    Respond ONLY with a valid JSON object.
    Do NOT include markdown, explanations, or extra text.
    Do NOT add fields outside the schema below.

    JSON SCHEMA (field meaning is important):

    {
      "analysis": {
        "overall": "High-level performance summary of the activity",
        "pace": "Insights about pace, intensity, or consistency (if applicable)",
        "heartRate": "Heart rate or exertion-related insights (if applicable)",
        "caloriesBurned": "Interpretation of calories burned relative to duration and activity type"
      },

      "improvements": [
        {
          "area": "Specific area that can be improved (e.g., endurance, pace, consistency)",
          "recommendation": "Clear and actionable improvement advice"
        }
      ],

      "nextWorkoutFocus": "Primary focus area for the user's next workout",

      "recovery": {
        "tip": "General recovery or rest guidance after this activity"
      },

      "dietRecommendations": [
        {
            "basedOnActivity": "Diet advice tailored to this activity",
            "purpose": "Why this diet supports recovery or performance"
        }
      ]
                          ,

      "safety": [
        "Optional general safety or precautionary advice related to this activity"
      ],

      "difficultyLevel": "Beginner | Intermediate | Advanced",

      "confidenceScore": "A numeric value between 0.0 and 1.0 indicating confidence in the recommendations"
    }

    RULES:
    - Populate all main sections with meaningful content.
    - Include safety tips ONLY if relevant; otherwise return an empty array.
    - Avoid medical diagnoses or emergency advice.
    - Keep language clear, practical, and user-friendly.
    - Provide one or more diet recommendations when relevant; If none are relevant, return an empty array.

    """,
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMetrics() != null
                        ? activity.getAdditionalMetrics()
                        : "None"
        );
    }
}
