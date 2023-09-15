import React, { useEffect } from "react";
import { useLocation, useNavigate } from 'react-router-dom';
import { KAKAO_AUTH_URL } from "./OAuth";

export const KakaoReirect = () => {
  const location = useLocation();
  const navigate = useNavigate();
  let code = new URL(window.location.href).searchParams.get("code");
  
  useEffect(() => {
    fetch({KAKAO_AUTH_URL}, { 
      method: 'GET',
    })
      .then(res => res.json())
      .then(data => {
        localStorage.setItem('token', data.token);
        navigate('/');
      });
  }, [])
  return(
    <>
    </>
  );
};