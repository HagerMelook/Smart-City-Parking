import React from "react";
import LeafletMap from "./LeafletMap";
import proptypes from "prop-types";

export default function LocationPicker({ onChange }) {
  const [position, setPosition] = React.useState([40.7128, -74.006]); // Default to NYC

  const handlePositionChange = async (newPosition) => {
    try {
      const response = await fetch(
        `https://nominatim.openstreetmap.org/reverse?lat=${newPosition[0]}&lon=${newPosition[1]}&format=json`
      );
      const data = await response.json();

      const address = data.address;
      onChange({
        street: `${address.house_number || ""} ${address.road || ""}`.trim(),
        city: address.city || address.town || address.village || "",
        state: address.state || "",
        zipCode: address.postcode || "",
        country: "United States",
      });
      setPosition(newPosition);
    } catch (error) {
      console.error("Error fetching address:", error);
    }
  };

  return (
    <div className="space-y-4">
      <LeafletMap position={position} onPositionChange={handlePositionChange} />
      <p className="text-sm text-gray-500">
        Click on the map to select the parking lot location
      </p>
    </div>
  );
}

LocationPicker.propTypes = {
  value: proptypes.object,
  onChange: proptypes.func,
};
