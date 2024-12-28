import React from "react";
import { Link, useNavigate } from "react-router-dom";
import PropTypes from "prop-types";

export default function LoginForm({ onLogin }) {
  const [formData, setFormData] = React.useState({
    email: "",
    password: "",
  });

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log("Login:", formData);

    try {
      const response = await fetch("https://localhost:8080/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        const result = await response.json();
        console.log("Logged in up successfully:", result);

        localStorage.setItem("userType", result.userType);
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
        <label htmlFor="email" className="label">
          Email address
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
        <button type="submit" className="btn btn-primary w-full">
          Sign in
        </button>
      </div>

      <div className="text-sm text-center">
        <Link to="/signup" className="text-primary-600 hover:text-primary-500">
          Don&apos;t have an account? Sign up
        </Link>
      </div>
    </form>
  );
}

LoginForm.propTypes = {
  onLogin: PropTypes.func.isRequired,
};
