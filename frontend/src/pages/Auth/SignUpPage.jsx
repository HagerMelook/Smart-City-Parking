import { Link } from "react-router-dom";
import AuthLayout from "../../components/auth/AuthLayout";

export default function SignUpPage() {
  return (
    <AuthLayout title="Create an account" subtitle="Choose your account type">
      <div className="space-y-4">
        <Link
          to="/signup/driver"
          className="block w-full btn btn-primary text-center"
        >
          Sign up as Driver
        </Link>

        <Link
          to="/signup/lot-admin"
          className="block w-full btn bg-gray-100 hover:bg-gray-200 text-gray-700 text-center"
        >
          Sign up as Lot Admin
        </Link>

        <div className="text-sm text-center">
          <Link to="/login" className="text-primary-600 hover:text-primary-500">
            Already have an account? Sign in
          </Link>
        </div>
      </div>
    </AuthLayout>
  );
}
