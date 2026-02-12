import { Box } from "@mui/material";
import { useNavigate } from "react-router-dom";
import ActivitiesForm from "../components/ActivitiesForm";

const AddActivityPage = () => {
  const navigate = useNavigate();

  const handleActivityAdded = () => {
    navigate("/activities");
  };

  return (
    <Box sx={{ p: 2 }}>
      <ActivitiesForm onActivityAdded={handleActivityAdded} />
    </Box>
  );
};

export default AddActivityPage;
