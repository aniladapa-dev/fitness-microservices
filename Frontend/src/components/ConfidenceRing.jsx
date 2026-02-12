import { Box, CircularProgress, Typography } from "@mui/material";

const getRingColor = (value) => {
    if (value < 30) return "#ef4444";   // soft red
    if (value < 70) return "#3b82f6";   // blue
    return "#22c55e";                   // green
  };
  
const ConfidenceRing = ({ value = 0 }) => {
  const color = getRingColor(value);

  return (
    <Box
    sx={{
      position: "relative",
      display: "inline-flex",
      alignItems: "center",
      justifyContent: "center",
      height: 160,   
    }}
  >

      {/* Background ring */}
      <CircularProgress
        variant="determinate"
        value={100}
        size={140}
        thickness={4}
        sx={{
          color: (theme) =>
            theme.palette.mode === "dark"
              ? "rgba(255,255,255,0.12)"
              : "rgba(0,0,0,0.08)",
        }}
      />

      {/* Foreground animated ring */}
      <CircularProgress
        variant="determinate"
        value={value}
        size={140}
        thickness={4}
        sx={{
          color,
          position: "absolute",
          left: 0,
          boxShadow: `0 0 20px ${color}`,
          transition: "all 0.6s ease",
        }}
      />

      {/* Center text */}
      <Box
        sx={{
          position: "absolute",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
        <Typography variant="h4" fontWeight="bold">
          {Math.round(value)}%
        </Typography>
        <Typography variant="caption" color="text.secondary">
          Confidence
        </Typography>
      </Box>
    </Box>
  );
};

export default ConfidenceRing;
