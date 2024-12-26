import AuthLayout from "../../components/auth/AuthLayout";
import LotAdminSignUpForm from "../../components/auth/LotAdminSignUpForm";

export default function LotAdminSignUpPage() {
  return (
    <AuthLayout
      title="Sign up as Lot Admin"
      subtitle="Create your parking lot administrator account"
    >
      <LotAdminSignUpForm />
    </AuthLayout>
  );
}
