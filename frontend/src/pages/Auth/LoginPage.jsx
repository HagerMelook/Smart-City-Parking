import AuthLayout from "../../components/auth/AuthLayout";
import LoginForm from "../../components/auth/LoginForm";
import PropTypes from "prop-types";

export default function LoginPage({onLogin}) {
  return (
    <AuthLayout title="Welcome back" subtitle="Sign in to your account">
      <LoginForm onLogin = {onLogin} />
    </AuthLayout>
  );
}

LoginPage.propTypes = {
  onLogin: PropTypes.func.isRequired,
};