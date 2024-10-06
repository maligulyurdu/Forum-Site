import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import './App.css';
import Navbar from './components/Navbar/Navbar';
import Home from './components/Home/Home';
import User from './components/User/User';
import Auth from './components/Auth/Auth';

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/users/:userId" element={<User />} />
          <Route path="/auth" element={localStorage.getItem("currentUser") != null ? <Navigate to="/"/> : <Auth/>} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;