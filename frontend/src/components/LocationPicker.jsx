import React from "react";
import LeafletMap from "./LeafletMap";
import proptypes from "prop-types";

export default function LocationPicker({ onChange }) {
  const [position, setPosition] = React.useState([40.7128, -74.006]); 

  const handlePositionChange = (newPosition) => {
    setPosition(newPosition); 
    onChange({
      latitude: newPosition[0],
      longitude: newPosition[1],
    }); 
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
  onChange: proptypes.func.isRequired, 
};

