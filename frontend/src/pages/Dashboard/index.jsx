import React from "react";
import {
  Box,
  Container,
  Tab,
  Tabs,
  Typography,
  Paper,
  List,
  ListItem,
  ListItemText,
  Divider,
} from "@mui/material";

import {
  DirectionsCar as CarIcon,
  AttachMoney as MoneyIcon,
  AccessTime as ClockIcon,
  Person as UserIcon,
  Percent as PercentIcon,
  Warning as AlertIcon,
  EmojiEvents as AwardIcon,
  Business as BuildingIcon,
} from "@mui/icons-material";
import StatCard from "./components/StatCard";
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

const RecentActivity = ({ activities }) => (
  <Paper sx={{ p: 2 }}>
    <Box sx={{ display: "flex", alignItems: "center", gap: 1, mb: 2 }}>
      <ClockIcon color="primary" />
      <Typography variant="h6">Recent Activity</Typography>
    </Box>
    <List>
      {activities.map((activity, index) => (
        <React.Fragment key={activity.id}>
          {index > 0 && <Divider />}
          <ListItem
            sx={{
              display: "flex",
              justifyContent: "space-between",
              py: 1.5,
            }}
          >
            <ListItemText
              primary={activity.description}
              secondary={activity.time}
              primaryTypographyProps={{ variant: "body2" }}
              secondaryTypographyProps={{ variant: "caption" }}
            />
            <Box
              sx={{
                width: 8,
                height: 8,
                borderRadius: "50%",
                bgcolor: activity.color,
              }}
            />
          </ListItem>
        </React.Fragment>
      ))}
    </List>
  </Paper>
);

const TopPerformers = ({ items, title, icon: Icon }) => (
  <Paper sx={{ p: 2 }}>
    <Box sx={{ display: "flex", alignItems: "center", gap: 1, mb: 2 }}>
      <Icon color="primary" />
      <Typography variant="h6">{title}</Typography>
    </Box>
    <List>
      {items.map((item, index) => (
        <React.Fragment key={index}>
          {index > 0 && <Divider />}
          <ListItem
            sx={{
              display: "flex",
              justifyContent: "space-between",
              py: 1.5,
            }}
          >
            <Box sx={{ display: "flex", alignItems: "center", gap: 2 }}>
              <Typography color="text.secondary">#{index + 1}</Typography>
              <ListItemText
                primary={item.name}
                secondary={item.detail}
                primaryTypographyProps={{ variant: "body2" }}
                secondaryTypographyProps={{ variant: "caption" }}
              />
            </Box>
            <Typography variant="body2" fontWeight="medium">
              {item.value}
            </Typography>
          </ListItem>
        </React.Fragment>
      ))}
    </List>
  </Paper>
);

export default function ParkingDashboard() {
  const [tabValue, setTabValue] = React.useState(0);

  const operationsActivities = [
    {
      id: 1,
      description: "Vehicle overstay in spot A1",
      time: "2 min ago",
      color: "#f44336",
    },
    {
      id: 2,
      description: "New reservation for spot B3",
      time: "5 min ago",
      color: "#4caf50",
    },
    {
      id: 3,
      description: "Payment received for spot C4",
      time: "10 min ago",
      color: "#2196f3",
    },
  ];

  const topUsers = [
    { name: "John Smith", detail: "Premium Member", value: "32 bookings" },
    { name: "Sarah Johnson", detail: "Regular User", value: "28 bookings" },
    { name: "Mike Wilson", detail: "Business Account", value: "25 bookings" },
  ];

  const topLots = [
    { name: "Downtown Garage", detail: "123 Main St", value: "$12,450" },
    { name: "Airport Parking", detail: "Terminal 3", value: "$10,280" },
    { name: "Mall Complex", detail: "Shopping District", value: "$8,975" },
  ];

  return (
    <Container maxWidth="xl" sx={{ py: 4 }}>
      <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
        <Tabs value={tabValue} onChange={(e, v) => setTabValue(v)}>
          <Tab label="Operations Dashboard" />
          <Tab label="Analytics Dashboard" />
        </Tabs>
      </Box>

      <TabPanel value={tabValue} index={0}>
        <Typography variant="h4" gutterBottom>
          Operations Dashboard
        </Typography>
        <Typography variant="subtitle1" color="text.secondary" gutterBottom>
          Real-time parking operations overview
        </Typography>

        <Box
          sx={{
            display: "grid",
            gridTemplateColumns: {
              xs: "1fr",
              md: "repeat(2, 1fr)",
              lg: "repeat(4, 1fr)",
            },
            gap: 2,
            mb: 3,
          }}
        >
          <StatCard
            title="Current Occupancy"
            value="85%"
            icon={PercentIcon}
            trend={{ value: 5, isPositive: true }}
          />
          <StatCard
            title="Active Sessions"
            value={45}
            icon={CarIcon}
            trend={{ value: 2, isPositive: true }}
          />
          <StatCard
            title="Violations Today"
            value={3}
            icon={AlertIcon}
            trend={{ value: 1, isPositive: false }}
          />
          <StatCard
            title="Revenue Today"
            value="$1,234"
            icon={MoneyIcon}
            trend={{ value: 12, isPositive: true }}
          />
        </Box>

        <Box
          sx={{
            display: "grid",
            gridTemplateColumns: { xs: "1fr", lg: "repeat(2, 1fr)" },
            gap: 3,
          }}
        >
          <RecentActivity activities={operationsActivities} />
        </Box>
      </TabPanel>

      <TabPanel value={tabValue} index={1}>
        <Typography variant="h4" gutterBottom>
          Analytics Dashboard
        </Typography>
        <Typography variant="subtitle1" color="text.secondary" gutterBottom>
          System-wide performance metrics
        </Typography>

        <Box
          sx={{
            display: "grid",
            gridTemplateColumns: {
              xs: "1fr",
              md: "repeat(2, 1fr)",
              lg: "repeat(4, 1fr)",
            },
            gap: 2,
            mb: 3,
          }}
        >
          <StatCard
            title="Monthly Revenue"
            value="$45,678"
            icon={MoneyIcon}
            trend={{ value: 8, isPositive: true }}
          />
          <StatCard
            title="Total Users"
            value="1,234"
            icon={UserIcon}
            trend={{ value: 15, isPositive: true }}
          />
          <StatCard title="Avg. Duration" value="2.5 hrs" icon={ClockIcon} />
          <StatCard
            title="Active Lots"
            value={12}
            icon={BuildingIcon}
            trend={{ value: 1, isPositive: true }}
          />
        </Box>

        <Box
          sx={{
            display: "grid",
            gridTemplateColumns: { xs: "1fr", lg: "repeat(2, 1fr)" },
            gap: 3,
          }}
        >
          <TopPerformers items={topUsers} title="Top Users" icon={AwardIcon} />
          <TopPerformers
            items={topLots}
            title="Top Parking Lots"
            icon={BuildingIcon}
          />
        </Box>
      </TabPanel>
    </Container>
  );
}

ParkingDashboard.propTypes = {
  children: proptypes.node.isRequired,
};

TabPanel.propTypes = {
  children: proptypes.node.isRequired,
  value: proptypes.number.isRequired,
  index: proptypes.number.isRequired,
};

RecentActivity.propTypes = {
  activities: proptypes.array.isRequired,
};

TopPerformers.propTypes = {
  items: proptypes.array.isRequired,
  title: proptypes.string.isRequired,
  icon: proptypes.elementType.isRequired,
};
