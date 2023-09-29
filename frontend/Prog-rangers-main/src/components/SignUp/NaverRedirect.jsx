import axios from "axios";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export const NaverRedirect = () => {
  const navigate = useNavigate();
  let params = new URL(document.location).searchParams;
  let code = params.get("code");
  let state = params.get("state");
  console.log(code);
  console.log(state);

  useEffect(() => {
    fetch(`http://13.124.131.171:8080/api/v1/login/naver?code=${code}&state=${state}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json;",
      },
    })
    .then(res => {
      return res.json();
    })
    .then(data => {
      console.log(data);
      localStorage.setItem('token', data.accessToken);
      navigate("/");
    })
    .catch(error => {
      console.log('Error:', error);
    })

  }, []);

  return(
    <>
    </>
  )  
}