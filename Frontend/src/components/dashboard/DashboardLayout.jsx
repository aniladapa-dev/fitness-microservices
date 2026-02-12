import { Box } from "@mui/material";

const DashboardLayout = ({
  greeting,
  confidence,
  todayStats,
  aiInsight,
  cta,
  dailyTargets,
}) => {
  return (
    <Box
      sx={{
        minHeight: "calc(100vh - 64px)",
        px: { xs: 2, md: 6 },
        py: { xs: 4, md: 6 },
      }}
    >
      {/* Greeting */}
      <Box sx={{ mb: 6 }}>
        {greeting}
      </Box>

      {/* Confidence + Today Stats */}
    <Box
    sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",   
        gap: { xs: 3, md: 5 },
        flexWrap: "wrap",
        mb: 6,
    }}
    >
  {confidence}
  {todayStats}
</Box>



      {/* AI Insight */}
      <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          mb: 6,
        }}
      >
        {aiInsight}
      </Box>

      {/* CTA */}
      <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          mb: 6,
        }}
      >
        {cta}
      </Box>

      {/* Daily Targets */}
      <Box
        sx={{
          maxWidth: "70%",
          mx: "auto",
          mb: 4,
        }}
      >
        {dailyTargets}
      </Box>
    </Box>
  );
};

export default DashboardLayout;
