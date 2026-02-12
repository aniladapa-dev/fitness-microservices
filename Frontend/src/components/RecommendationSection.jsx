import { Box, Typography, Divider } from "@mui/material";

const RecommendationSection = ({ title, children }) => {
  return (
    <Box sx={{ mb: 4 }}>
      <Typography
        variant="h6"
        sx={{ mb: 1, fontWeight: "bold" }}
      >
        {title}
      </Typography>
      <Divider sx={{ mb: 2 }} />
      {children}
    </Box>
  );
};

export default RecommendationSection;
