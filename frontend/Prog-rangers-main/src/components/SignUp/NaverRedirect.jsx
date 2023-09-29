import axios from "axios";
import { useEffect } from "react";
import { Navigate } from "react-router-dom";

export const NaverRedirect = () => {
  const href = window.location.href;
  let params = new URL(document.location).searchParams;
  let code = params.get("code");
  let state = params.get("state");

  // const naver = async () => {
  //   try{
  //     const result = await axios.get(
  //       `http://13.124.131.171:8080/prog-rangers/login/naver?code=${code}&state=${state}`
  //     );
  //   }catch(error){
  //     console.log("error", error);
  //   }
  // }
  // useEffect(() => {
  //   naver
  // }, []);
  useEffect(() => {
    fetch(`http://13.124.131.171:8080/prog-rangers/login/naver?code=${code}&state=${state}`, {
      method: "POST",
    })
    .then(res => {
      return res.json();
    })
    .then(data => {
      console.log(data);
      localStorage.setItem('token', data.accessToken);
      Navigate("/");
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