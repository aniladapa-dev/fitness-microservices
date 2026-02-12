import {
  Box,
  Typography,
  Checkbox,
  IconButton,
  TextField,
  Button,
  Select,
  MenuItem,
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import { useState } from "react";
import { ACTIVITY_TYPES } from "../constants/activityTypes";

const DailyTargets = ({
  targets,
  onToggle,
  onAdd,
  onRemove,
  onReset,
}) => {
  const [selectedActivity, setSelectedActivity] = useState("");
  const [customTarget, setCustomTarget] = useState("");

  const handleAdd = () => {
  let label = "";

  if (selectedActivity === "OTHER") {
    label = customTarget.trim();
  } else {
    const activity = ACTIVITY_TYPES.find(
      (a) => a.value === selectedActivity
    );
    label = activity?.label || "";
  }

  if (!label) return;

  onAdd({
    activityType: selectedActivity, // REAL ENUM VALUE
    label,
  });

  setSelectedActivity("");
  setCustomTarget("");
};


  return (
    <Box
      sx={{
        mt: 3,
        px: 3,
        py: 2,
        borderRadius: 2,
        border: "1px solid",
        borderColor: "divider",
        backgroundColor: "background.paper",
        minWidth: 320,
      }}
    >
      <Typography variant="subtitle1" sx={{ mb: 2 }}>
        Daily Targets
      </Typography>

      {/* Add Target */}
      <Box sx={{ display: "flex", gap: 1, mb: 2 }}>
        <Select
          size="small"
          value={selectedActivity}
          onChange={(e) => setSelectedActivity(e.target.value)}
          displayEmpty
          fullWidth
        >
          <MenuItem value="">
            <em>Select activity</em>
          </MenuItem>

          {ACTIVITY_TYPES.map((activity) => (
            <MenuItem key={activity.value} value={activity.value}>
              {activity.label}
            </MenuItem>
          ))}
        </Select>

        <Button variant="contained" onClick={handleAdd}>
          Add
        </Button>
      </Box>

      {/* Manual input only if OTHER */}
      {selectedActivity === "OTHER" && (
        <TextField
          size="small"
          placeholder="Enter custom target"
          value={customTarget}
          onChange={(e) => setCustomTarget(e.target.value)}
          fullWidth
          sx={{ mb: 2 }}
        />
      )}

      {/* Targets List */}
      {targets.length === 0 ? (
        <Typography variant="body2" color="text.secondary">
          No targets added for today.
        </Typography>
      ) : (
        targets.map((target) => (
          <Box
            key={target.id}
            sx={{
              display: "flex",
              alignItems: "center",
              gap: 1,
            }}
          >
            <Checkbox
              checked={target.completed}
              disabled={target.completed}   // key line
              onChange={() => {
                if (!target.completed) {
                  onToggle(target.id);
                }
              }}
            />

            <Typography
              sx={{
              flexGrow: 1,
              opacity: target.completed ? 0.6 : 1,
            }}
            >
              {target.label}
            </Typography>

            <IconButton
              size="small"
              onClick={() => onRemove(target.id)}
            >
              <DeleteIcon fontSize="small" />
            </IconButton>
          </Box>
        ))
      )}

      {/* Reset Button */}
      {targets.length > 0 && (
        <Button
          color="error"
          size="small"
          sx={{ mt: 2 }}
          onClick={onReset}
        >
          Reset Today
        </Button>
      )}
    </Box>
  );
};

export default DailyTargets;
