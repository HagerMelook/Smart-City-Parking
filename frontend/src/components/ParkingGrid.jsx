import { Car, Zap, Accessibility } from "lucide-react";
import proptype from "prop-types";

export default function ParkingGrid({ spots, onSpotClick }) {
  return (
    <div className="grid grid-cols-4 gap-4">
      {spots.map((spot, index) => (
        <button
          key={spot.id}
          onClick={() => onSpotClick(spot)}
          disabled={spot.isOccupied}
          className={`
            p-4 rounded-lg shadow-sm border-2 transition-all
            ${
              spot.isOccupied
                ? "bg-gray-100 border-gray-200 cursor-not-allowed"
                : "bg-white border-blue-200 hover:border-blue-400 hover:shadow-md"
            }
          `}
        >
          {/* Header: Spot ID and Type Icon */}
          <div className="flex items-center justify-between mb-2">
            <span className="text-lg font-semibold">#{index + 1}</span>
            {spot.type === "EV charging" && <Zap className="text-green-600" />}
            {spot.type === "regular" && <Car className="text-gray-600" />}
            {spot.type === "disabled" && (
              <Accessibility className="text-blue-600" />
            )}
          </div>

          {/* Spot Level */}
          <div className="text-sm text-gray-600">
            Level {spot.level || "N/A"}
          </div>

          {/* Cost Per Hour */}
          <div className="mt-2 font-bold text-blue-600">
            ${spot.cost_per_hour}/hr
          </div>

          {/* Availability Status */}
          <div className="mt-2 text-sm">
            {spot.isOccupied ? (
              <span className="text-red-500">Occupied</span>
            ) : (
              <span className="text-green-500">Available</span>
            )}
          </div>

          {/* Available Periods */}
          <div className="mt-2 text-sm text-gray-600">
            <div>Available Periods:</div>
            {spot.available_periods.map((period, idx) => (
              <div key={idx}>
                {period.start} - {period.end}
              </div>
            ))}
          </div>
        </button>
      ))}
    </div>
  );
}

ParkingGrid.propTypes = {
  spots: proptype.arrayOf(
    proptype.shape({
      id: proptype.string.isRequired,
      type: proptype.string.isRequired,
      cost_per_hour: proptype.number.isRequired,
      isOccupied: proptype.bool.isRequired,
      level: proptype.number,
      available_periods: proptype.arrayOf(
        proptype.shape({
          start: proptype.string.isRequired,
          end: proptype.string.isRequired,
        })
      ).isRequired,
    })
  ).isRequired,
  onSpotClick: proptype.func.isRequired,
};
