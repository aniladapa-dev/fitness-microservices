import { createTheme } from "@mui/material/styles";

const darkTheme = createTheme({
  palette: {
    mode: "dark",
    primary: {
      main: "#22d3ee", // neon cyan accent
    },
    background: {
      default: "#0f172a", // deep slate
      paper: "#020617",   // card background
    },
    text: {
      primary: "#e5e7eb",
      secondary: "#94a3b8",
    },
  },
  typography: {
    fontFamily: "Inter, Roboto, sans-serif",
    h4: {
      fontWeight: 600,
    },
    h5: {
      fontWeight: 600,
    },
  },
});

export default darkTheme;
