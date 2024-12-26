import { Link, useLocation } from "react-router-dom";
import {
  Car,
  LayoutDashboard,
  LogOut,
  Settings,
  User,
  ParkingCircle,
} from "lucide-react";
import proptype from "prop-types";

export default function Layout({ children }) {
  const location = useLocation();

  return (
    <div className="min-h-screen bg-gray-50 flex">
      {/* Sidebar */}
      <aside className="w-64 bg-white shadow-lg">
        <div className="p-4">
          <h1 className="text-2xl font-bold text-blue-600 flex items-center gap-2">
            <Car className="w-8 h-8" />
            ParkWise
          </h1>
        </div>

        <nav className="mt-8">
          <NavItem
            to="/dashboard"
            icon={<LayoutDashboard />}
            text="Dashboard"
            active={location.pathname === "/dashboard"}
          />
          <NavItem
            to="/parking-lots"
            icon={<ParkingCircle />}
            text="Parking Lots"
            active={location.pathname === "/parking-lots"}
          />
          <NavItem
            to="/parking-spots"
            icon={<Car />}
            text="Parking Spots"
            active={location.pathname === "/parking-spots"}
          />
          <NavItem
            to="/profile"
            icon={<User />}
            text="Profile"
            active={location.pathname === "/profile"}
          />
          <NavItem
            to="/settings"
            icon={<Settings />}
            text="Settings"
            active={location.pathname === "/settings"}
          />

          <div className="absolute bottom-4 w-64">
            <NavItem
              to="/logout"
              icon={<LogOut />}
              text="Logout"
              active={false}
            />
          </div>
        </nav>
      </aside>

      {/* Main Content */}
      <main className="flex-1">{children}</main>
    </div>
  );
}

Layout.propTypes = {
  children: proptype.node.isRequired,
};

function NavItem({ to, icon, text, active = false }) {
  return (
    <Link
      to={to}
      className={`flex items-center gap-3 px-6 py-3 text-gray-700 hover:bg-blue-50 hover:text-blue-600 transition-colors ${
        active ? "bg-blue-50 text-blue-600" : ""
      }`}
    >
      {icon}
      <span className="font-medium">{text}</span>
    </Link>
  );
}

NavItem.propTypes = {
  to: proptype.string.isRequired,
  icon: proptype.node.isRequired,
  text: proptype.string.isRequired,
  active: proptype.bool,
};
