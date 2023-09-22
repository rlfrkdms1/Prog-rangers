// import 
// React from 'react';
import { Outlet } from 'react-router-dom';
import { Header } from './components/Header';
import { AuthProvider } from './context/AuthContext';
import './App.css';

function App() {
  return (
	  <div>
    <AuthProvider>
      <Header />
      <Outlet />
    </AuthProvider>
	  </div>
  );
}

export default App;
