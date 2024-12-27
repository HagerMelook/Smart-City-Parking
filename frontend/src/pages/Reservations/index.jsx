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
  const reservations = [
    {
      id: 1,
      spotId: "A1",
      vehicleNo: "ABC123",
      status: "active",
      startTime: "2024-12-27 10:00 AM",
      endTime: "2024-12-27 12:00 PM",
      customerName: "John Doe",
    },
    {
      id: 2,
      spotId: "B3",
      vehicleNo: "XYZ789",
      status: "upcoming",
      startTime: "2024-12-27 02:00 PM",
      endTime: "2024-12-27 04:00 PM",
      customerName: "Jane Smith",
    },
    {
      id: 3,
      spotId: "C2",
      vehicleNo: "DEF456",
      status: "completed",
      startTime: "2024-12-27 08:00 AM",
      endTime: "2024-12-27 09:30 AM",
      customerName: "Mike Johnson",
    },
  ];

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
                <TableCell>Customer</TableCell>
                <TableCell>Vehicle No.</TableCell>
                <TableCell>Start Time</TableCell>
                <TableCell>End Time</TableCell>
                <TableCell>Status</TableCell>
                <TableCell>Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {reservations.map((reservation) => (
                <TableRow key={reservation.id}>
                  <TableCell>#{reservation.id}</TableCell>
                  <TableCell>{reservation.spotId}</TableCell>
                  <TableCell>{reservation.customerName}</TableCell>
                  <TableCell>{reservation.vehicleNo}</TableCell>
                  <TableCell>{reservation.startTime}</TableCell>
                  <TableCell>{reservation.endTime}</TableCell>
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
