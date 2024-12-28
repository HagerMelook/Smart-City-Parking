import { useState, useEffect } from "react";
import { Box, Container, Typography, Grid, Button } from "@mui/material";
import {
  AttachMoney as MoneyIcon,
  Warning as AlertIcon,
  Report as ReportIcon,
} from "@mui/icons-material";
import StatCard from "../Dashboard/components/StatCard";
import { PercentIcon } from "lucide-react";

export function LotManagerDashboard() {
  const handleReportClick = () => {
    const lotId = localStorage.getItem("userId");
    window.location.href = `http://localhost:8080/lots/${lotId}/report`;
  };

  const [data, setData] = useState([]);

  useEffect(() => {
    async function fetchData() {
      const response = await fetch(
        "http://localhost:8080/lots/{lot_id}/report"
      );
      const data = await response.json();
      setData(data);
    }
    fetchData();
  }, []);

  return (
    <Container maxWidth="xl" sx={{ py: 4 }}>
      <Box
        sx={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          mb: 3,
        }}
      >
        <Typography variant="h4">Lot Manager Dashboard</Typography>
        <Button
          variant="contained"
          startIcon={<ReportIcon />}
          onClick={handleReportClick}
          color="primary"
        >
          View Report
        </Button>
      </Box>

      <Grid container spacing={3}>
        {data.map((item) => (
          <Grid item xs={12} lg={8} key={item.interval}>
            <Box
              sx={{
                display: "grid",
                gridTemplateColumns: {
                  xs: "1fr",
                  sm: "repeat(2, 1fr)",
                  md: "repeat(4, 1fr)",
                },
                gap: 2,
                mb: 3,
              }}
            >
              <Typography variant="h4">{`${item.interval} Stats`}</Typography>
              <StatCard
                title="Revenue"
                value={`$${item.revenue.toFixed(1)}`}
                icon={MoneyIcon}
                trend={{ value: 12, isPositive: true }}
              />
              <StatCard
                title="Violations"
                value={item.violations}
                icon={AlertIcon}
                trend={{ value: 1, isPositive: false }}
              />
              <StatCard
                title="Violations"
                value={`${item.occupancyRate.toFixed(1)}%`}
                icon={PercentIcon}
                trend={{ value: 1, isPositive: false }}
              />
            </Box>
          </Grid>
        ))}
        <Grid item xs={12} lg={8}>
          {/* Stats Section */}
          <Box
            sx={{
              display: "grid",
              gridTemplateColumns: {
                xs: "1fr",
                sm: "repeat(2, 1fr)",
                md: "repeat(4, 1fr)",
              },
              gap: 2,
              mb: 3,
            }}
          >
            <StatCard
              title="Today's Revenue"
              value="$520"
              icon={MoneyIcon}
              trend={{ value: 12, isPositive: true }}
            />
            <StatCard
              title="Violations"
              value="3"
              icon={AlertIcon}
              trend={{ value: 1, isPositive: false }}
            />
          </Box>
        </Grid>
      </Grid>
    </Container>
  );
}
