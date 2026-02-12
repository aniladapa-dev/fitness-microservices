import React from "react";
import ReactDOM from "react-dom/client";
import { Provider } from "react-redux";
import { store } from "./store/store";
import { AuthProvider } from "react-oauth2-code-pkce";
import { authConfig } from "./authConfig";
import App from "./App";
import { ThemeContextProvider } from "./context/ThemeContext";
import { BrowserRouter as Router } from "react-router-dom";




const root = ReactDOM.createRoot(
  document.getElementById("root")
);

root.render(
  <ThemeContextProvider>
    <Router>
      <AuthProvider
        authConfig={authConfig}
        autoLogin={true}
        autoRefresh={true}
        refreshTimeBeforeExpiry={30}
        loadingComponent={<div>Loading...</div>}
      >
        <Provider store={store}>
          <App />
        </Provider>
      </AuthProvider>
    </Router>
  </ThemeContextProvider>
);
