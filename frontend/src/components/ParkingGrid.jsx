import { Car, Zap, Accessibility } from "lucide-react";
import proptype from "prop-types";

export default function ParkingGrid({ spots, onSpotClick }) {
  return (
    <div className="grid grid-cols-4 gap-4">
      {spots.map((spot, index) => (
        <button
          key={spot.spot_id}
          onClick={() => onSpotClick(spot)}
          className={`
            p-4 rounded-lg shadow-sm border-2 transition-all bg-white border-blue-200 hover:border-blue-400 hover:shadow-md
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
            Level {(spot.number / 20 + 1).toFixed(0)}
          </div>

          {/* Cost Per Hour */}
          <div className="mt-2 font-bold text-blue-600">${spot.price}/hr</div>

          {/* Availability Status */}
          <div className="mt-2 text-sm">
            {<span className="text-green-500">Available</span>}
          </div>

          {/* Available Periods */}
          <div className="mt-2 text-sm text-gray-600">
            <div>Available Periods:</div>
            {spot.availableHours.map((period, idx) => (
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
