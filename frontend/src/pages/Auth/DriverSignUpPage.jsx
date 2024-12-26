import AuthLayout from "../../components/auth/AuthLayout";
import DriverSignUpForm from "../../components/auth/DriverSignUpForm";

export default function DriverSignUpPage() {
  return (
    <AuthLayout title="Sign up as Driver" subtitle="Create your driver account">
      <DriverSignUpForm />
    </AuthLayout>
  );
}
