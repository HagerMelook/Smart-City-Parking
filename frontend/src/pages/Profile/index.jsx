import ProfileForm from "./components/ProfileForm";

export default function Profile() {
  const handleProfileUpdate = (data) => {
    console.log("Profile updated:", data);
    // TODO: Implement profile update logic
  };

  return (
    <div className="max-w-2xl mx-auto">
      <header className="m-8">
        <h1 className="text-2xl font-bold text-gray-900">Profile Settings</h1>
        <p className="mt-1 text-gray-600">
          Manage your account settings and preferences
        </p>
      </header>

      <div className="bg-white rounded-lg shadow-sm p-6">
        <ProfileForm onSubmit={handleProfileUpdate} />
      </div>
    </div>
  );
}
