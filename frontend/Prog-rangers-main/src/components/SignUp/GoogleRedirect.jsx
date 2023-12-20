import { useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { IsLoginContext } from '../../context/AuthContext';

export const GoogleRedirect = () => {
  const navigate = useNavigate();
  const href = window.location.href;
  let params = new URL(document.location).searchParams;
  let GOOGLE_CODE = params.get('code');
  const { setIsLogin } = useContext(IsLoginContext);

  useEffect(() => {
    fetch(
      `http://13.125.13.131:8080/api/v1/login/google?code=${GOOGLE_CODE}`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json;',
        },
      }
    )
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        console.log(data);
        localStorage.setItem('token', data.accessToken);
        localStorage.setItem('nickname', data.nickname);
        setIsLogin(true);
        navigate('/');
      })
      .catch((error) => {
        console.log('Error:', error);
      });
  }, []);

  return <></>;
};
