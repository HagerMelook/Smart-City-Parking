import React, { useEffect, useState } from "react";
import { 
  Container, 
  Box, 
  Typography, 
  Button, 
  Paper, 
  List, 
  Divider, 
  ListItem, 
  ListItemText, 
  IconButton, 
  Badge 
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";

export default function NotificationsPage() {
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchNotifications = async () => {
      try {
        const response = await fetch("http://localhost:3001/notifications");
        if (response.ok) {
          const data = await response.json();
          setNotifications(data);
        } else {
          console.error("Failed to fetch notifications:", response.statusText);
        }
      } catch (error) {
        console.error("Error fetching notifications:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchNotifications();
  }, []);

  const handleClearAll = async () => {
    try {
      const response = await fetch("http://localhost:8080/notifications/clear-all", {
        method: "DELETE",
      });
      if (response.ok) {
        setNotifications([]);
      } else {
        console.error("Failed to clear notifications:", response.statusText);
      }
    } catch (error) {
      console.error("Error clearing notifications:", error);
    }
  };

  const handleDelete = async (id) => {
    try {
      const response = await fetch(
        `http://localhost:8080/notifications/${id}`,
        { method: "DELETE" }
      );
      if (response.ok) {
        setNotifications((prev) => prev.filter((n) => n.id !== id));
      } else {
        console.error("Failed to delete notification:", response.statusText);
      }
    } catch (error) {
      console.error("Error deleting notification:", error);
    }
  };

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
        <Button
          variant="outlined"
          startIcon={<DeleteIcon />}
          onClick={handleClearAll}
        >
          Clear All
        </Button>
      </Box>

      <Paper>
        {loading ? (
          <Typography sx={{ p: 2 }}>Loading notifications...</Typography>
        ) : notifications.length === 0 ? (
          <Typography sx={{ p: 2 }}>No notifications to display.</Typography>
        ) : (
          <List>
            {notifications.map((notification, index) => (
              <React.Fragment key={notification.id}>
                {index > 0 && <Divider />}
                <ListItem
                  secondaryAction={
                    <IconButton
                      edge="end"
                      aria-label="delete"
                      onClick={() => handleDelete(notification.id)}
                    >
                      <DeleteIcon />
                    </IconButton>
                  }
                >
                  <ListItemText
                    primary={
                      <Box
                        sx={{ display: "flex", alignItems: "center", gap: 1 }}
                      >
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
        )}
      </Paper>
    </Container>
  );
}
