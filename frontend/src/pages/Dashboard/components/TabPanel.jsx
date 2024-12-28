import { Box } from "@mui/material";
import proptypes from "prop-types";

function TabPanel({ children, value, index, ...other }) {
  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`dashboard-tabpanel-${index}`}
      {...other}
    >
      {value === index && <Box sx={{ py: 3 }}>{children}</Box>}
    </div>
  );
}

TabPanel.propTypes = {
  children: proptypes.node.isRequired,
  value: proptypes.number.isRequired,
  index: proptypes.number.isRequired,
};

export default TabPanel;
