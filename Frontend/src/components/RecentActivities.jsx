import { Box, Typography, CircularProgress, Divider } from "@mui/material";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getActivities } from "../services/api";
import { formatDateTime } from "../utils/dateUtils";

const MAX_ITEMS = 3;

const RecentActivities = () => {
  const [activities, setActivities] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchRecent = async () => {
      try {
        const res = await getActivities();

        // sort by start time (latest first)
        const sorted = [...res.data].sort(
          (a, b) => new Date(b.start) - new Date(a.start)
        );

        setActivities(sorted.slice(0, MAX_ITEMS));
      } catch (err) {
        console.error("Failed to load recent activities", err);
      } finally {
        setLoading(false);
      }
    };

    fetchRecent();
  }, []);

  if (loading) {
    return (
      <Box sx={{ textAlign: "center", py: 2 }}>
        <CircularProgress size={24} />
      </Box>
    );
  }

  return (
    <Box sx={{ mt: 5 }}>
      <Typography variant="h6" sx={{ mb: 2 }}>
        Recent Activities
      </Typography>

      {activities.length === 0 ? (
        <Typography color="text.secondary">
          No activities logged yet.
        </Typography>
      ) : (
        <Box>
          {activities.map((activity) => (
            <Box key={activity.id} sx={{ mb: 1.5 }}>
              <Box
                sx={{
                  px: 2,
                  py: 1.5,
                  borderRadius: 2,
                  border: "1px solid",
                  borderColor: "divider",
                  backgroundColor: "background.paper",
                }}
              >
                <Typography fontWeight="medium">
                  {activity.type}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  {activity.duration} min •{" "}
                  {formatDateTime(activity.start)}
                </Typography>
              </Box>
            </Box>
          ))}
        </Box>
      )}

      <Divider sx={{ my: 2 }} />

      <Typography
        variant="body2"
        sx={{
          cursor: "pointer",
          color: "primary.main",
          textAlign: "right",
        }}
        onClick={() => navigate("/activities")}
      >
        View all activities →
      </Typography>
    </Box>
  );
};

export default RecentActivities;
