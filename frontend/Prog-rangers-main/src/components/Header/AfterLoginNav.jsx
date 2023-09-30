import axios from 'axios';
import { useEffect, useContext, useState } from 'react';
import { Link, navigate, useNavigate } from 'react-router-dom';
import {
  navStyle,
  linkDefault,
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
      <Link to="problems" css={linkDefault}>
        풀이 보러가기
      </Link>
      <Link to="registerReview" css={linkDefault}>
        풀이 쓰러가기
      </Link>
      <Link to="myPage" css={linkDefault}>
        {nickname}님
      </Link>
      <Link to="" css={linkDefault} onClick={logoutAction}>
        로그아웃
      </Link>
    </div>
  );
};