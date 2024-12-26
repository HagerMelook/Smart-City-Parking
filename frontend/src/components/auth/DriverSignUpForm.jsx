import React from "react";

export default function DriverSignUpForm() {
  const [formData, setFormData] = React.useState({
    name: "",
    email: "",
    password: "",
    licensePlate: "",
    paymentMethod: {
      type: "credit_card",
      cardNumber: "",
      expiryDate: "",
      cvv: "",
    },
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    // TODO: Implement signup logic
    console.log("Driver signup:", formData);
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
        <label htmlFor="licensePlate" className="label">
          License Plate
        </label>
        <input
          id="licensePlate"
          type="text"
          required
          className="input"
          value={formData.licensePlate}
          onChange={(e) =>
            setFormData({ ...formData, licensePlate: e.target.value })
          }
        />
      </div>

      <div className="space-y-4">
        <h3 className="font-medium">Payment Method</h3>

        <div>
          <label htmlFor="cardType" className="label">
            Card Type
          </label>
          <select
            id="cardType"
            className="input"
            value={formData.paymentMethod.type}
            onChange={(e) =>
              setFormData({
                ...formData,
                paymentMethod: {
                  ...formData.paymentMethod,
                  type: e.target.value,
                },
              })
            }
          >
            <option value="credit_card">Credit Card</option>
            <option value="debit_card">Debit Card</option>
          </select>
        </div>

        <div>
          <label htmlFor="cardNumber" className="label">
            Card Number
          </label>
          <input
            id="cardNumber"
            type="text"
            required
            className="input"
            value={formData.paymentMethod.cardNumber}
            onChange={(e) =>
              setFormData({
                ...formData,
                paymentMethod: {
                  ...formData.paymentMethod,
                  cardNumber: e.target.value,
                },
              })
            }
          />
        </div>

        <div className="grid grid-cols-2 gap-4">
          <div>
            <label htmlFor="expiryDate" className="label">
              Expiry Date
            </label>
            <input
              id="expiryDate"
              type="text"
              required
              placeholder="MM/YY"
              className="input"
              value={formData.paymentMethod.expiryDate}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  paymentMethod: {
                    ...formData.paymentMethod,
                    expiryDate: e.target.value,
                  },
                })
              }
            />
          </div>
          <div>
            <label htmlFor="cvv" className="label">
              CVV
            </label>
            <input
              id="cvv"
              type="text"
              required
              className="input"
              value={formData.paymentMethod.cvv}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  paymentMethod: {
                    ...formData.paymentMethod,
                    cvv: e.target.value,
                  },
                })
              }
            />
          </div>
        </div>
      </div>

      <div>
        <button type="submit" className="btn btn-primary w-full">
          Sign up as Driver
        </button>
      </div>
    </form>
  );
}
