import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import useGeolocation from "../hooks/useGeoLocation";

import "./Map.css";
import { useEffect, useState } from "react";

const Map = () => {
  const { latitude, longitude, error } = useGeolocation();
  console.log(latitude, longitude, error);

  const [position, setPosition] = useState([0, 0]);

  useEffect(() => {
    if (latitude && longitude) {
      setPosition([latitude, longitude]);
    }
  }, [latitude, longitude]);

  return (
    <MapContainer
      className="map"
      center={position}
      zoom={7}
      scrollWheelZoom={false}
    >
      <TileLayer
        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        url="https://api.maptiler.com/maps/basic-v2/{z}/{x}/{y}.png?key=g5FqKg3wJCiXfMjqmozR"
      />
      <Marker position={position}>
        <Popup>
          A pretty CSS3 popup. <br /> Easily customizable.
        </Popup>
      </Marker>
    </MapContainer>
  );
};
export default Map;
