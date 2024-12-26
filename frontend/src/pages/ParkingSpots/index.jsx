import ParkingGrid from "../../components/ParkingGrid";
import ReservationModal from "../../components/ReservationModal";
import { useState } from "react";

// Mock data for demonstration
const mockSpots = [
  {
    id: "1",
    number: "A1",
    isOccupied: false,
    price: 5,
    type: "standard",
    level: 1,
  },
  {
    id: "2",
    number: "A2",
    isOccupied: true,
    price: 5,
    type: "standard",
    level: 1,
  },
  {
    id: "3",
    number: "A3",
    isOccupied: false,
    price: 7,
    type: "electric",
    level: 1,
  },
  {
    id: "4",
    number: "A4",
    isOccupied: false,
    price: 6,
    type: "handicap",
    level: 1,
  },
  {
    id: "5",
    number: "B1",
    isOccupied: false,
    price: 4,
    type: "standard",
    level: 2,
  },
  {
    id: "6",
    number: "B2",
    isOccupied: false,
    price: 4,
    type: "standard",
    level: 2,
  },
  {
    id: "7",
    number: "B3",
    isOccupied: true,
    price: 6,
    type: "electric",
    level: 2,
  },
  {
    id: "8",
    number: "B4",
    isOccupied: false,
    price: 5,
    type: "standard",
    level: 2,
  },
];

export default function ParkingSpots() {
  const [spots, setSpots] = useState(mockSpots);
  const [selectedSpot, setSelectedSpot] = useState(null);

  const handleSpotClick = (spot) => {
    if (!spot.isOccupied) {
      setSelectedSpot(spot);
    }
  };

  const handleReservation = (hours) => {
    if (selectedSpot) {
      setSpots(
        spots.map((spot) =>
          spot.id === selectedSpot.id ? { ...spot, isOccupied: true } : spot
        )
      );
      setSelectedSpot(null);
      alert(`Spot ${selectedSpot.number} reserved for ${hours} hours!`);
    }
  };

  return (
    <div>
      <header className="mb-8">
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
