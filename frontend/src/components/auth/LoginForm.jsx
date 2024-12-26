import React from "react";
import { Link } from "react-router-dom";

export default function LoginForm() {
  const [formData, setFormData] = React.useState({
    email: "",
    password: "",
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    // TODO: Implement login logic
    console.log("Login:", formData);
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
