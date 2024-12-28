import React from "react";
import { useNavigate } from "react-router-dom";
import PropTypes from "prop-types";

export default function DriverSignUpForm({onLogin}) {
  const [formData, setFormData] = React.useState({
    full_name: "",
    email: "",
    password: "",
    license_plate: "",
    card_type: "credit_card",
    card_num: "",
    expiry_date: "",
    cvv: "",
  });

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log("Driver signup:", formData);

    try {
      const response = await fetch("http://localhost:8080/signup/driver", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });
  
      if (response.ok) {
        const result = await response.json();
        console.log("Driver signed up successfully:", result);

        localStorage.setItem("userType", "driver");
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
            setFormData({ ...formData, license_plate: e.target.value })
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
            value={formData.cardType}
            onChange={(e) =>
              setFormData({...formData, card_type: e.target.value})
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
            value={formData.card_num}
            onChange={(e) =>
              setFormData({...formData, card_num: e.target.value})
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
              value={formData.expiryDate}
              onChange={(e) =>
                setFormData({...formData, expiry_date: e.target.value})
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
              value={formData.cvv}
              onChange={(e) =>
                setFormData({...formData, cvv: e.target.value})
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

DriverSignUpForm.propTypes = {
  onLogin: PropTypes.func.isRequired,
};