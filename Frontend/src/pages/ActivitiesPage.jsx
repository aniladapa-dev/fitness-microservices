import { Box, Typography, CircularProgress } from "@mui/material";
import { useEffect, useState } from "react";
import { getActivities, deleteActivity, deleteRecommendation } from "../services/api";
import ActivityCard from "../components/ActivityCard";

const ActivitiesPage = () => {
  const [activities, setActivities] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);


  const fetchActivities = async () => {
    try {
      const response = await getActivities();
      setActivities(response.data);
    } catch (err) {
      setError("Failed to load activities");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchActivities();
  }, []);

  const handleDeleteActivity = async (activityId) => {
    try {
      await deleteActivity(activityId);
      await deleteRecommendation(activityId);

      // Option 1: Refetch
      // await fetchActivities();

      // ✅ Option 2 (Better UX – Instant Update)
      setActivities(prev =>
        prev.filter(activity => activity.id !== activityId)
      );

    } catch (err) {
      console.error("Failed to delete activity", err);
    }
  };

  if (loading) {
    return (
      <Box sx={{ p: 4, textAlign: "center" }}>
        <CircularProgress />
      </Box>
    );
  }

  if (error) {
    return (
      <Box sx={{ p: 4 }}>
        <Typography color="error">{error}</Typography>
      </Box>
    );
  }

  return (
    <Box sx={{ p: 4 }}>
      <Typography variant="h5" sx={{ mb: 3 }}>
        Your Activities
      </Typography>

      {activities.length === 0 ? (
        <Typography>No activities found. Add one!</Typography>
      ) : (
        activities.map((activity) => (
          <ActivityCard
            key={activity.id}
            activity={activity}
            onDelete={handleDeleteActivity}
          />
        ))
      )}
    </Box>
  );
};

export default ActivitiesPage;
