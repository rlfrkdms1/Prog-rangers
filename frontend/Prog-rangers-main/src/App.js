// import 
// React from 'react';
import { Outlet } from 'react-router-dom';
import { Header } from './components/Header';
import { AuthProvider } from './context/AuthContext';
import './App.css';

function App() {
  return (
<<<<<<< HEAD
=======
	  <div>
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
    <AuthProvider>
      <Header />
      <Outlet />
    </AuthProvider>
<<<<<<< HEAD
=======
	  </div>
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
  );
}

export default App;
