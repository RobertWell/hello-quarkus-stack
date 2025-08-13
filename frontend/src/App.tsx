import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Home from './pages/Home';
import Introduction from './pages/Introduction';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <nav className="navbar">
          <div className="nav-links">
            <Link to="/" className="nav-link">Home</Link>
            <Link to="/introduction" className="nav-link">Introduction</Link>
          </div>
        </nav>
        <main>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/introduction" element={<Introduction />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;