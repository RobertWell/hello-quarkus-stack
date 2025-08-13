import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { AuthProvider, useAuth } from './contexts/AuthContext';
import ProtectedRoute from './components/ProtectedRoute';
import Home from './pages/Home';
import Introduction from './pages/Introduction';
import './App.css';

function AppContent() {
  const { isAuthenticated, user, logout } = useAuth();

  return (
    <Router>
      <div className="App">
        <nav className="navbar">
          <div className="nav-links">
            <Link to="/" className="nav-link">Home</Link>
            <Link to="/introduction" className="nav-link">Introduction</Link>
          </div>
          <div className="auth-section">
            {isAuthenticated && (
              <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
                <span>Welcome, {user?.username}!</span>
                <button onClick={logout} className="nav-button">Logout</button>
              </div>
            )}
          </div>
        </nav>
        <main>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route 
              path="/introduction" 
              element={
                <ProtectedRoute>
                  <Introduction />
                </ProtectedRoute>
              } 
            />
            <Route path="/actuator/health" element={<div>Health endpoint is backend only</div>} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

function App() {
  return (
    <AuthProvider>
      <AppContent />
    </AuthProvider>
  );
}

export default App;