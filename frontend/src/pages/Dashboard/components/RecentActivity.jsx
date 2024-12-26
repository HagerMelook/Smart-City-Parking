import { Clock } from "lucide-react";

const activities = [
  { id: "1", type: "reservation", spotNumber: "A1", time: "2 hours ago" },
  { id: "2", type: "completion", spotNumber: "B3", time: "4 hours ago" },
  { id: "3", type: "cancellation", spotNumber: "A4", time: "5 hours ago" },
];

export default function RecentActivity() {
  return (
    <div className="bg-white rounded-lg p-6 shadow-sm">
      <div className="flex items-center gap-2 mb-4">
        <Clock className="w-5 h-5 text-blue-600" />
        <h2 className="text-lg font-semibold">Recent Activity</h2>
      </div>
      <div className="space-y-4">
        {activities.map((activity) => (
          <div key={activity.id} className="flex items-center justify-between">
            <div>
              <p className="text-sm font-medium">
                Spot #{activity.spotNumber} -{" "}
                {activity.type === "reservation" && "Reserved"}
                {activity.type === "completion" && "Completed"}
                {activity.type === "cancellation" && "Cancelled"}
              </p>
              <p className="text-xs text-gray-500">{activity.time}</p>
            </div>
            <div
              className={`w-2 h-2 rounded-full ${
                activity.type === "reservation"
                  ? "bg-green-500"
                  : activity.type === "completion"
                  ? "bg-blue-500"
                  : "bg-red-500"
              }`}
            />
          </div>
        ))}
      </div>
    </div>
  );
}
