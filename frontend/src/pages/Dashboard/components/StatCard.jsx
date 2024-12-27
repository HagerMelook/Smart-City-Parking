import { Card, CardContent, Typography } from "@mui/material";
import PropTypes from "prop-types";

const StatCard = ({ title, value, icon: Icon, trend }) => {
  return (
    <Card>
      <CardContent>
        <div
          style={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "flex-start",
          }}
        >
          <div>
            <Typography variant="subtitle2" color="textSecondary">
              {title}
            </Typography>
            <Typography
              variant="h4"
              component="div"
              sx={{ mt: 1, fontWeight: "bold" }}
            >
              {value}
            </Typography>
            {trend && (
              <Typography
                variant="body2"
                sx={{
                  mt: 1,
                  color: trend.isPositive ? "success.main" : "error.main",
                  display: "flex",
                  alignItems: "center",
                  gap: 0.5,
                }}
              >
                {trend.isPositive ? "↑" : "↓"} {Math.abs(trend.value)}%
              </Typography>
            )}
          </div>
          {Icon && (
            <Icon
              sx={{
                width: 32,
                height: 32,
                color: "primary.main",
              }}
            />
          )}
        </div>
      </CardContent>
    </Card>
  );
};

StatCard.propTypes = {
  title: PropTypes.string.isRequired,
  value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
  icon: PropTypes.elementType,
  trend: PropTypes.shape({
    value: PropTypes.number.isRequired,
    isPositive: PropTypes.bool.isRequired,
  }),
};

export default StatCard;
