import React, { useEffect, useCallback } from "react";
import { useLocation, useNavigate } from 'react-router-dom';
import { KAKAO_CLIENT_ID, KAKAO_REDIRECT_URI, KAKAO_AUTH_URL } from "./OAuth";
import axios from "axios";
import qs from "qs";

export const KakaoReirect = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const KAKAO_CODE = location.search.split('=')[1];

  useEffect(() => {
    fetch(`http://13.124.131.171:8080/prog-rangers/login/kakao?code=${KAKAO_CODE}`,{
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded;",
        },
      })
      .then(res =>{
          return res.json();
        })
      .then(data => {
        console.log(data);
        localStorage.setItem('token', data.accessToken);
        navigate("/");
      })
      .catch(error =>{
        console.log('Error:', error);
      })

  }, []);


  return(
    <>
    </>
  );
};