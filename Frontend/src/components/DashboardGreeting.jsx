import { Box, Typography, Fade } from "@mui/material";
import { useEffect, useState } from "react";
import AnimatedText from "./AnimatedText";

const DashboardGreeting = ({ userName }) => {
  const [showSubtitle, setShowSubtitle] = useState(false);

  useEffect(() => {
    const t = setTimeout(() => setShowSubtitle(true), 800);
    return () => clearTimeout(t);
  }, []);

  const getGreeting = () => {
    const hour = new Date().getHours(); // local browser time

    if (hour >= 5 && hour < 12) return "Good Morning";
    if (hour >= 12 && hour < 17) return "Good Afternoon";
    if (hour >= 17 && hour < 21) return "Good Evening";
    return "Good Night";
  };

  return (
    <Box sx={{ textAlign: "center" }}>
      <AnimatedText
        text={`${getGreeting()}, ${userName}`}
        variant="h3"
      />

      <Fade in={showSubtitle}>
        <Typography
          variant="subtitle1"
          color="text.secondary"
          sx={{ mt: 1 }}
        >
          Welcome back!!
        </Typography>
      </Fade>
    </Box>
  );
};

export default DashboardGreeting;
