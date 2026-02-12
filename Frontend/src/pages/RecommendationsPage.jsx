import { Box, Typography, CircularProgress } from "@mui/material";
import { useEffect, useState } from "react";
import { getUserRecommendations } from "../services/api";
import RecommendationCard from "../components/RecommendationCard";
import { useContext } from "react";
import { AuthContext } from "react-oauth2-code-pkce";



const RecommendationsPage = () => {
  const [recommendations, setRecommendations] = useState([]);
  const [loading, setLoading] = useState(true);

  const { token } = useContext(AuthContext);

  if (!token) return null;

    // decode JWT payload
  const payload = JSON.parse(atob(token.split(".")[1]));
  const userId = payload.sub;
  console.log(userId);
  
  useEffect(() => {
    const fetchRecommendations = async () => {
      try {
        const res = await getUserRecommendations(userId);

        // sort by createdAt (latest first)
        const sorted = res.data.sort(
          (a, b) => new Date(b.createdAt) - new Date(a.createdAt)
        );

        setRecommendations(sorted);
      } catch (err) {
        console.error("Failed to load recommendations", err);
      } finally {
        setLoading(false);
      }
    };

    fetchRecommendations();
  }, [userId]);

  if (loading) {
    return (
      <Box sx={{ p: 4, textAlign: "center" }}>
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Box sx={{ p: 4 }}>
      <Typography variant="h5" sx={{ mb: 3 }}>
        Your AI Recommendations
      </Typography>

      {recommendations.length === 0 ? (
        <Typography>No recommendations available yet.</Typography>
      ) : (
        recommendations.map((rec) => (
          <RecommendationCard
            key={rec.id}
            recommendation={rec}
          />
        ))
      )}
    </Box>
  );
};

export default RecommendationsPage;
