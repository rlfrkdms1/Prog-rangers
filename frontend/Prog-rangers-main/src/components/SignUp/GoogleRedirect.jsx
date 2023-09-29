import axios from "axios";
import { useEffect } from "react";
import { Navigate, useNavigate } from "react-router-dom";

export const GoogleRedirect = () => {
  const navigate = useNavigate();
  const href = window.location.href;
  let params = new URL(document.location).searchParams;
  let GOOGLE_CODE = params.get("code");
  console.log(GOOGLE_CODE);

  useEffect(() => {
    fetch(`http://13.124.131.171:8080/api/v1/login/google?code=${GOOGLE_CODE}`,{
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