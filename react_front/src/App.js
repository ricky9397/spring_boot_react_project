import React from 'react';
import './App.css';
import { Route, Routes } from 'react-router-dom';
import Login from './components/AuthForm';
import Phone from './view/PhoneForm';
import Jender from './view/JenderForm';

const App = () => {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/phone" element={<Phone />} />
      <Route path="/jender" element={<Jender />} />
      {/* <Route element={<AuthLayout />}>
        <Route path="/auth" element={<AuthPage />} />
        <Route path="/auth2" element={<AuthPage2 />} />
      </Route> */}
    </Routes>
  );
}

export default App;
