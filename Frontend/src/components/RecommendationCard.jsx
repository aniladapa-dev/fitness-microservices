import { Card, CardContent, Typography, Box } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { formatDateTime } from "../utils/dateUtils";

const RecommendationCard = ({ recommendation }) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/activities/${recommendation.activityId}`);
  };

  return (
    <Card
      sx={{
        mb: 2,
        cursor: "pointer",
        "&:hover": { boxShadow: 6 }
      }}
      onClick={handleClick}
    >
      <CardContent>
        <Typography variant="subtitle1" sx={{ mb: 1 }}>
          {recommendation.analysis?.overall}
        </Typography>

        <Box sx={{ display: "flex", gap: 3 }}>
          <Typography variant="body2">
            Difficulty: {recommendation.difficultyLevel}
          </Typography>

          <Typography variant="body2">
            Confidence: {Math.round(recommendation.confidenceScore * 100)}%
          </Typography>
        </Box>

        <Typography
          variant="body2"
          sx={{ mt: 1, color: "text.secondary" }}
        >
          Generated at: {formatDateTime(recommendation.createdAt)}
        </Typography>
      </CardContent>
    </Card>
  );
};

export default RecommendationCard;
