import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Layout from "./components/Layout";
import Dashboard from "./pages/Dashboard";
import ParkingSpots from "./pages/ParkingSpots";
import Profile from "./pages/Profile";
import LoginPage from "./pages/Auth/LoginPage";
import SignUpPage from "./pages/Auth/SignUpPage";
import DriverSignUpPage from "./pages/Auth/DriverSignUpPage";
import LotAdminSignUpPage from "./pages/Auth/LotAdminSignUpPage";
import { useState } from "react";

export default function App() {
  // TODO: Add auth state management
  const [isAuthenticated] = useState(false);

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
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/parking" element={<ParkingSpots />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/" element={<Navigate to="/dashboard" replace />} />
        </Routes>
      </Layout>
    </BrowserRouter>
  );
}
