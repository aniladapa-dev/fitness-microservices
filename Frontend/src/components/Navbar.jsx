import { AppBar, Toolbar, Typography, Button, Box } from "@mui/material";
import { Link, useNavigate } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "react-oauth2-code-pkce";
import ThemeToggle from "./ThemeToggle";

const Navbar = () => {
  const { token, logOut, logIn } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = async () => {
    logOut();
    navigate("/");
  };





  const handleLogin =  () => {
    logIn(); // redirects to Keycloak
  };

  return (
    <AppBar position="static">
      <Toolbar>

        {/* LEFT: App name */}
        <Typography
          variant="h6"
          component={Link}
          to="/"
          sx={{ flexGrow: 1, textDecoration: "none", color: "inherit" }}
        >
          Fitness AI
        </Typography>

        {/* RIGHT: nav buttons + theme toggle (ONE FLEX ROW) */}
        <Box
          sx={{
            display: "flex",
            alignItems: "center",
            gap: 1.5,
          }}
        >
          <Button color="inherit" component={Link} to="/">
            Home
          </Button>

          {token && (
            <>
              <Button color="inherit" component={Link} to="/activities/new">
                Add Activity
              </Button>

              <Button color="inherit" component={Link} to="/activities">
                Activities
              </Button>

              <Button color="inherit" component={Link} to="/recommendations">
                Recommendations
              </Button>
            </>
          )}

          {!token ? (
            <Button color="inherit" onClick={handleLogin}>
              Login
            </Button>
          ) : (
            <Button color="inherit" onClick={handleLogout}>
              Logout
            </Button>
          )}


          {/* Theme toggle stays INLINE */}
          <ThemeToggle />
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;
