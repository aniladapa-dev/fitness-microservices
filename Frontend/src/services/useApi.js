import axios from "axios";
import { useContext, useMemo } from "react";
import { AuthContext } from "react-oauth2-code-pkce";

export const useApi = () => {
  const { token } = useContext(AuthContext);

  const api = useMemo(() => {
    const instance = axios.create({
      baseURL: "http://localhost:8080/api",
      headers: {
        "Content-Type": "application/json",
      },
    });

    instance.interceptors.request.use((config) => {
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    });

    return instance;
  }, [token]);

  return api;
};
