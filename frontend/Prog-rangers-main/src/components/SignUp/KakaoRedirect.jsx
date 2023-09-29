import React, { useEffect, useCallback } from "react";
import { useLocation, useNavigate } from 'react-router-dom';

export const KakaoRedirect = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const KAKAO_CODE = location.search.split('=')[1];

  useEffect(() => {
    fetch(`http://13.124.131.171:8080/api/login/kakao?code=${KAKAO_CODE}`,{
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