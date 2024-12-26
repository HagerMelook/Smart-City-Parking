import proptype from "prop-types";

export default function StatCard({ title, value, icon: Icon, trend }) {
  return (
    <div className="bg-white rounded-lg p-6 shadow-sm">
      <div className="flex items-center justify-between">
        <div>
          <p className="text-sm text-gray-600">{title}</p>
          <h3 className="text-2xl font-bold mt-1">{value}</h3>
          {trend && (
            <p
              className={`text-sm mt-2 ${
                trend.isPositive ? "text-green-600" : "text-red-600"
              }`}
            >
              {trend.isPositive ? "↑" : "↓"} {Math.abs(trend.value)}%
            </p>
          )}
        </div>
        <Icon className="w-8 h-8 text-blue-600" />
      </div>
    </div>
  );
}

StatCard.propTypes = {
  title: proptype.string.isRequired,
  value: proptype.oneOfType([proptype.string, proptype.number]).isRequired,
  icon: proptype.elementType.isRequired,
  trend: proptype.shape({
    value: proptype.number.isRequired,
    isPositive: proptype.bool,
  }),
};
