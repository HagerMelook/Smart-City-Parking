import { Car, Zap } from "lucide-react";
import proptype from "prop-types";

const WheelchairIcon = () => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    fill="none"
    viewBox="0 0 24 24"
    strokeWidth={1.5}
    stroke="currentColor"
    className="w-6 h-6"
  >
    <path strokeLinecap="round" strokeLinejoin="round" d="M..." />
  </svg>
);

export default function ParkingGrid({ spots, onSpotClick }) {
  return (
    <div className="grid grid-cols-4 gap-4">
      {spots.map((spot) => (
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
          <div className="flex items-center justify-between mb-2">
            <span className="text-lg font-semibold">#{spot.number}</span>
            {spot.type === "handicap" && (
              <WheelchairIcon className="text-blue-600" />
            )}
            {spot.type === "electric" && <Zap className="text-green-600" />}
            {spot.type === "standard" && <Car className="text-gray-600" />}
          </div>
          <div className="text-sm text-gray-600">Level {spot.level}</div>
          <div className="mt-2 font-bold text-blue-600">${spot.price}/hr</div>
          <div className="mt-2 text-sm">
            {spot.isOccupied ? (
              <span className="text-red-500">Occupied</span>
            ) : (
              <span className="text-green-500">Available</span>
            )}
          </div>
        </button>
      ))}
    </div>
  );
}

ParkingGrid.propTypes = {
  spots: proptype.array.isRequired,
  onSpotClick: proptype.func.isRequired,
};
