import axios from 'axios';
import { useEffect, useContext, useState } from 'react';
import { Link, navigate, useNavigate } from 'react-router-dom';
import {
  navStyle,
  linkDefault,
  StyledLink
} from './headerStyle';
import { IsLoginContext } from '../../context/AuthContext';

export const AfterLoginNav = () => {
  const navigate = useNavigate();
  const { setIsLogin } = useContext(IsLoginContext);
  const [ nickname, setNickName ] = useState(localStorage.getItem('nickname'));

  const logoutAction = () => {
    window.localStorage.removeItem('token');
    window.localStorage.removeItem('nickname');
    setIsLogin(false);
    navigate("/");
  }

  return (
    <div className="AfterLoginNav" css={navStyle}>
      <StyledLink to="problems" >
        풀이 보러가기
      </StyledLink>
      <StyledLink to="registerReview" >
        풀이 쓰러가기
      </StyledLink>
      <StyledLink to="myPage" >
        {nickname}님
      </StyledLink>
      <StyledLink to="" onClick={logoutAction}>
        로그아웃
      </StyledLink>
    </div>
  );
};