import { Box, Container, Button } from "@mui/material";

import { Description as ReportIcon } from "@mui/icons-material";
// import RecentActivity from "./components/RecentActivity";
import proptypes from "prop-types";

export default function ParkingDashboard() {
  const handleTopUsersReport = () => {
    window.location.href = "http://localhost:8080/admin/topUsers";
  };

  const handleTopLotsReport = () => {
    window.location.href = "http://localhost:8080/admin/topLots";
  };

  // useEffect(() => {
  //   async function fetchParkingData() {
  //     const response = await fetch(`http://localhost:3003/admin_dashboard`);
  //     const data = await response.json();
  //     console.log(data);
  //   }
  //   fetchParkingData();
  // }, []);

  return (
    <Container maxWidth="xl" sx={{ py: 4 }}>
      <Box sx={{ display: "flex", gap: 2, mb: 3 }}>
        <Button
          variant="contained"
          startIcon={<ReportIcon />}
          onClick={handleTopUsersReport}
          sx={{ bgcolor: "primary.main" }}
        >
          Top Users Report
        </Button>
        <Button
          variant="contained"
          startIcon={<ReportIcon />}
          onClick={handleTopLotsReport}
          sx={{ bgcolor: "primary.main" }}
        >
          Top Lots Report
        </Button>
      </Box>
    </Container>
  );
}

ParkingDashboard.propTypes = {
  children: proptypes.node.isRequired,
};
