import React from "react";
import LocationPicker from "../LocationPicker";

export default function LotAdminSignUpForm() {
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
    costPerHour: 0,
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    // TODO: Implement signup logic
    console.log("Lot admin signup:", formData);
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
            setFormData((prev) => ({ ...prev, lotLocation: location }))
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
        <label htmlFor="costPerHour" className="label">
          Cost per Hour ($)
        </label>
        <input
          id="costPerHour"
          type="number"
          required
          min="0"
          step="0.01"
          className="input"
          value={formData.costPerHour}
          onChange={(e) =>
            setFormData({
              ...formData,
              costPerHour: parseFloat(e.target.value),
            })
          }
        />
      </div>

      <div>
        <button type="submit" className="btn btn-primary w-full">
          Sign up as Lot Admin
        </button>
      </div>
    </form>
  );
}
