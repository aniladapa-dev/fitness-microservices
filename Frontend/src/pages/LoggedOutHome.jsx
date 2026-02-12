import { Box, Typography, Button, Container, Grid } from "@mui/material";
import { useContext } from "react";
import { AuthContext } from "react-oauth2-code-pkce";
import FeatureCard from "../components/FeatureCard";

const LoggedOutHome = () => {
  const { logIn } = useContext(AuthContext);

  const signupUrl =
  "http://localhost:8181/realms/fitness-oauth2/protocol/openid-connect/auth" +
  "?response_type=code" +
  "&client_id=oauth2-pkce-client" +
  "&redirect_uri=http://localhost:5173" +
  "&scope=openid profile email" +
  "&kc_action=register";


  return (
    <Box sx={{ py: 8 }}>
      <Container maxWidth="lg">
        {/* Heading */}
        <Typography variant="h3" fontWeight="bold" align="center">
          Fitness, powered by <span style={{ color: "#3b82f6" }}>AI</span>
        </Typography>

        <Typography
          color="text.secondary"
          sx={{ mt: 2, mb: 6, textAlign: "center" }}
        >
          Track your activities, set daily goals, and receive intelligent fitness
          insights tailored just for you.
        </Typography>

        <Grid
          container
          spacing={4}
          justifyContent="center"
        >
          {[
            {
              title: "AI-Powered Fitness Insights",
              desc: "Get intelligent insights generated from your activities, progress, and fitness patterns.",
            },
            {
              title: "Smart Activity Tracking",
              desc: "Log workouts like walking, running, yoga, or cycling and track them over time.",
            },
            {
              title: "Daily Confidence Score",
              desc: "Understand how consistent and effective your day was with an AI-calculated fitness confidence score.",
            },
            {
              title: "Actionable Workout Recommendations",
              desc: "Receive specific, activity-based workout recommendations to improve performance and consistency.",
            },
            {
              title: "Personalized Diet Recommendations",
              desc: "Get AI-suggested nutrition guidance tailored to your workouts, intensity, and recovery needs.",
            },
            {
              title: "Recovery & Safety Guidance",
              desc: "Avoid overtraining with AI-driven recovery suggestions and safety tips based on your workload.",
            },
          ].map((item, index) => (
            <Grid
              key={index}
              item
              xs={12}
              md={6}              
              display="flex"
              justifyContent="center"
            >
              {/* SAME CARD, SAME SIZE */}
              <FeatureCard
                title={item.title}
                description={item.desc}
              />
            </Grid>
          ))}
        </Grid>

        {/* CTA */}
    
      <Box sx={{ textAlign: "center", mt: 6 }}>
        <Button
          variant="contained"
          size="large"
          sx={{ px: 6, mr: 2 }}
          onClick={() => logIn()}
        >
          Login
        </Button>

        <Button
          variant="outlined"
          size="large"
          sx={{ px: 6 }}
          onClick={() => (window.location.href = signupUrl)}
        >
          Sign Up
        </Button>
      </Box>

      </Container>
    </Box>
  );
};

export default LoggedOutHome;
