import { Navigate } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "react-oauth2-code-pkce";

const ProtectedRoute = ({ children }) => {
  const { token, isAuthenticated } = useContext(AuthContext);

  if (isAuthenticated === undefined) {
    return null;
  }

  if (!token) {
    return <Navigate to="/" replace />;
  }

  return children;
};

export default ProtectedRoute;
