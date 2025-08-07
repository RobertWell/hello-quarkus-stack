import React, { useState, useEffect } from 'react';

const Home: React.FC = () => {
  const [message, setMessage] = useState<string>('');
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchMessage = async () => {
      try {
        const response = await fetch('/api/hello');
        const data = await response.json();
        setMessage(data.message);
      } catch (error) {
        console.error('Error fetching message:', error);
        setMessage('Failed to connect to backend');
      } finally {
        setLoading(false);
      }
    };

    fetchMessage();
  }, []);

  return (
    <div className="page">
      <h1>Welcome Home</h1>
      <p>This is the home page of our Quarkus + React application.</p>
      <div className="backend-test">
        <h3>Backend Connection Test:</h3>
        {loading ? (
          <p>Loading...</p>
        ) : (
          <p>{message}</p>
        )}
      </div>
    </div>
  );
};

export default Home;