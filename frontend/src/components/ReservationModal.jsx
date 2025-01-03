import React from "react";
import proptype from "prop-types";

export default function ReservationModal({ spot, onClose, onReserve }) {
  const [hours, setHours] = React.useState(1);
  const [startTime, setStartTime] = React.useState("");
  const [endTime, setEndTime] = React.useState("");
  const [error, setError] = React.useState("");

  const calculateEndTime = (startTime, hours) => {
    if (!startTime) return "";
    const startDate = new Date(startTime);
    startDate.setHours(startDate.getHours() + hours);

    setEndTime(startDate.getTime());
  };

  const validateStartTime = (startTime) => {
    const currentTime = new Date();

    console.log(startTime, currentTime.getTime());

    if (startTime < currentTime.getTime()) {
      setError(
        "Selected start time is in the past. Please choose a future time."
      );
      return false;
    }
    setError("");
    return true;
  };

  const handleStartTimeChange = (time) => {
    const currentYear = new Date().getFullYear();
    const currentMonth = new Date().getMonth();
    const currentDay = new Date().getDate();

    const selectedTime = new Date(
      `${currentYear}-${currentMonth + 1}-${currentDay}T${time}:00`
    );

    setStartTime(selectedTime.getTime());
  };

  React.useEffect(() => {
    if (startTime && validateStartTime(startTime)) {
      calculateEndTime(startTime, hours);
    }
  }, [startTime, hours]);

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
      <div className="bg-white rounded-lg p-6 w-96">
        <h2 className="text-2xl font-bold mb-4">Reserve Spot #{spot.number}</h2>

        <div className="mb-4">
          <label className="block text-sm font-medium text-gray-700">
            Start Time
          </label>
          <input
            type="time"
            value={`${
              (new Date(startTime).getHours() >= 10 ? "" : "0") +
              new Date(startTime).getHours()
            }:${
              (new Date(startTime).getMinutes() >= 10 ? "" : "0") +
              new Date(startTime).getMinutes()
            }`}
            onChange={(e) => handleStartTimeChange(e.target.value)}
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
          />
        </div>

        {error && <div className="text-sm text-red-500 mb-2">{error}</div>}

        <div className="mb-4">
          <label className="block text-sm font-medium text-gray-700">
            Duration (hours)
          </label>
          <input
            type="number"
            min="1"
            max="24"
            value={hours}
            onChange={(e) => setHours(parseInt(e.target.value))}
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
          />
        </div>

        <div className="mb-6">
          <div className="text-sm text-gray-600">
            Price per hour: ${spot.price}
          </div>
          <div className="text-lg font-bold text-blue-600">
            Total: ${(spot.price * hours).toFixed(2)}
          </div>
        </div>

        <div className="flex justify-end gap-3">
          <button
            onClick={onClose}
            className="px-4 py-2 text-gray-700 hover:bg-gray-100 rounded-md"
          >
            Cancel
          </button>
          <button
            onClick={() => onReserve(startTime, endTime)}
            disabled={error !== ""}
            className={`px-4 py-2 ${
              error
                ? "bg-gray-300 text-gray-500 cursor-not-allowed"
                : "bg-blue-600 text-white"
            } rounded-md hover:bg-blue-700`}
          >
            Confirm Reservation
          </button>
        </div>
      </div>
    </div>
  );
}

ReservationModal.propTypes = {
  spot: proptype.object.isRequired,
  onClose: proptype.func.isRequired,
  onReserve: proptype.func.isRequired,
};
