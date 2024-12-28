import { useState, useEffect } from "react";
import { Button } from "@mui/material";

import StatCard from "../Dashboard/components/StatCard";
import { DollarSign, Percent, AlertTriangle, FileText } from "lucide-react";
const lotId = localStorage.getItem("userId");

export function LotManagerDashboard() {
  const handleReportClick = () => {
    window.location.href = `http://localhost:8080/lots/${lotId}/report`;
  };

  const [data, setData] = useState([]);

  useEffect(() => {
    async function fetchData() {
      const response = await fetch(
        `http://localhost:8080/lots/${lotId}/report`
      );
      const data = await response.json();
      setData(data);
    }
    fetchData();
  }, []);

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold">Lot Manager Dashboard</h1>
        <Button onClick={handleReportClick} className="flex items-center gap-2">
          <FileText className="h-4 w-4" />
          View Report
        </Button>
      </div>

      <div className="space-y-8">
        {data.map((item) => (
          <div key={item.interval} className="space-y-4">
            <h2 className="text-2xl font-semibold">{`${item.interval} Stats`}</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
              <StatCard
                title="Revenue"
                value={`$${item.revenue.toFixed(1)}`}
                icon={DollarSign}
                trend={{ value: 12, isPositive: true }}
              />
              <StatCard
                title="Violations"
                value={item.violations}
                icon={AlertTriangle}
                trend={{ value: 1, isPositive: false }}
              />
              <StatCard
                title="Occupancy Rate"
                value={`${item.occupancyRate.toFixed(1)}%`}
                icon={Percent}
                trend={{ value: 1, isPositive: false }}
              />
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
