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
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const handleLogin = () => {
    console.log("Logged in successfully");
    setIsAuthenticated(true);
  };

  const handleLogout = () => {
    localStorage.removeItem("userType");
    localStorage.removeItem("userId");
    console.log("Logged out successfully");
    setIsAuthenticated(false);
  };



  if (!isAuthenticated) {
    return (
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<LoginPage onLogin={handleLogin} />} />
          <Route path="/signup" element={<SignUpPage />} />
          <Route path="/signup/driver" element={<DriverSignUpPage onLogin = {handleLogin} />} />
          <Route path="/signup/lot-admin" element={<LotAdminSignUpPage onLogin = {handleLogin} />} />
          <Route path="*" element={<Navigate to="/login" replace />} />
        </Routes>
      </BrowserRouter>
    );
  }

  return (
    <BrowserRouter>
      <Layout onLogout={handleLogout}>
        <Routes>
          <Route path="/dashboard" element={<ParkingDashboards />} />
          <Route path="/lot-dashboard" element={<LotManagerDashboard />} />
          <Route path="/notifications" element={<NotificationsPage />} />
          <Route path="/reservations" element={<ReservationsPage />} />
          <Route path="/parking-lots" element={<MapWithSearch />} />
          <Route path="/parking-spots" element={<ParkingSpots />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="*" element={<Navigate to="/profile" replace />} />
        </Routes>
      </Layout>
    </BrowserRouter>
  );
}
