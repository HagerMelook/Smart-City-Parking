import React from "react";
import {
  Box,
  Container,
  Typography,
  Paper,
  List,
  ListItem,
  ListItemText,
  Divider,
  Chip,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  IconButton,
  Grid,
} from "@mui/material";
import {
  DirectionsCar as CarIcon,
  AttachMoney as MoneyIcon,
  AccessTime as ClockIcon,
  LocalParking as ParkingIcon,
  Warning as AlertIcon,
} from "@mui/icons-material";
import StatCard from "../Dashboard/components/StatCard";

export function LotManagerDashboard() {
  const parkingSpots = [
    { id: "A1", status: "occupied", duration: "1h 30m", vehicle: "ABC123" },
    { id: "A2", status: "available", duration: "-", vehicle: "-" },
    {
      id: "B1",
      status: "reserved",
      duration: "starts in 30m",
      vehicle: "XYZ789",
    },
    { id: "B2", status: "occupied", duration: "45m", vehicle: "DEF456" },
  ];

  return (
    <Container maxWidth="xl" sx={{ py: 4 }}>
      <Typography variant="h4" gutterBottom>
        Lot Manager Dashboard
      </Typography>

      <Grid container spacing={3}>
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
              title="Available Spots"
              value="15"
              icon={ParkingIcon}
              trend={{ value: 2, isPositive: true }}
            />
            <StatCard
              title="Occupied Spots"
              value="25"
              icon={CarIcon}
              trend={{ value: 5, isPositive: false }}
            />
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

          {/* Parking Spots Grid */}
          <Paper sx={{ p: 2, mb: 3 }}>
            <Typography variant="h6" gutterBottom>
              Parking Spots Status
            </Typography>
            <TableContainer>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>Spot ID</TableCell>
                    <TableCell>Status</TableCell>
                    <TableCell>Duration</TableCell>
                    <TableCell>Vehicle</TableCell>
                    <TableCell>Actions</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {parkingSpots.map((spot) => (
                    <TableRow key={spot.id}>
                      <TableCell>{spot.id}</TableCell>
                      <TableCell>
                        <Chip
                          label={spot.status}
                          color={
                            spot.status === "available"
                              ? "success"
                              : spot.status === "occupied"
                              ? "error"
                              : "warning"
                          }
                          size="small"
                        />
                      </TableCell>
                      <TableCell>{spot.duration}</TableCell>
                      <TableCell>{spot.vehicle}</TableCell>
                      <TableCell>
                        <IconButton size="small" color="primary">
                          <ClockIcon />
                        </IconButton>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </Paper>
        </Grid>

        {/* Activity Feed */}
        <Grid item xs={12} lg={4}>
          <Paper sx={{ p: 2, height: "100%" }}>
            <Typography variant="h6" gutterBottom>
              Recent Activity
            </Typography>
            <List>
              {[
                { time: "2m ago", text: "Vehicle ABC123 entered spot A1" },
                {
                  time: "15m ago",
                  text: "Reservation XYZ789 confirmed for B1",
                },
                { time: "30m ago", text: "Vehicle DEF456 exited spot C3" },
              ].map((activity, index) => (
                <React.Fragment key={index}>
                  {index > 0 && <Divider />}
                  <ListItem>
                    <ListItemText
                      primary={activity.text}
                      secondary={activity.time}
                      primaryTypographyProps={{ variant: "body2" }}
                      secondaryTypographyProps={{ variant: "caption" }}
                    />
                  </ListItem>
                </React.Fragment>
              ))}
            </List>
          </Paper>
        </Grid>
      </Grid>
    </Container>
  );
}
