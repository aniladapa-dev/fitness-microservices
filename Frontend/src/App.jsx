import { Routes, Route } from "react-router-dom";
import { Box } from "@mui/material";
import Navbar from "./components/Navbar";
import HomePage from "./pages/HomePage";
import ActivitiesPage from "./pages/ActivitiesPage";
import AddActivityPage from "./pages/AddActivityPage";
import ActivityDetailPage from "./pages/ActivityDetailPage";
import RecommendationsPage from "./pages/RecommendationsPage";
import Footer from "./components/Footer";
import { useContext, useEffect } from "react";
import { AuthContext } from "react-oauth2-code-pkce";
import { setAccessToken } from "./services/api";



// function App() {
//   const { token } = useContext(AuthContext);

//   useEffect(() => {
//     if (token) {
//       setAccessToken(token);
//     }
//   }, [token]);



//   return (
    
//     <Router>
//       {/*  Root layout container */}
//       <Box
//         sx={{
//           minHeight: "100vh",
//           display: "flex",
//           flexDirection: "column",
//         }}
//       >
//         <Navbar />

//         {/*  This MUST grow */}
//         <Box component="main" sx={{ flex: 1 }}>
//           <Routes>
//             <Route path="/" element={<HomePage />} />
//             <Route path="/activities" element={<ActivitiesPage />} />
//             <Route path="/activities/new" element={<AddActivityPage />} />
//             <Route path="/activities/:id" element={<ActivityDetailPage />} />
//             <Route path="/recommendations" element={<RecommendationsPage />} />
//           </Routes>
//         </Box>

//         <Footer />
//       </Box>
//     </Router>
//   );
// }
  
function App() {

  const { token } = useContext(AuthContext);

  useEffect(() => {
    if (token) {
      setAccessToken(token);
    }
  }, [token]);


  return (
    <Box sx={{ minHeight: "100vh", display: "flex", flexDirection: "column" }}>
      <Navbar />

      <Box component="main" sx={{ flex: 1 }}>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/activities" element={<ActivitiesPage />} />
          <Route path="/activities/new" element={<AddActivityPage />} />
          <Route path="/activities/:id" element={<ActivityDetailPage />} />
          <Route path="/recommendations" element={<RecommendationsPage />} />
        </Routes>
      </Box>

      <Footer />
    </Box>
  );
}

export default App;
