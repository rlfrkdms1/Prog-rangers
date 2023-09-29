import React, { useEffect, useCallback } from "react";
import { useLocation, useNavigate } from 'react-router-dom';
import axios from "axios";

export const KakaoRedirect = () => {
  const location = useLocation();
  const navigate = useNavigate();
  let params = new URL(document.location).searchParams;
  let KAKAO_CODE = params.get("code");
  console.log(KAKAO_CODE);

  useEffect(() => {
    fetch(`http://13.124.131.171:8080/api/v1/login/kakao?code=${KAKAO_CODE}`,{
        method: "POST",
        headers: {
          "Content-Type": "application/json;",
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