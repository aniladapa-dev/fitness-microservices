import { Box } from "@mui/material";
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";

const AppLayout = ({ children }) => {
  return (
    <Box
      sx={{
        minHeight: "100vh",
        display: "flex",
        flexDirection: "column",
      }}
    >
      {/* Header */}
      <Navbar />

      {/* Main content (this grows) */}
      <Box
        component="main"
        sx={{
          flex: 1,
        }}
      >
        {children}
      </Box>

      {/* Footer (always at bottom) */}
      <Footer />
    </Box>
  );
};

export default AppLayout;
