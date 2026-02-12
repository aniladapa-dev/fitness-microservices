import { Box, Typography, CircularProgress, Divider } from "@mui/material";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
  getActivityById,
  getRecommendationByActivityId
} from "../services/api";
import { formatDateTime } from "../utils/dateUtils";
import RecommendationSection from "../components/RecommendationSection";

const analysisLabelMap = {
  overall: "Overall",
  pace: "Pace",
  heartRate: "Heart Rate",
  caloriesBurned: "Calories Burned",
};

const ActivityDetailPage = () => {
  const { id } = useParams();

  const [activity, setActivity] = useState(null);
  const [recommendation, setRecommendation] = useState(null);

  const [loadingActivity, setLoadingActivity] = useState(true);
  const [loadingRecommendation, setLoadingRecommendation] = useState(true);

  useEffect(() => {
    const fetchActivity = async () => {
      try {
        const res = await getActivityById(id);
        setActivity(res.data);
      } catch (err) {
        console.error("Failed to load activity", err);
      } finally {
        setLoadingActivity(false);
      }
    };

    fetchActivity();
  }, [id]);

  useEffect(() => {
    const fetchRecommendation = async () => {
      try {
        const res = await getRecommendationByActivityId(id);
        setRecommendation(res.data);
      } catch (err) {
        if (err.response?.status === 404) {
          setRecommendation(null);
        } else {
          console.error("Failed to load recommendation", err);
        }
      } finally {
        setLoadingRecommendation(false);
      }
    };

    fetchRecommendation();
  }, [id]);

  if (loadingActivity) {
    return (
      <Box sx={{ p: 4, textAlign: "center" }}>
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Box sx={{ p: 4, maxWidth: 900, mx: "auto" }}>
      <Typography variant="h5">{activity.type}</Typography>

      <Typography sx={{ mt: 1 }}>
        Duration: {activity.duration} min
      </Typography>

      <Typography>
        Calories Burned: {activity.caloriesBurned}
      </Typography>

      <Typography sx={{ color: "text.secondary" }}>
        Started at: {formatDateTime(activity.start)}
      </Typography>

      <Divider sx={{ my: 3 }} />

      {loadingRecommendation ? (
        <CircularProgress />
      ) : recommendation ? (
        <Box>
          <RecommendationSection title="AI Fitness Analysis">
            {Object.entries(recommendation.analysis || {}).map(
              ([key, value]) => (
                <Typography key={key} sx={{ mb: 1 }}>
                  <strong>{analysisLabelMap[key] || key}:</strong> {value}
                </Typography>
              )
            )}
          </RecommendationSection>

          <RecommendationSection title="Improvements">
            {recommendation.improvements?.map((item, index) => (
              <Typography key={index} sx={{ mb: 1 }}>
                • {item}
              </Typography>
            ))}
          </RecommendationSection>

          <RecommendationSection title="Suggestions">
            {recommendation.suggestions?.map((item, index) => (
              <Typography key={index} sx={{ mb: 1 }}>
                • {item}
              </Typography>
            ))}
          </RecommendationSection>

          <RecommendationSection title="Diet Recommendations">
            {recommendation.dietRecommendations?.map((diet, index) => (
              <Box key={index} sx={{ mb: 2 }}>
                <Typography>
                  <strong>Advice:</strong> {diet.basedOnActivity}
                </Typography>
                <Typography color="text.secondary">
                  {diet.purpose}
                </Typography>
              </Box>
            ))}
          </RecommendationSection>

          <RecommendationSection title="Safety Tips">
            {recommendation.safety?.map((tip, index) => (
              <Typography key={index} sx={{ mb: 1 }}>
                • {tip}
              </Typography>
            ))}
          </RecommendationSection>

          <RecommendationSection title="Summary">
            <Typography>
              Difficulty Level:{" "}
              <strong>{recommendation.difficultyLevel}</strong>
            </Typography>
            <Typography>
              Confidence Score:{" "}
              <strong>
                {Math.round(recommendation.confidenceScore * 100)}%
              </strong>
            </Typography>
            <Typography sx={{ color: "text.secondary" }}>
              Generated at: {formatDateTime(recommendation.createdAt)}
            </Typography>
          </RecommendationSection>
        </Box>
      ) : (
        <Typography color="text.secondary">
          AI recommendation is being generated. Please check back later.
        </Typography>
      )}
    </Box>
  );
};

export default ActivityDetailPage;
