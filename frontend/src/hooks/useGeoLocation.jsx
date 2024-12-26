import { useState, useEffect } from "react";

const useGeolocation = (watch = false) => {
  const [location, setLocation] = useState({
    latitude: null,
    longitude: null,
    error: null,
  });

  useEffect(() => {
    if (!navigator.geolocation) {
      setLocation((prev) => ({
        ...prev,
        error: "Geolocation is not supported by your browser.",
      }));
      return;
    }

    const success = (position) => {
      setLocation({
        latitude: position.coords.latitude,
        longitude: position.coords.longitude,
        error: null,
      });
    };

    const error = (err) => {
      setLocation((prev) => ({
        ...prev,
        error: err.message,
      }));
    };

    const options = {
      enableHighAccuracy: true,
      timeout: 500000,
      maximumAge: 0,
    };

    let watcher;
    if (watch) {
      // Watch position continuously
      watcher = navigator.geolocation.watchPosition(success, error, options);
    } else {
      // Get position once
      navigator.geolocation.getCurrentPosition(success, error, options);
    }

    // Cleanup function
    return () => {
      if (watch && watcher) {
        navigator.geolocation.clearWatch(watcher);
      }
    };
  }, [watch]); // Added watch to dependency array

  return location;
};

export default useGeolocation;
