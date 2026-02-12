import { motion } from "framer-motion";
import { Typography } from "@mui/material";

const container = {
  hidden: {},
  visible: {
    transition: {
      staggerChildren: 0.05, // letter delay
    },
  },
};

const letter = {
  hidden: { opacity: 0, y: 10 },
  visible: {
    opacity: 1,
    y: 0,
    transition: { ease: "easeOut" },
  },
};

const AnimatedText = ({ text, variant = "h3" }) => {
  return (
    <motion.div
      variants={container}
      initial="hidden"
      animate="visible"
      style={{ display: "inline-flex" }}
    >
      {text.split("").map((char, index) => (
        <motion.span key={index} variants={letter}>
          <Typography
            component="span"
            variant={variant}
            sx={{ whiteSpace: "pre" }}
          >
            {char}
          </Typography>
        </motion.span>
      ))}
    </motion.div>
  );
};

export default AnimatedText;
