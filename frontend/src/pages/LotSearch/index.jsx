import { useEffect, useState } from "react";
import { MapContainer, TileLayer, Marker, Popup, useMap } from "react-leaflet";
import L from "leaflet";
import useGeolocation from "../../hooks/useGeoLocation";
import { Card, Input, Button } from "@mui/material";
import proptypes from "prop-types";
import { MapPin } from "lucide-react";

const BASE_URL = "https://nominatim.openstreetmap.org/search?";
const customIcon = L.icon({
  iconUrl:
    "https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-red.png", // Replace with your custom marker image URL
  iconSize: [25, 41], // Size of the icon
  iconAnchor: [12, 41], // Anchor point of the icon
  popupAnchor: [1, -34], // Popup anchor point
  shadowUrl:
    "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-shadow.png", // Optional shadow image
  shadowSize: [41, 41], // Shadow size
});

const MapWithSearch = () => {
  const { latitude, longitude } = useGeolocation(false);
  const [position, setPosition] = useState([30.037491, 31.236319]);
  const [searchQuery, setSearchQuery] = useState("");
  const [searchResults, setSearchResults] = useState([]);
  const [selectedLocation, setSelectedLocation] = useState(null);
  const [parkingLots, setParkingLots] = useState([]);

  useEffect(() => {
    async function fetchParkingLots() {
      // const response = await fetch("https://localhost:8080/lots");
      const response = await fetch("http://localhost:3001/parking_lots");

      const data = await response.json();
      console.log(data);
      setParkingLots(data);
    }

    fetchParkingLots();
  }, []);

  useEffect(() => {
    if (latitude && longitude) {
      setPosition([latitude, longitude]);
    }
  }, [latitude, longitude]);

  const handleSearch = (e) => {
    e.preventDefault();
    const params = {
      q: searchQuery,
      format: "json",
      addressdetails: 1,
      polygon_geojson: 0,
    };

    const queryString = new URLSearchParams(params).toString();
    const requestOptions = {
      method: "GET",
      redirect: "follow",
    };

    fetch(`${BASE_URL}${queryString}`, requestOptions)
      .then((response) => response.json())
      .then((result) => {
        console.log(result);
        setSearchResults(result);
      })
      .catch((error) => console.log("error", error));
  };

  const handleLocationSelect = (result) => {
    setSelectedLocation(result);
    setPosition([parseFloat(result.lat), parseFloat(result.lon)]);
    setSearchResults([]);
  };

  return (
    <div className="flex flex-col gap-4 w-full h-screen p-4">
      <Card className="p-4">
        <form onSubmit={handleSearch} className="flex gap-2 mb-4">
          <Input
            type="text"
            placeholder="Search for parking lots"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            fullWidth
          />
          <Button type="submit" variant="contained">
            Search
          </Button>
        </form>

        {searchResults.length > 0 && (
          <div className="h-[300px] overflow-y-auto scrollbar-thin scrollbar-thumb-gray-400 scrollbar-track-gray-100 mt-4">
            <div className="space-y-4 p-2">
              {searchResults.map((result) => (
                <Card
                  key={result.place_id}
                  className={`
                    cursor-pointer transition-all duration-200 hover:bg-gray-50
                    ${
                      selectedLocation?.place_id === result.place_id
                        ? "ring-2 ring-blue-500"
                        : ""
                    }
                  `}
                  onClick={() => handleLocationSelect(result)}
                >
                  <div className="p-4">
                    <div className="flex gap-2">
                      <MapPin className="w-5 h-5 mt-1 flex-shrink-0 text-gray-500" />
                      <div className="flex-1">
                        <div className="font-medium text-sm mb-2">
                          {result.display_name
                            .split(", ")
                            .slice(0, 2)
                            .join(", ")}
                        </div>
                        <div className="text-gray-600">
                          <div className="text-sm mb-1">
                            {result.type} •{" "}
                            {result.address?.city || result.address?.town}
                          </div>
                          <div className="text-xs">
                            {[
                              result.address?.road,
                              result.address?.suburb,
                              result.address?.postcode,
                              result.address?.country,
                            ]
                              .filter(Boolean)
                              .join(", ")}
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </Card>
              ))}
            </div>
          </div>
        )}
      </Card>

      <div className="flex-1 w-full rounded-lg overflow-hidden">
        <MapContainer
          className="h-full w-full"
          center={position}
          zoom={9}
          scrollWheelZoom={true}
        >
          <TileLayer
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            url="https://api.maptiler.com/maps/basic-v2/{z}/{x}/{y}.png?key=g5FqKg3wJCiXfMjqmozR"
          />
          {selectedLocation ? (
            <Marker
              position={[
                parseFloat(selectedLocation.lat),
                parseFloat(selectedLocation.lon),
              ]}
            >
              <Popup>{selectedLocation.display_name}</Popup>
            </Marker>
          ) : (
            <Marker position={position}>
              <Popup>Your current location</Popup>
            </Marker>
          )}
          {parkingLots.map((lot) => (
            <Marker
              key={lot.id}
              position={[lot.latitude, lot.longitude]}
              icon={customIcon}
            >
              <Popup>{lot.name}</Popup>
            </Marker>
          ))}
          <ChangeView center={position} zoom={9} />
        </MapContainer>
      </div>
    </div>
  );
};

function ChangeView({ center, zoom }) {
  const map = useMap();
  map.setView(center, zoom);
  const egyptBounds = [
    [22.0, 24.7], // Southwest corner (latitude, longitude)
    [31.8, 36.9], // Northeast corner (latitude, longitude)
  ];

  map.setMaxBounds(egyptBounds);

  map.on("drag", function () {
    map.panInsideBounds(egyptBounds, { animate: true });
  });
  return null;
}

ChangeView.propTypes = {
  center: proptypes.arrayOf(proptypes.number).isRequired,
  zoom: proptypes.number.isRequired,
};

export default MapWithSearch;
