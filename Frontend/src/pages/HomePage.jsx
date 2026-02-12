import { useContext } from "react";
import { AuthContext } from "react-oauth2-code-pkce";
import LoggedOutHome from "./LoggedOutHome";
import DashboardHome from "./DashboardHome";

const HomePage = () => {
  const { token } = useContext(AuthContext);

  
  if (!token) {
    return <LoggedOutHome />;
  }

  return <DashboardHome />;
};

export default HomePage;
