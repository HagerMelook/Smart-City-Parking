import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Layout from "./components/Layout";
import ParkingSpots from "./pages/ParkingSpots";
import Profile from "./pages/Profile";
import LoginPage from "./pages/Auth/LoginPage";
import SignUpPage from "./pages/Auth/SignUpPage";
import DriverSignUpPage from "./pages/Auth/DriverSignUpPage";
import LotAdminSignUpPage from "./pages/Auth/LotAdminSignUpPage";
import MapWithSearch from "./pages/LotSearch";
import { useState } from "react";
import ParkingDashboards from "./pages/Dashboard";
import { LotManagerDashboard } from "./pages/LotMangerDashboard";
import NotificationsPage from "./pages/Notifications";
import ReservationsPage from "./pages/Reservations";

export default function App() {
  // TODO: Add auth state management
  const [isAuthenticated] = useState(true);

  if (!isAuthenticated) {
    return (
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/signup" element={<SignUpPage />} />
          <Route path="/signup/driver" element={<DriverSignUpPage />} />
          <Route path="/signup/lot-admin" element={<LotAdminSignUpPage />} />
          <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
      </BrowserRouter>
    );
  }

  return (
    <BrowserRouter>
      <Layout>
        <Routes>
          <Route path="/dashboard" element={<ParkingDashboards />} />
          <Route path="/lot-dashboard" element={<LotManagerDashboard />} />
          <Route path="/notifications" element={<NotificationsPage />} />
          <Route path="/reservations" element={<ReservationsPage />} />
          <Route path="/parking-lots" element={<MapWithSearch />} />
          <Route path="/parking-spots" element={<ParkingSpots />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/" element={<Navigate to="/dashboard" replace />} />
        </Routes>
      </Layout>
    </BrowserRouter>
  );
}
