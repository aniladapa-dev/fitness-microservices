import { Box, Button, Typography } from "@mui/material";
import { useContext, useState, useEffect, useCallback } from "react";
import { AuthContext } from "react-oauth2-code-pkce";
import { useNavigate } from "react-router-dom";

import DashboardGreeting from "../components/DashboardGreeting";
import DailyTargets from "../components/DailyTargets";
import DashboardLayout from "../components/dashboard/DashboardLayout";
import TodayStatsCard from "../components/dashboard/TodayStatsCard";
import ConfidenceRing from "../components/ConfidenceRing";
import RecentActivities from "../components/RecentActivities";

import {
  getDailyTargets,
  addDailyTarget,
  toggleDailyTarget,
  resetDailyTargets,
  deleteDailyTarget,
  getTodayActivities,
} from "../services/api";

const DashboardHome = () => {
  const { tokenData } = useContext(AuthContext);
  const navigate = useNavigate();

  const userId =
    tokenData?.sub ||
    tokenData?.preferred_username ||
    "anonymous";

  /* -------------------- STATE -------------------- */
  const [targets, setTargets] = useState([]);
  const [todayActivities, setTodayActivities] = useState([]);

  /* -------------------- FETCH FUNCTIONS -------------------- */

  const fetchDailyTargets = useCallback(() => {
    if (!userId) return;

    getDailyTargets(userId)
      .then((res) => setTargets(res.data))
      .catch((err) =>
        console.error("Failed to load daily targets", err)
      );
  }, [userId]);

  const fetchTodayActivities = useCallback(() => {
    getTodayActivities()
      .then((res) => setTodayActivities(res.data))
      .catch((err) =>
        console.error("Failed to fetch activities", err)
      );
  }, []);

  /* -------------------- INITIAL LOAD -------------------- */
  useEffect(() => {
    fetchDailyTargets();
    fetchTodayActivities();
  }, [fetchDailyTargets, fetchTodayActivities]);

  /* -------------------- DAILY TARGET ACTIONS -------------------- */

  const addTarget = ({ activityType, label }) => {
  addDailyTarget(userId, {
    activityType, // WALKING / RUNNING / YOGA
    label,
  })
    .then(fetchDailyTargets)
    .catch(err => console.error("Failed to add target", err));
};


  const toggleTarget = (id) => {
    toggleDailyTarget(userId, id)
      .then(fetchDailyTargets)
      .catch((err) =>
        console.error("Failed to toggle target", err)
      );
  };

  const resetTargets = () => {
    resetDailyTargets(userId)
      .then(fetchDailyTargets)
      .catch((err) =>
        console.error("Failed to reset targets", err)
      );
  };

  const removeTarget = (id) => {
    deleteDailyTarget(userId, id)
      .then(fetchDailyTargets)
      .catch((err) =>
        console.error("Failed to delete target", err)
      );
  };

  /* -------------------- DERIVED DATA -------------------- */

  const completedActivities = todayActivities.length;

  const caloriesBurnedToday = todayActivities.reduce(
    (sum, activity) => sum + (activity.caloriesBurned || 0),
    0
  );

  const totalTargets = targets.length;
  const completedTargets = targets.filter(t => t.completed).length;

  const confidenceScore =
    totalTargets === 0
      ? 0
      : Math.round((completedTargets / totalTargets) * 100);

  const userName =
    tokenData?.given_name ||
    tokenData?.name ||
    tokenData?.preferred_username ||
    "User";

  const getCoachInsight = () => {
    if (totalTargets === 0)
      return "Set one small goal for today. Even a short walk counts — momentum starts small.";

    if (completedTargets === 0)
      return "You’ve planned your day well. Start with the easiest goal to build momentum.";

    if (completedTargets < totalTargets)
      return `Nice progress. You’ve completed ${completedTargets} of ${totalTargets} goals. One more will push you forward.`;

    return "Outstanding work today. You completed all your goals — prioritize recovery now.";
  };

  /* -------------------- UI -------------------- */

  return (
    <DashboardLayout
      greeting={
        <DashboardGreeting
          userName={userName}
          confidenceScore={confidenceScore}
          aiInsight={getCoachInsight()}
          onStartActivity={() => navigate("/activities/new")}
        />
      }
      confidence={<ConfidenceRing value={confidenceScore} />}
      todayStats={
        <TodayStatsCard
          activities={completedActivities}
          calories={caloriesBurnedToday}
        />
      }
      aiInsight={
        <Box sx={{ maxWidth: 640, textAlign: "center" }}>
          <Typography variant="h6" sx={{ mb: 1 }}>
            Today’s Performance Overview
            {/* Daily Performance Summary */}
          </Typography>
          <Typography color="text.secondary">
            {getCoachInsight()}
          </Typography>
        </Box>
      }
      cta={
        <Button
          variant="contained"
          size="large"
          onClick={() => navigate("/activities/new")}
        >
          Start Today’s Activity
        </Button>
      }
      dailyTargets={
        <Box>
          <DailyTargets
            targets={targets}
            onToggle={toggleTarget}
            onAdd={addTarget}
            onRemove={removeTarget}
            onReset={resetTargets}
          />
          <RecentActivities />
        </Box>
      }
    />
  );
};

export default DashboardHome;
