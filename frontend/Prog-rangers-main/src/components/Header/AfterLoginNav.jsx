import { useEffect, useContext, useState } from 'react';
import {
  useNavigate,
} from 'react-router-dom';
import {
  navStyle,
  linkDefault,
  StyledLink,
  ProfileImg,
  DropdownStyle,
} from './headerStyle';
import Profile from '../../assets/default-profile.png';
import { IsLoginContext } from '../../context/AuthContext';

export const AfterLoginNav = () => {
  const navigate = useNavigate();
  const { setIsLogin } = useContext(IsLoginContext);
  const [nickname] = useState(
    localStorage.getItem('nickname')
  );

  const [showDropdown, setShowDropdown] = useState(false);

  const logoutAction = () => {
    window.localStorage.removeItem('token');
    window.localStorage.removeItem('nickname');
    setIsLogin(false);
    navigate('/');
  };

  const toggleDropdown = () => {
    setShowDropdown((prevState) => !prevState);
  };

  const hideDropdown = () => {
    setShowDropdown(false);
  };

  const handleMyPageClick = (e) => {
    e.stopPropagation();
    hideDropdown();
    navigate('/myPage');
  };

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (!event.target.closest('.AfterLoginNav')) {
        setShowDropdown(false);
      }
    };

    document.addEventListener('click', handleClickOutside);

    return () => {
      document.removeEventListener(
        'click',
        handleClickOutside
      );
    };
  }, []);

  return (
    <div className="AfterLoginNav" css={navStyle}>
      <StyledLink to="problems" onClick={hideDropdown}>
        문제 보러가기
      </StyledLink>
      <StyledLink
        to="registerReview"
        onClick={hideDropdown}
      >
        풀이 쓰러가기
      </StyledLink>
      <div
        css={linkDefault}
        onClick={toggleDropdown}
        onBlur={hideDropdown}
      >
        <img
          src={Profile}
          alt="프로필 이미지"
          css={ProfileImg}
        />
        <span>{nickname}님</span>
        {showDropdown && (
          <div css={DropdownStyle}>
            <StyledLink
              to="myPage"
              onClick={handleMyPageClick}
            >
              마이페이지
            </StyledLink>
            <StyledLink to="" onClick={logoutAction}>
              로그아웃
            </StyledLink>
          </div>
        )}
      </div>
    </div>
  );
};
