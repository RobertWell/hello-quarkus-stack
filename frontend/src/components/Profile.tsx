import React from 'react';
import { useAuth0 } from '@auth0/auth0-react';

const Profile: React.FC = () => {
  const { user, isAuthenticated, isLoading } = useAuth0();

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (!isAuthenticated) {
    return null;
  }

  return (
    <div className="profile">
      <img src={user?.picture} alt={user?.name} className="profile-picture" />
      <h3>Welcome, {user?.name}!</h3>
      <p>Email: {user?.email}</p>
    </div>
  );
};

export default Profile;