import ParkingGrid from "../../components/ParkingGrid";
import ReservationModal from "../../components/ReservationModal";
import { useState, useEffect } from "react";
// import { useSearchParams } from "react-router-dom";

export default function ParkingSpots() {
  const [spots, setSpots] = useState([]);
  const [selectedSpot, setSelectedSpot] = useState(null);
  // const [searchParams] = useSearchParams();

  function formatDate(milliseconds) {
    const date = new Date(milliseconds);

    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");
    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");
    const seconds = String(date.getSeconds()).padStart(2, "0");

    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  }

  useEffect(() => {
    // const lotId = searchParams.get("id");

    async function fetchParkingSpots() {
      // const response = await fetch(`http://localhost:8080/lots/${lotId}/spots`);
      const response = await fetch(`http://localhost:3002/spots`);
      const data = await response.json();

      const spotsWithNumbers = data.map((spot, index) => ({
        ...spot,
        number: index + 1,
      }));

      setSpots(spotsWithNumbers);
    }

    fetchParkingSpots();
  }, []);

  const handleSpotClick = (spot) => {
    if (!spot.isOccupied) {
      setSelectedSpot(spot);
    }
  };

  const handleReservation = async (startTime, endTime) => {
    if (selectedSpot && startTime && endTime) {
      try {
        const response = await fetch(
          `http://localhost:8080/spots/${selectedSpot.spot_id}/reserve`,
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              driver_id: localStorage.getItem("userId"),
              spot_id: selectedSpot.spot_id,
              start_time: formatDate(startTime),
              end_time: formatDate(endTime),
            }),
          }
        );

        if (!response.ok) {
          throw new Error(`Error: ${response.status} ${response.statusText}`);
        }

        console.log(
          `Spot ${selectedSpot.number} reserved from ${startTime} to ${endTime}!`
        );

        setSelectedSpot(null);
      } catch (error) {
        console.error("Failed to reserve spot:", error);
      }
    } else {
      console.warn("No spot selected for reservation.");
    }
  };

  return (
    <div>
      <header className="m-8">
        <h1 className="text-2xl font-bold text-gray-900">
          Available Parking Spots
        </h1>
        <p className="mt-1 text-gray-600">Find and reserve your parking spot</p>
      </header>

      <div className="bg-white rounded-lg shadow-sm p-6">
        <div className="flex gap-4 mb-6">
          <div className="flex items-center gap-2">
            <div className="w-4 h-4 bg-green-500 rounded-full"></div>
            <span>Available</span>
          </div>
          <div className="flex items-center gap-2">
            <div className="w-4 h-4 bg-red-500 rounded-full"></div>
            <span>Occupied</span>
          </div>
        </div>

        <ParkingGrid spots={spots} onSpotClick={handleSpotClick} />
      </div>

      {selectedSpot && (
        <ReservationModal
          spot={selectedSpot}
          onClose={() => setSelectedSpot(null)}
          onReserve={handleReservation}
        />
      )}
    </div>
  );
}
