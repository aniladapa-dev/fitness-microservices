import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api";

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

let accessToken = null;

export const setAccessToken = (token) => {
  accessToken = token;
};

api.interceptors.request.use(
  (config) => {
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);


/* API calls */
export const addActivity = (activity) => api.post("/activities", activity);
export const getActivities = () => api.get("/activities");
export const getActivityById = (id) => api.get(`/activities/${id}`);

export const getRecommendationByActivityId = (activityId) =>
  api.get(`/recommendations/activity/${activityId}`);

export const getUserRecommendations = (userId) =>
  api.get(`/recommendations/user/${userId}`);

export const getDailyTargets = (userId) =>
  api.get(`/users/${userId}/daily-targets/today`);

export const addDailyTarget = (userId, data) =>
  api.post(`/users/${userId}/daily-targets/today`, data);

export const toggleDailyTarget = (userId, targetId) =>
  api.patch(`/users/${userId}/daily-targets/${targetId}/toggle`);

export const resetDailyTargets = (userId) =>
  api.delete(`/users/${userId}/daily-targets/today`);

export const deleteDailyTarget = (userId, id) =>
  api.delete(`/users/${userId}/daily-targets/${id}`);

export const getTodayActivities = () =>
  api.get("/activities/today");

export const deleteActivity = (activityId) =>
  api.delete(`/activities/${activityId}`);

export const deleteRecommendation = (activityId) =>
  api.delete(`/recommendations/activity/${activityId}`);



export default api;
