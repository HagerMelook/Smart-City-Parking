import { useEffect, useState } from "react";
import { MapContainer, TileLayer, Marker, Popup, useMap } from "react-leaflet";
import useGeolocation from "../../hooks/useGeoLocation";
import { Card } from "@mui/material";
import { Input } from "@mui/material";
import { Button } from "@mui/material";
import proptypes from "prop-types";

const MapWithSearch = () => {
  const { latitude, longitude, error } = useGeolocation();
  console.log(latitude, longitude, error);
  const [position, setPosition] = useState([0, 0]);
  const [searchQuery, setSearchQuery] = useState("");

  useEffect(() => {
    if (latitude && longitude) {
      setPosition([latitude, longitude]);
    }
  }, [latitude, longitude]);

  const handleSearch = (e) => {
    e.preventDefault();
    // Handle search functionality here
    console.log("Searching for:", searchQuery);
  };

  return (
    <div className="flex flex-col gap-4 w-full h-screen p-4">
      <Card className="p-4">
        <form onSubmit={handleSearch} className="flex gap-2">
          <Input
            type="text"
            placeholder="Search for parking lots"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="flex-1"
          />
          <Button type="submit">Search</Button>
        </form>
      </Card>

      <div className="flex-1 w-full rounded-lg overflow-hidden">
        <MapContainer
          className="h-full w-full"
          center={position}
          zoom={7}
          scrollWheelZoom={false}
        >
          <TileLayer
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            url="https://api.maptiler.com/maps/basic-v2/{z}/{x}/{y}.png?key=g5FqKg3wJCiXfMjqmozR"
          />
          <Marker position={position}>
            <Popup>Your current location</Popup>
          </Marker>
          <ChangeView center={position} zoom={15} />
        </MapContainer>
      </div>
    </div>
  );
};

function ChangeView({ center, zoom }) {
  const map = useMap();
  map.setView(center, zoom);
  return null;
}

ChangeView.propTypes = {
  center: proptypes.arrayOf(proptypes.number).isRequired,
  zoom: proptypes.number.isRequired,
};

export default MapWithSearch;
