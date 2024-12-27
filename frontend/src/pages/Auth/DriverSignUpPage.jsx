import AuthLayout from "../../components/auth/AuthLayout";
import DriverSignUpForm from "../../components/auth/DriverSignUpForm";
import PropTypes from "prop-types";

export default function DriverSignUpPage({onLogin}) {
  return (
    <AuthLayout title="Sign up as Driver" subtitle="Create your driver account">
      <DriverSignUpForm onLogin ={onLogin}/>
    </AuthLayout>
  );
}

DriverSignUpPage.propTypes = {
  onLogin: PropTypes.func.isRequired,
};