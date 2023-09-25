// import React from 'react';
import { Outlet } from 'react-router-dom';
import { Header } from './components/Header';
import { AuthProvider } from './context/AuthContext';
import './App.css';

function App() {
  return (
    <AuthProvider>
      <Header />
      <Outlet />
    </AuthProvider>
  );
}

export default App;
