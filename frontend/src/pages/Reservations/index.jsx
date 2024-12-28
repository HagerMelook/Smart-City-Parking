import { useState, useEffect } from "react";
import {
  Box,
  Container,
  Typography,
  Paper,
  Chip,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  IconButton,
} from "@mui/material";
import {
  DirectionsCar as CarIcon,
  AttachMoney as MoneyIcon,
  AccessTime as ClockIcon,
  Check as CheckIcon,
  Clear as ClearIcon,
} from "@mui/icons-material";
import StatCard from "../Dashboard/components/StatCard";

export default function ReservationsPage() {
  const [reservations, setReservations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const id = localStorage.getItem("userId");

  useEffect(() => {
    const fetchReservations = async () => {
      try {
        const response = await fetch("http://localhost:8080/reservations"+id);

        if (response.ok) {
          const data = await response.json();
          setReservations(data);
        } else {
          const errorData = await response.json();
          setError(errorData.message || "Failed to fetch reservations.");
        }
      } catch (err) {
        setError(err.message || "Network error.");
      } finally {
        setLoading(false);
      }
    };

    fetchReservations();
  }, []);

  if (loading) {
    return (
      <Container maxWidth="xl" sx={{ py: 4 }}>
        <Typography variant="h4" gutterBottom>
          Loading Reservations...
        </Typography>
      </Container>
    );
  }

  if (error) {
    return (
      <Container maxWidth="xl" sx={{ py: 4 }}>
        <Typography variant="h4" gutterBottom>
          Error: {error}
        </Typography>
      </Container>
    );
  }

  return (
    <Container maxWidth="xl" sx={{ py: 4 }}>
      <Typography variant="h4" gutterBottom>
        Reservations
      </Typography>

      <Box
        sx={{
          display: "grid",
          gridTemplateColumns: { xs: "1fr", md: "repeat(3, 1fr)" },
          gap: 2,
          mb: 3,
        }}
      >
        <StatCard title="Active Reservations" value="8" icon={CarIcon} />
        <StatCard title="Upcoming Reservations" value="12" icon={ClockIcon} />
        <StatCard
          title="Today's Revenue"
          value="$890"
          icon={MoneyIcon}
          trend={{ value: 15, isPositive: true }}
        />
      </Box>

      <Paper>
        <TableContainer>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Reservation ID</TableCell>
                <TableCell>Spot</TableCell>
                <TableCell>Start Time</TableCell>
                <TableCell>End Time</TableCell>
                <TableCell>Status</TableCell>
                <TableCell>Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {reservations.map((reservation) => (
                <TableRow key={reservation.resv_id}>
                  <TableCell>#{reservation.resv_id}</TableCell>
                  <TableCell>{reservation.spot_id}</TableCell>
                  <TableCell>{reservation.start_time}</TableCell>
                  <TableCell>{reservation.end_time}</TableCell>
                  <TableCell>
                    <Chip
                      label={reservation.status}
                      color={
                        reservation.status === "active"
                          ? "success"
                          : reservation.status === "upcoming"
                          ? "warning"
                          : "default"
                      }
                      size="small"
                    />
                  </TableCell>
                  <TableCell>
                    <IconButton size="small" color="success">
                      <CheckIcon />
                    </IconButton>
                    <IconButton size="small" color="error">
                      <ClearIcon />
                    </IconButton>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Paper>
    </Container>
  );
}
