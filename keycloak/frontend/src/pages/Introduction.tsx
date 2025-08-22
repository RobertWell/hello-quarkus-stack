import React from 'react';

const Introduction: React.FC = () => {
  return (
    <div className="page">
      <h1>Introduction</h1>
      <div className="intro-content">
        <h2>About This Application</h2>
        <p>
          This is a full-stack application built with:
        </p>
        <ul>
          <li><strong>Backend:</strong> Quarkus 3.20.1 - A Kubernetes Native Java stack</li>
          <li><strong>Frontend:</strong> React with TypeScript - A popular JavaScript library for building user interfaces</li>
          <li><strong>Architecture:</strong> Separate frontend and backend services with CORS configuration</li>
        </ul>
        
        <h3>Features</h3>
        <ul>
          <li>Frontend served on port 3000 during development</li>
          <li>Backend API served on port 8124</li>
          <li>Production build copies frontend to backend resources</li>
          <li>Cross-origin resource sharing (CORS) configured for seamless communication</li>
          <li>Responsive navigation between pages</li>
        </ul>
        
        <h3>Development</h3>
        <p>
          Run <code>npm start</code> in the frontend directory to start the React development server.
          Run <code>./mvnw quarkus:dev</code> in the backend directory to start the Quarkus development server.
        </p>
      </div>
    </div>
  );
};

export default Introduction;