import { Box, Typography } from "@mui/material";

const Footer = () => {
  return (
    <Box
      sx={{
        py: 2,
        textAlign: "center",
        borderTop: "1px solid",
        borderColor: "divider",
        backgroundColor: "background.default",
      }}
    >
      <Typography variant="body2" color="text.secondary">
        Fitness AI
      </Typography>
      <Typography variant="caption" color="text.secondary">
        Smarter insights. Healthier habits.
      </Typography>
      <Typography variant="caption" color="text.secondary" display="block">
        © 2026 Fitness AI. All rights reserved.
      </Typography>
    </Box>
  );
};

export default Footer;
