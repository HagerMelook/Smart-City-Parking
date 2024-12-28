import React from "react";
import LocationPicker from "../LocationPicker";
import { useNavigate } from "react-router-dom";
import PropTypes from "prop-types";

export default function LotAdminSignUpForm({onLogin}) {
  const [formData, setFormData] = React.useState({
    full_name: "",
    email: "",
    password: "",
    lotData: {
      name: "",
      latitude:0,
      longitude:0,
      capacity: 0,
      regular_cap: 0,
      disabled_cap: 0,
      ev_charging_cap: 0,
    },
  });

  const navigate = useNavigate();

  const handleSubmit = async(e) => {
    e.preventDefault();
    console.log("Lot admin signup:", formData);
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
          value={formData.full_name}
          onChange={(e) => setFormData({ ...formData, full_name: e.target.value })}
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
          value={formData.lotData.name}
          onChange={(e) =>
            setFormData({
              ...formData,
              lotData: {
                ...formData.lotData,
                name: e.target.value,
              },
            })
          }
        />
      </div>

      <div>
        <label className="label">Lot Location</label>
        <LocationPicker

          value={{ latitude: formData.lotData.latitude, longitude: formData.lotData.longitude }}
          onChange={(location) =>
            setFormData((prev) => ({
              ...prev,
              lotData: {
                ...prev.lotData,
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
          value={formData.lotData.capacity}
          onChange={(e) =>
            setFormData({
              ...formData,
              lotData: {
                ...formData.lotData,
                capacity: parseInt(e.target.value),
              },
            })
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
              value={formData.lotData.regular_cap}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  lotData: {
                    ...formData.lotData,
                    regular_cap: parseInt(e.target.value),
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
              value={formData.lotData.disabled_cap}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  lotData: {
                    ...formData.lotData,
                    disabled_cap: parseInt(e.target.value),
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
              value={formData.lotData.ev_charging_cap}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  lotData: {
                    ...formData.lotData,
                    ev_charging_cap: parseInt(e.target.value),
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