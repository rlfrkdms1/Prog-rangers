// import React from 'react';
import { Outlet } from 'react-router-dom';
import { Header } from './components/Header';
import { IsLoginProvider } from './context/AuthContext';
import './App.css';
import { SearchProvider } from './context/SearchContext';

function App() {
  return (
    <div>
      <IsLoginProvider>
        <SearchProvider>
          <Header />
          <Outlet />
        </SearchProvider>
      </IsLoginProvider>
    </div>
  );
}

export default App;
