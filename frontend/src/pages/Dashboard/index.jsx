import { Car, DollarSign, Clock, Users } from "lucide-react";
import StatCard from "./components/StatCard";
import RecentActivity from "./components/RecentActivity";

export default function Dashboard() {
  return (
    <div className="space-y-6 m-8">
      <header>
        <h1 className="text-2xl font-bold text-gray-900">Dashboard</h1>
        <p className="mt-1 text-gray-600">
          Overview of your parking management system
        </p>
      </header>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        <StatCard
          title="Total Spots"
          value={120}
          icon={Car}
          trend={{ value: 5, isPositive: true }}
        />
        <StatCard
          title="Revenue Today"
          value="$1,234"
          icon={DollarSign}
          trend={{ value: 12, isPositive: true }}
        />
        <StatCard title="Avg. Duration" value="2.5 hrs" icon={Clock} />
        <StatCard
          title="Active Users"
          value={45}
          icon={Users}
          trend={{ value: 3, isPositive: false }}
        />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <RecentActivity />
        {/* Add more dashboard widgets here */}
      </div>
    </div>
  );
}
