import {
  Box,
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
  TextField,
  Typography
} from "@mui/material";
import { useState } from "react";
import { addActivity } from "../services/api";
import { ACTIVITY_TYPES } from "../constants/activityTypes";
import { DateTimePicker } from "@mui/x-date-pickers/DateTimePicker";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import dayjs from "dayjs";




const ActivityForm = ({ onActivityAdded }) => {
  const [activity, setActivity] = useState({
    type: "RUNNING",
    duration: "",
    caloriesBurned: "",
    startTime: "",
    additionalMetrics: {
      distance: "",
      averageSpeed: "",
      maxHeartRate: ""
    }
  });

  // Handles top-level fields
  const handleChange = (field, value) => {
    setActivity((prev) => ({
      ...prev,
      [field]: value
    }));
  };

  // Handles nested metrics map
  const handleMetricChange = (field, value) => {
    setActivity((prev) => ({
      ...prev,
      additionalMetrics: {
        ...prev.additionalMetrics,
        [field]: value
      }
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Build payload EXACTLY as backend expects
    const payload = {
      type: activity.type,
      duration: Number(activity.duration),
      caloriesBurned: Number(activity.caloriesBurned),
      startTime: activity.startTime,
      additionalMetrics: {
        distance: Number(activity.additionalMetrics.distance),
        averageSpeed: Number(activity.additionalMetrics.averageSpeed),
        maxHeartRate: Number(activity.additionalMetrics.maxHeartRate)
      }
    };

    try {
      await addActivity(payload);

      // reset form
      setActivity({
        type: "RUNNING",
        duration: "",
        caloriesBurned: "",
        startTime: "",
        additionalMetrics: {
          distance: "",
          averageSpeed: "",
          maxHeartRate: ""
        }
      });

      if (onActivityAdded) onActivityAdded();
    } catch (error) {
      console.error("Error adding activity", error);
    }
  };

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ mb: 4 }}>
      <Typography variant="h6" sx={{ mb: 2 }}>
        Add Activity
      </Typography>

      {/* Activity Type */}
      <FormControl fullWidth required sx={{ mb: 2 }}>
        <InputLabel>Activity Type</InputLabel>
        <Select
          label="Activity Type"
          value={activity.type}
          onChange={(e) => handleChange("type", e.target.value)}
        >
          {ACTIVITY_TYPES.map((type) => (
            <MenuItem key={type.value} value={type.value}>
              {type.label}
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      {/* Duration */}
      <TextField
        fullWidth
        required
        label="Duration (minutes)"
        type="number"
        sx={{ mb: 2 }}
        value={activity.duration}
        onChange={(e) => handleChange("duration", e.target.value)}
      />

      {/* Calories */}
      <TextField
        fullWidth
        label="Calories Burned"
        type="number"
        sx={{ mb: 2 }}
        value={activity.caloriesBurned}
        onChange={(e) => handleChange("caloriesBurned", e.target.value)}
      />

      {/* Start Time */}
     <LocalizationProvider dateAdapter={AdapterDayjs}>
      <DateTimePicker
        label="Start Time"
        value={activity.startTime ? dayjs(activity.startTime) : null}
        onChange={(newValue) => {
          if (newValue) {
            handleChange("startTime", newValue.toISOString());
          }
        }}
        closeOnSelect
        slotProps={{
          textField: {
            fullWidth: true,
            required: true,
            sx: { mb: 2 },
          },
          actionBar: {
            actions: [],   // removes OK / Cancel buttons completely
          },
        }}
      />
    </LocalizationProvider>



      <Typography variant="subtitle1" sx={{ mt: 2, mb: 1 }}>
        Additional Metrics
      </Typography>

      {/* Metrics */}
      <TextField
        fullWidth
        label="Distance (km)"
        type="number"
        sx={{ mb: 2 }}
        value={activity.additionalMetrics.distance}
        onChange={(e) => handleMetricChange("distance", e.target.value)}
      />

      <TextField
        fullWidth
        label="Average Speed"
        type="number"
        sx={{ mb: 2 }}
        value={activity.additionalMetrics.averageSpeed}
        onChange={(e) => handleMetricChange("averageSpeed", e.target.value)}
      />

      <TextField
        fullWidth
        label="Max Heart Rate"
        type="number"
        sx={{ mb: 2 }}
        value={activity.additionalMetrics.maxHeartRate}
        onChange={(e) => handleMetricChange("maxHeartRate", e.target.value)}
      />

      <Button type="submit" variant="contained">
        Add Activity
      </Button>
    </Box>
  );
};

export default ActivityForm;
