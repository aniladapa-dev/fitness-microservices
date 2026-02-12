import { Box, Typography } from "@mui/material";

const FeatureCard = ({ title, description }) => {
  return (
    <Box
      sx={{
        width: "100%",
        maxWidth: 520,          
        p: 3,
        borderRadius: 2,
        textAlign: "center",

        backgroundColor: (theme) =>
          theme.palette.mode === "dark"
            ? "rgba(255,255,255,0.06)"
            : "#ffffff",

        border: "1px solid",
        borderColor: "divider",

        boxShadow: (theme) =>
          theme.palette.mode === "dark"
            ? "none"
            : "0 4px 12px rgba(0,0,0,0.06)",
      }}
    >
      <Typography variant="h6" sx={{ mb: 1 }}>
        {title}
      </Typography>

      <Typography variant="body2" color="text.secondary">
        {description}
      </Typography>
    </Box>
  );
};

export default FeatureCard;
