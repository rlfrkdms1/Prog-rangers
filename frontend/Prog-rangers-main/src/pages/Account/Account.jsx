import React, { useEffect, useState } from 'react';
import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';
import { Link } from 'react-router-dom';
import { SideBar } from '../../components/SideBar/SideBar';
import {
  profileContentStyle,
  editBtnStyle,
  deleteBtnStyle,
} from './AccountStyle';
import Kakao from '../../assets/icons/signin-kakao-logo.svg';
import Google from '../../assets/icons/signin-google-logo.svg';
import Naver from '../../assets/icons/signin-naver-logo.svg';
import ProfileImg from '../../components/SolutionDetail/profile/default.png';

const Account = () => {
  const [userData, setUserData] = useState({
    type: '',
    nickname: '',
    email: '',
    github: '',
    introduction: '',
    passwordModifiedAt: '',
    photo: '',
  });

  // 유저 정보 조회 api
  useEffect(() => {
    const token = localStorage.getItem('token');
    fetch('http://13.125.13.131:8080/api/v1/members', {
      method: 'GET',
      headers: { Authorization: `Bearer ${token}` },
    })
      .then((response) => response.json())
      .then((json) => {
        setUserData({
          type: json.type || '',
          nickname: json.nickname || '',
          email: json.email || '',
          github: json.github || '',
          introduction: json.introduction || '',
          passwordModifiedAt: json.passwordModifiedAt || '',
          password: json.password || '',
          photo: json.photo || '',
        });
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  // 날짜 형식 변환
  const formattedDate = new Date(
    userData.passwordModifiedAt
  );

  const year = formattedDate.getFullYear();
  const month = formattedDate.getMonth() + 1;
  const day = formattedDate.getDate();

  const formattedTime = `${year}년 ${month}월 ${day}일`;

  // 계정 탈퇴
  const [inputValue, setInputValue] = useState('');
  const [deleteSuccess, setDeleteSuccess] = useState(false);

  const handleDeleteAccount = async () => {
    const isConfirmed =
      window.confirm('정말 삭제하시겠습니까?');

    if (isConfirmed) {
        const token = localStorage.getItem('token');

        try {
          const response = await fetch(
            'http://13.125.13.131:8080/api/v1/members',
            {
              method: 'DELETE',
              headers: { Authorization: `Bearer ${token}` },
            }
          );

          if (response.ok) {
            setDeleteSuccess(true);
            localStorage.removeItem('token');
            alert('계정이 삭제되었습니다.');
            window.location.href = '/';
          } else {
            alert('계정 삭제에 실패했습니다.');
          }
        } catch (error) {
          console.error(error);
        }
      } 
    };

  return (
    <div
      className="container"
      css={css`
        width: 1200px;
        height: 100%;
        display: flex;
        justify-content: space-between;
        margin: 0 auto;
      `}
    >
      <SideBar />
      <div
        className="content"
        css={css`
          width: 800px;
          margin: 100px 110px 0;
        `}
      >
        <div
          className="profile"
          css={css`
            height: 330px;
            display: flex;
            justify-content: space-between;
          `}
        >
          <div
            css={css`
              width: 250px;
              display: flex;
              flex-direction: column;
              align-items: center;
            `}
          >
            <img
              src={
                userData.photo ? userData.photo : ProfileImg
              }
              alt="profileImg"
              width="250px"
              css={css`
                border-radius: 50%;
              `}
            ></img>
            <Link to="/account/accountChange" css={editBtnStyle}>
              수정하기
            </Link>
          </div>

          <div
            css={css`
              width: 500px;
              font-size: 18px;
            `}
          >
            <div
              className="nickname"
              css={profileContentStyle}
            >
              <div
                css={css`
                  width: 72px;
                `}
              >
                닉네임
              </div>
              <div>{userData.nickname}</div>
            </div>
            <div
              className="email"
              css={profileContentStyle}
            >
              <div
                css={css`
                  width: 72px;
                `}
              >
                이메일
              </div>
              <div
                css={css`
                  display: flex;
                  flex-direction: row;
                  align-items: center;
                `}
              >
                {userData.type === 'BASIC' && (
                  <div>{userData.email}</div>
                )}
                {userData.type === 'KAKAO' && (
                  <div
                    css={css`
                      display: flex;
                      align-items: center;
                      gap: 10px;
                    `}
                  >
                    <img
                      src={Kakao}
                      alt="kakao_logo"
                      css={css`
                        width: 30px;
                      `}
                    />
                    <div>Kakao 로그인</div>
                  </div>
                )}
                {userData.type === 'NAVER' && (
                  <div
                    css={css`
                      display: flex;
                      align-items: center;
                      gap: 10px;
                    `}
                  >
                    <img
                      src={Naver}
                      alt="naver_logo"
                      css={css`
                        width: 30px;
                      `}
                    />
                    <div>Naver 로그인</div>
                  </div>
                )}
                {userData.type === 'GOOGLE' && (
                  <div
                    css={css`
                      display: flex;
                      align-items: center;
                      gap: 10px;
                    `}
                  >
                    <img
                      src={Google}
                      alt="google_logo"
                      css={css`
                        width: 30px;
                      `}
                    />
                    <div>Google 로그인</div>
                  </div>
                )}
              </div>
            </div>
            <div
              className="github"
              css={profileContentStyle}
            >
              <div
                css={css`
                  width: 72px;
                `}
              >
                깃허브
              </div>
              <div>{userData.github}</div>
            </div>
            <div
              className="introduce"
              css={profileContentStyle}
            >
              <div
                css={css`
                  width: 72px;
                `}
              >
                소개
              </div>
              <div>{userData.introduction}</div>
            </div>
            {userData.type === 'BASIC' && (
              <div className="pw" css={profileContentStyle}>
                <div
                  css={css`
                    width: 72px;
                  `}
                >
                  비밀번호
                </div>
                <div>
                  {userData.passwordModifiedAt && (
                    <span>
                      최근 변경일: {formattedTime}
                    </span>
                  )}
                  {!userData.passwordModifiedAt && (
                    <span>최근 변경일: 데이터 없음</span>
                  )}
                </div>
              </div>
            )}
          </div>
        </div>

        <div
          className="accountDelete"
          css={css`
            height: 100px;
            margin-top: 132px;
          `}
        >
          <div
            css={css`
              font-size: 20px;
              font-weight: 700;
              padding: 0 0 10px 30px;
              cursor: default;
            `}
          >
            계정삭제
          </div>

          <div
            css={css`
              height: 60px;
              padding: 0 10px 0 30px;

              display: flex;
              justify-content: space-between;
              align-items: center;

              border: 1px solid ${theme.colors.light1};
              border-radius: 30px;
            `}
          >
            <div
              css={css`
                width: 100%;
                font-size: 16px;
                outline: none;
                border: none;
                color: ${theme.colors.light1};
                cursor: default;
              `}
            >
              계정 삭제시 이 계정으로 작성한 글, 댓글 및
              리뷰에 대한 접근이 불가능합니다.
            </div>
            <button
              type="button"
              css={deleteBtnStyle}
              onClick={handleDeleteAccount}
            >
              삭제하기
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Account;
