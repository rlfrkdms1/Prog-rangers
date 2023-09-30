// import React from 'react';
import { Outlet } from 'react-router-dom';
import { Header } from './components/Header';
import { IsLoginProvider } from './context/AuthContext';
import './App.css';

function App() {
  return (
    <div>
      <IsLoginProvider>
        <Header />
        <Outlet />
      </IsLoginProvider>
    </div>
  );
}

export default App;
