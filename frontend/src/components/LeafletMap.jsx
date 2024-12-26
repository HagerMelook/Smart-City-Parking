import { MapContainer, TileLayer, Marker, useMapEvents } from "react-leaflet";
import proptypes from "prop-types";

function LocationMarker({ position, onPositionChange }) {
  useMapEvents({
    click(e) {
      onPositionChange([e.latlng.lat, e.latlng.lng]);
    },
  });

  return <Marker position={position} />;
}

export default function LeafletMap({ position, onPositionChange }) {
  return (
    <MapContainer
      center={position}
      zoom={13}
      scrollWheelZoom={false}
      className="h-[300px] w-full rounded-lg"
    >
      <TileLayer
        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
      />
      <LocationMarker position={position} onPositionChange={onPositionChange} />
    </MapContainer>
  );
}

LeafletMap.propTypes = {
  position: proptypes.arrayOf(proptypes.number),
  onPositionChange: proptypes.func,
};

LocationMarker.propTypes = {
  position: proptypes.arrayOf(proptypes.number),
  onPositionChange: proptypes.func,
};
