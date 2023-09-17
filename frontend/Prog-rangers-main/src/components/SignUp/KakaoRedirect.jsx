import React, { useEffect } from "react";
import { useLocation, useNavigate } from 'react-router-dom';
import { KAKAO_CLIENT_ID, KAKAO_REDIRECT_URI, KAKAO_AUTH_URL } from "./OAuth";
import axios from "axios";
import qs from "qs";

export const KakaoReirect = () => {
  const location = useLocation();
  const navigate = useNavigate();
  // let code = new URL(window.location.href).searchParams.get("code");
  const KAKAO_CODE = location.search.split('=')[1];

  // const getToken = async () => {
  //   let access_token = {
  //     code: KAKAO_CODE,
  //   };

  //   try{
  //     fetch("http://localhost:3000/prog-rangers/login/kakao",{
  //       method: "POST",
  //       headers: {
  //         "Content-Type": "application/x-www-form-urlencoded;",
  //       },
  //       body : JSON.stringify(access_token)
  //     })
  //     navigate("/");
  //     // window.Kakao.init(KAKAO_CLIENT_ID);
  //     // window.Kakao.Auth.setAccessToken(res.data.access_token);
  //   } catch(err){
  //     console.log(err);
  //   }
  // };

  const getToken = async() => {
    const Toss = await axios.get(`http://13.124.131.171:8080/prog-rangers/login/kakao`, {
      params: { code: KAKAO_CODE },
    }, {
      withCredentials : true 
    })
      .then((res) => {
        console.log(res.data);
      })
    // console.log(Toss.data);
    if(!Toss){
      alert("존재하지 않는 회원입니다.");
    } else{
      navigate(`/`);
    }
  }

  useEffect(() => {
    getToken();
  }, []);

  return(
    <>
    </>
  );
};