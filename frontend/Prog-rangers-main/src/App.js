// import 
// React from 'react';
import { Outlet } from 'react-router-dom';
import { Header } from './components/Header';
import { IsLoginProvider } from './context/AuthContext';
import './App.css';

function App() {
  return (
<<<<<<< HEAD
    <AuthProvider>
      <Header />
      <Outlet />
    </AuthProvider>
=======
    <div>
      <IsLoginProvider>
        <Header />
        <Outlet />
      </IsLoginProvider>
    </div>
>>>>>>> e1cb18d0af8a45d3c312367c939a2bfacdeb7498
  );
}

export default App;
