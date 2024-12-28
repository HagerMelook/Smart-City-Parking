import { useEffect, useRef } from "react";
import PropTypes from "prop-types";

export default function LocationAutocomplete({ value, onChange, className }) {
  const inputRef = useRef(null);
  const autocompleteRef = useRef(null);

  useEffect(() => {
    if (!inputRef.current) return;

    // Initialize Google Places Autocomplete
    autocompleteRef.current = new google.maps.places.Autocomplete(
      inputRef.current,
      {
        types: ["address"],
        componentRestrictions: { country: "US" }, // Restrict to US addresses
        fields: ["formatted_address", "geometry", "place_id"],
      }
    );

    // Add place_changed event listener
    autocompleteRef.current.addListener("place_changed", () => {
      const place = autocompleteRef.current?.getPlace();
      if (place?.formatted_address) {
        onChange(place.formatted_address, place.place_id);
      }
    });

    return () => {
      // Cleanup
      google.maps.event.clearInstanceListeners(autocompleteRef.current);
    };
  }, [onChange]);

  return (
    <input
      ref={inputRef}
      type="text"
      value={value}
      onChange={(e) => onChange(e.target.value)}
      placeholder="Enter parking lot address"
      className={className}
    />
  );
}

// Define PropTypes for the component
LocationAutocomplete.propTypes = {
  value: PropTypes.string.isRequired,
  onChange: PropTypes.func.isRequired,
  className: PropTypes.string,
};

LocationAutocomplete.defaultProps = {
  className: "",
};
