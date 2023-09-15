import React, { useEffect } from "react";
import { useLocation, useNavigate } from 'react-router-dom';
import { KAKAO_CLIENT_ID, KAKAO_REDIRECT_URI, KAKAO_AUTH_URL } from "./OAuth";

export const KakaoReirect = () => {
  const location = useLocation();
  const navigate = useNavigate();
  // let code = new URL(window.location.href).searchParams.get("code");
  const KAKAO_CODE = location.search.split('=')[1];


  useEffect(() => {
    fetch(`http://localhost:3000/signin/kakao/redirect?code=${KAKAO_CODE}`, { 
      method: 'GET',
    })
      .then(res => res.json())
      .then(data => {
        localStorage.setItem('token', data.token);
        navigate('/');
      });
  }, []);
  return(
    <>
    </>
  );
};