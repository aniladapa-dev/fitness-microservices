import { Box, Typography } from "@mui/material";

const TodayStatsCard = ({ activities, calories }) => {
  return (
    <Box
    sx={{
      px: 3,
      py: 2,
      borderRadius: 2,
      border: "1px solid",
      borderColor: "divider",
      backgroundColor: "background.paper",
      minWidth: 220,
      minHeight: 160,           
      display: "flex",          
      flexDirection: "column",
      justifyContent: "center",
      textAlign: "left",
      boxShadow: (theme) =>
        theme.palette.mode === "dark"
          ? "0 8px 24px rgba(0,0,0,0.35)"
          : "0 6px 18px rgba(0,0,0,0.08)",
    }}
  >

      <Typography variant="subtitle2" sx={{ mb: 1 }}>
        Today
      </Typography>

      <Typography variant="body2" color="text.secondary">
        • Activities logged: <strong>{activities}</strong>
      </Typography>

      <Typography variant="body2" color="text.secondary">
        • Calories burned: <strong>{calories} kcal</strong>
      </Typography>
    </Box>
  );
};

export default TodayStatsCard;
