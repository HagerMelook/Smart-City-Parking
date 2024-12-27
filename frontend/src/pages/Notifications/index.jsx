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
  IconButton,
  Badge,
  Button,
} from "@mui/material";
import { Delete as DeleteIcon } from "@mui/icons-material";

export default function NotificationsPage() {
  const notifications = [
    {
      id: 1,
      type: "violation",
      message: "Vehicle ABC123 has exceeded parking time limit in spot A1",
      time: "5 minutes ago",
      read: false,
    },
    {
      id: 2,
      type: "reservation",
      message: "New reservation request for spot B3",
      time: "1 hour ago",
      read: true,
    },
    {
      id: 3,
      type: "system",
      message: "System maintenance scheduled for tonight at 2 AM",
      time: "3 hours ago",
      read: false,
    },
  ];

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Box
        sx={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          mb: 3,
        }}
      >
        <Typography variant="h4">Notifications</Typography>
        <Button variant="outlined" startIcon={<DeleteIcon />}>
          Clear All
        </Button>
      </Box>

      <Paper>
        <List>
          {notifications.map((notification, index) => (
            <React.Fragment key={notification.id}>
              {index > 0 && <Divider />}
              <ListItem
                secondaryAction={
                  <IconButton edge="end" aria-label="delete">
                    <DeleteIcon />
                  </IconButton>
                }
              >
                <ListItemText
                  primary={
                    <Box sx={{ display: "flex", alignItems: "center", gap: 1 }}>
                      {!notification.read && (
                        <Badge color="error" variant="dot" />
                      )}
                      <Typography
                        variant="body1"
                        sx={{
                          fontWeight: notification.read ? "normal" : "bold",
                        }}
                      >
                        {notification.message}
                      </Typography>
                    </Box>
                  }
                  secondary={notification.time}
                />
              </ListItem>
            </React.Fragment>
          ))}
        </List>
      </Paper>
    </Container>
  );
}
