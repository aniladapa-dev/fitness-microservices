import {
  Card,
  CardContent,
  Typography,
  Box,
  IconButton
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import { useNavigate } from "react-router-dom";
import { formatDateTime } from "../utils/dateUtils";

const ActivityCard = ({ activity, onDelete }) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/activities/${activity.id}`);
  };

  const handleDelete = (e) => {
    e.stopPropagation(); // Prevent card navigation
    onDelete(activity.id);
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
        <Box display="flex" justifyContent="space-between">
          <Typography variant="h6">
            {activity.type}
          </Typography>

          <IconButton
            color="error"
            onClick={handleDelete}
          >
            <DeleteIcon />
          </IconButton>
        </Box>

        <Box sx={{ display: "flex", gap: 3, mt: 1 }}>
          <Typography variant="body2">
            Duration: {activity.duration} min
          </Typography>

          <Typography variant="body2">
            Calories: {activity.caloriesBurned}
          </Typography>
        </Box>

        <Typography
          variant="body2"
          sx={{ mt: 1, color: "text.secondary" }}
        >
          {formatDateTime(activity.start)}
        </Typography>
      </CardContent>
    </Card>
  );
};

export default ActivityCard;
