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
import proptypes from "prop-types";

const TopPerformers = ({ items, title, icon: Icon, isLot = false }) => (
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
              {isLot ? item.revenue : item.number_of_resvs}
            </Typography>
          </ListItem>
        </React.Fragment>
      ))}
    </List>
  </Paper>
);

TopPerformers.propTypes = {
  items: proptypes.array.isRequired,
  title: proptypes.string.isRequired,
  icon: proptypes.elementType.isRequired,
  isLot: proptypes.bool,
};

export default TopPerformers;
