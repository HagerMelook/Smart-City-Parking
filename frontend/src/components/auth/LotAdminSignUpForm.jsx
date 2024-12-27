import React from "react";
import LocationPicker from "../LocationPicker";
import { useNavigate } from "react-router-dom";
import PropTypes from "prop-types";

export default function LotAdminSignUpForm({onLogin}) {
  const [formData, setFormData] = React.useState({
    name: "",
    email: "",
    password: "",
    lotLocation: "",
    lotName: "",
    capacity: 0,
    spotTypes: {
      regular: 0,
      disabled: 0,
      evCharging: 0,
    },
  });

  const navigate = useNavigate();

  const handleSubmit = async(e) => {
    e.preventDefault();
    console.log("Lot admin signup:", formData);
    console.log(formData.lotLocation)
    try {
      const response = await fetch("https://8080/signup/lot-admin", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });
  
      if (response.ok) {
        const result = await response.json();
        console.log("manager signed up successfully:", result);

        localStorage.setItem("userType", "lot-admin");
        localStorage.setItem("userId", result.userId);
        onLogin();
        navigate("/dashboard");

      } else {
        const error = await response.json();
        console.error("Error during signup:", error);
      }
    } catch (error) {
      console.error("Network error:", error);
    }

  };

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      <div>
        <label htmlFor="name" className="label">
          Full Name
        </label>
        <input
          id="name"
          type="text"
          required
          className="input"
          value={formData.name}
          onChange={(e) => setFormData({ ...formData, name: e.target.value })}
        />
      </div>

      <div>
        <label htmlFor="email" className="label">
          Email
        </label>
        <input
          id="email"
          type="email"
          required
          className="input"
          value={formData.email}
          onChange={(e) => setFormData({ ...formData, email: e.target.value })}
        />
      </div>

      <div>
        <label htmlFor="password" className="label">
          Password
        </label>
        <input
          id="password"
          type="password"
          required
          className="input"
          value={formData.password}
          onChange={(e) =>
            setFormData({ ...formData, password: e.target.value })
          }
        />
      </div>

      <div>
        <label htmlFor="lotName" className="label">
          Parking Lot Name
        </label>
        <input
          id="lotName"
          type="text"
          required
          className="input"
          value={formData.lotName}
          onChange={(e) =>
            setFormData({ ...formData, lotName: e.target.value })
          }
        />
      </div>

      <div>
        <label className="label">Lot Location</label>
        <LocationPicker
          value={formData.lotLocation}
          onChange={(location) =>
            setFormData((prev) => ({
              ...prev,
              lotLocation: {
                latitude: location.latitude,
                longitude: location.longitude,
              },
            }))
          }
        />
      </div>

      <div>
        <label htmlFor="capacity" className="label">
          Total Capacity
        </label>
        <input
          id="capacity"
          type="number"
          required
          min="0"
          className="input"
          value={formData.capacity}
          onChange={(e) =>
            setFormData({ ...formData, capacity: parseInt(e.target.value) })
          }
        />
      </div>

      <div className="space-y-4">
        <h3 className="font-medium">Spot Types</h3>

        <div className="grid grid-cols-3 gap-4">
          <div>
            <label htmlFor="regularSpots" className="label">
              Regular
            </label>
            <input
              id="regularSpots"
              type="number"
              required
              min="0"
              className="input"
              value={formData.spotTypes.regular}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  spotTypes: {
                    ...formData.spotTypes,
                    regular: parseInt(e.target.value),
                  },
                })
              }
            />
          </div>

          <div>
            <label htmlFor="disabledSpots" className="label">
              Disabled
            </label>
            <input
              id="disabledSpots"
              type="number"
              required
              min="0"
              className="input"
              value={formData.spotTypes.disabled}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  spotTypes: {
                    ...formData.spotTypes,
                    disabled: parseInt(e.target.value),
                  },
                })
              }
            />
          </div>

          <div>
            <label htmlFor="evSpots" className="label">
              EV Charging
            </label>
            <input
              id="evSpots"
              type="number"
              required
              min="0"
              className="input"
              value={formData.spotTypes.evCharging}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  spotTypes: {
                    ...formData.spotTypes,
                    evCharging: parseInt(e.target.value),
                  },
                })
              }
            />
          </div>
        </div>
      </div>

      <div>
        <button type="submit" className="btn btn-primary w-full">
          Sign up as Lot Admin
        </button>
      </div>
    </form>
  );
}

LotAdminSignUpForm.propTypes = {
  onLogin: PropTypes.func.isRequired,
};