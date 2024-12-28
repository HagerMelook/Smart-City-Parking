import React from "react";
import {
  Box,
  Typography,
  Paper,
  List,
  ListItem,
  ListItemText,
  Divider,
} from "@mui/material";

import { AccessTime as ClockIcon } from "@mui/icons-material";
import proptypes from "prop-types";

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

RecentActivity.propTypes = {
  activities: proptypes.array.isRequired,
};

export default RecentActivity;
