import AuthLayout from "../../components/auth/AuthLayout";
import LotAdminSignUpForm from "../../components/auth/LotAdminSignUpForm";
import PropTypes from "prop-types";

export default function LotAdminSignUpPage({onLogin}) {
  return (
    <AuthLayout
      title="Sign up as Lot Admin"
      subtitle="Create your parking lot administrator account"
    >
      <LotAdminSignUpForm onLogin={onLogin} />
    </AuthLayout>
  );
}

LotAdminSignUpPage.propTypes = {
  onLogin: PropTypes.func.isRequired,
};