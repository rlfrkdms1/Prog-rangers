import React, { useEffect, useRef, useState } from 'react';
import { css } from '@emotion/react';
import { SideBar } from '../../components/SideBar/SideBar';
import { profileContentStyle, profileContentInputStyle, inputBoxStyle, blueBtn, grayBtn, alertStyle } from './AccountStyle';
import { Link, useNavigate } from 'react-router-dom';
import eyeOpen from '../../assets/icons/mypage-eye-open.svg';
import eyeClosed from '../../assets/icons/mypage-eye-closed.svg';
import Kakao from '../../assets/icons/signin-kakao-logo.svg'
import Google from '../../assets/icons/signin-google-logo.svg'
import Naver from '../../assets/icons/signin-naver-logo.svg'
import ProfileImg from '../../components/SolutionDetail/profile/default.png';
import axios from 'axios';

const AccountChange = () => {
  const [userData, setUserData] = useState({
    type: '',
    nickname: '',
    email: '',
    github: '',
    introduction: '',
    passwordModifiedAt: '',
    password: '',
    // photo: '',
  });

  // 유저 정보 조회 api
  useEffect(() => {
    const token = localStorage.getItem('token');    
    fetch("http://13.124.131.171:8080/api/v1/members", {
      method: "GET",
      headers: {Authorization: `Bearer ${token}`},
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
        // photo: json.photo || '',
      })
      console.log(token);
    })
    .catch((error) => {
      console.error(error);
    })
  }, []);

  // 사진 업로드
  const [photo, setPhoto] = useState("");
  const imgRef = useRef();

  // const savephoto = () => {
  //   const file = imgRef.current.files[0];

  //   const reader = new FileReader();
  //     reader.readAsDataURL(file);
  //     reader.onloadend = () => {
  //         setPhoto(reader.result);
  //     };
  // };

  const [nickname, setNickname] = useState('');
  const [github, setGithub] = useState(userData.github);
  const [introduction, setIntroduction] = useState(userData.introduction);

  useEffect(() => {
    setGithub(userData.github);
  }, [userData.github]);
  
  useEffect(() => {
    setIntroduction(userData.introduction);
  }, [userData.introduction]);

  const [showPassword, setShowPassword] = useState(false);
  const [showPassword2, setShowPassword2] = useState(false);
  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };
  const togglePasswordVisibility2 = () => {
    setShowPassword2(!showPassword2);
  };

  const [isNicknameAvailable, setIsNicknameAvailable] = useState(null);
  const handleNicknameChange = (e) => {
    setNickname(e.target.value);
  };

  // 닉네임 중복확인 api
  const nicknameDuplicationCheck = async () => {
    try {
      const response = await axios.get(`http://13.124.131.171:8080/api/v1/members?nickname=${nickname}`);
      const errorCode = response.data.errorCode
      setIsNicknameAvailable(response.data.errorCode);

      if (errorCode === "ALREADY_EXIST_NICKNAME") {
        setIsNicknameAvailable(false); 
      } else {
        setIsNicknameAvailable(true); 
      }
    } catch (error) {
      if (error.response && error.response.data && error.response.data.errorCode === "ALREADY_EXIST_NICKNAME") {
        setIsNicknameAvailable(false); 
      } else {
        console.error('에러 발생:', error);
      }
    }
  };

  // 기존 비밀번호와 일치하는지 검사
  const [currentPassword, setCurrentPassword] = useState('');
  const [isPwCorrect, setIsPwCorrect] = useState(null);

  const handlePasswordChange = (e) => {
    setCurrentPassword(e.target.value);
  }

  const pwCheck = () => {
    if (currentPassword === '') {
      setIsPwCorrect(true);
    } else if (currentPassword !== userData.password) {
      setIsPwCorrect(false);
    } else {
      setIsPwCorrect(true);
    }
}

  useEffect(() => {
    pwCheck();
  }, [currentPassword]);

// 변경 비밀번호와 비밀번호 확인이 일치하는지 검사
const [changePassword, setChangePassword] = useState('');
const [checkPassword, setCheckPassword] = useState('');
const [isPasswordSame, setIsPasswordSame] = useState(null);

const handleChangePassword = (e) => {
  setChangePassword(e.target.value);
};

const handleCheckPassword = (e) => {
  setCheckPassword(e.target.value);
};

const pwSameCheck = () => {
  if (changePassword !== checkPassword ) {
    setIsPasswordSame(false);
  } else {
    setIsPasswordSame(true);
    //setPasswordModifiedAt(new Date().toISOString());
}
}

useEffect(() => {
  pwSameCheck();
}, [changePassword, checkPassword]);

// 정보 수정하기
const navigate = useNavigate();

const handleUpdateProfile = () => {
  const token = localStorage.getItem('token');

  const updatedData = {};

  if (isNicknameAvailable && nickname) {
    updatedData.nickname = nickname;
    localStorage.setItem('nickname', updatedData.nickname);
  }

  if (github.trim() !== null) {
    updatedData.github = github;
  }

  if (introduction.trim() !== null) {
    updatedData.introduction = introduction;
  }

  if (changePassword) {
    updatedData.newPassword = changePassword;
    updatedData.oldPassword = userData.password;
  }

  fetch("http://13.124.131.171:8080/api/v1/members", {
    method: "PATCH",
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(updatedData),
  })
  .then((response) => {
    if (!response.ok) {
      throw new Error('네트워크 응답이 실패했습니다.');
    }
    navigate('/account');
    window.location.reload();
    return response.json();
  })
    .then((data) => {
      console.log(data);
    })
    .catch((error) => {
      console.error(error);
    });

};

const accountSubmit = () => {
  if(isNicknameAvailable === false) {
    alert('중복된 닉네임입니다.');
  } else if (nickname && isNicknameAvailable === null) {
    alert('닉네임 중복 확인을 해주세요.')
  } else if (isPwCorrect === false) {
    alert('기존 비밀번호가 일치하지 않습니다.');
  } else if (isPasswordSame === false) {
    alert('변경 비밀번호가 일치하지 않습니다.');
  } else {
    const isNicknameEmpty = nickname === null;
    const isChangePasswordEmpty = changePassword === null;

    if (!isNicknameEmpty || !isChangePasswordEmpty) {
      handleUpdateProfile();
    } else {
      alert('변경된 사항이 없습니다.');
    }
  }
}

  return (
    <div 
      className='container' 
      css={css`
      width: 1200px;
      height: 100%;
      display: flex;
      justify-content: space-between;
      margin: 0 auto;
    ` }
    >
      <SideBar />

      <div
        className='content'
        css={css`
        width: 800px;
        margin: 100px 110px 0;
        `}
        >

          <div 
            className='profile'
            css={css`
            height: 560x;
            display: flex;
            justify-content: space-between;
            `}>
              <div css={css`
              width: 250px;
              display: flex;
              flex-direction: column;
              align-items: center;
              `}> 
                <img
                src={photo ? photo : ProfileImg}                  
                alt='profileImg'
                css={css`
                height: 250px;
                border-radius: 50%;
                object-fit: cover;
                `}></img>
                {/* <label htmlFor='uploadProfileImg' css={editBtnStyle}>사진선택</label>
                <input type='file' accept='image/*' id='uploadProfileImg' onChange={savephoto} ref={imgRef}
                css={css`display: none;`}></input> */}
              </div>

              <div  className='contentEdit'
              css={css`
              width: 500px;
              font-size: 18px;
              `}>
                <div className='nickname' css={profileContentStyle}>
                  <div css={css`width: 72px`}>닉네임</div>

                  <div css={profileContentInputStyle}>
                    <div>{userData.nickname}</div>
                    <div css={css`
                      display: flex;
                      flex-direction: row;`}>
                      <div css={inputBoxStyle}>
                        <input
                          type="text"
                          value={nickname}
                          onChange={handleNicknameChange}
                          placeholder={userData.nickname}
                          maxLength={10}
                          css={css`
                            width: 100%;
                            font-size: 16px;
                            outline: none;
                            border: none;
                          `}
                        />
                      </div>
                    <button type="button" onClick={nicknameDuplicationCheck} css={grayBtn}> 중복확인 </button>
                    </div>
                    {isNicknameAvailable !== null && (
                      <div>
                        {isNicknameAvailable ? (
                          <div css={alertStyle}>*사용 가능한 닉네임입니다.</div>
                        ) : (
                          <div css={alertStyle}>*이미 존재하는 닉네임입니다.</div>
                        )}
                      </div>
                    )}
                  </div>
                </div>
                
                <div className='email' css={profileContentStyle}>
                  <div css={css`width: 72px`}>이메일</div>
                  <div css={css`display: flex; flex-direction: row; align-items: center`}>
                  {userData.type === 'BASIC' && (
                    <div>{userData.email}</div>
                  )}
                  {userData.type === 'KAKAO' && (
                    <div css={css`display: flex; align-items: center; gap:10px;`}>
                      <img src={Kakao} alt="kakao_logo" css={css`width:30px;`} />
                      <div>Kakao 로그인</div>
                    </div>
                  )}
                  {userData.type === 'NAVER' && (
                    <div css={css`display: flex; align-items: center; gap:10px;`}>
                      <img src={Naver} alt="naver_logo" css={css`width:30px;`} />
                      <div>Naver 로그인</div>
                    </div>
                  )}
                  {userData.type === 'GOOGLE' && (
                    <div css={css`display: flex; align-items: center; gap:10px;`}>
                      <img src={Google} alt="google_logo" css={css`width:30px;`} />
                      <div>Google 로그인</div>
                    </div>
                  )}
                  </div>
                </div>

                <div className='github' css={profileContentStyle}>
                 <div css={css`width: 72px`}>깃허브</div>

                 <div css={profileContentInputStyle}>
                  <div css={{display: userData.github ? 'block' : 'none'}}>{userData.github}</div>
                  
                  <div css={css`width: 374px;`}>
                    <div css={inputBoxStyle}>
                      <input
                        type="text"
                        value={github}
                        onChange={(e) => setGithub(e.target.value)}
                        placeholder={userData.github}
                        css={css`
                          width: 100%;
                          font-size: 16px;
                          background: none;
                          outline: none;
                          border: none;
                        `}
                      />
                    </div>
                  </div>
                 </div>
                </div>

                <div className='introduction' css={profileContentStyle}>
                 <div css={css`width: 72px`}>소개</div>

                 <div css={profileContentInputStyle}>
                  <div css={{display: userData.introduction ? 'block' : 'none'}}>{userData.introduction}</div>
                
                  <div css={css`width: 374px;`}>                
                    <div css={inputBoxStyle}>
                        <input
                          type="text"
                          value={introduction}
                          onChange={(e) => setIntroduction(e.target.value)}
                          placeholder={userData.introduction}
                          css={css`
                            width: 100%;
                            font-size: 16px;
                            outline: none;
                            border: none;
                          `}
                        />
                      </div>
                  </div>
                 </div>
                </div>

                {userData.type === 'BASIC' && (
                <div className='pw' css={profileContentStyle}>
                 <div css={css`width: 72px; padding-top: 8px;`}>비밀번호</div>

                 <div css={profileContentInputStyle}>
                  <div css={css `gap: 20px; display: flex; align-items: center;`}>
                    <div>기존 비밀번호</div>
                    <div css={css`width: 239px`}>
                      <div css={inputBoxStyle}>
                          <input
                            type="text"
                            value={currentPassword}
                            onChange={handlePasswordChange}
                            css={css`
                              width: 100%;
                              font-size: 16px;
                              outline: none;
                              border: none;
                            `}
                          />
                        </div>
                      </div>
                    </div>
                  <div css={{display: isPwCorrect ? 'none' : 'block', marginLeft: 130}}>
                  <div css={alertStyle}>*기존 비밀번호가 틀립니다.</div> 
                  </div>

                  <div css={css `gap: 20px; display: flex; align-items: center; align-items: center;`}>
                    <div>변경 비밀번호</div>
                    <div css={css`width: 239px`}>
                      <div css={inputBoxStyle}>
                        <input
                          type={showPassword ? 'text' : 'password'}
                          value={changePassword}
                          onChange={handleChangePassword}
                          css={css`
                            width: 100%;
                            font-size: 16px;
                            outline: none;
                            border: none;
                          `}
                        />
                        <img
                          src={showPassword ? eyeOpen : eyeClosed}
                          alt={showPassword ? '눈이 떠있는' : '눈이 감겨있는'}
                          onClick={togglePasswordVisibility}
                          style={{ cursor: 'pointer' }}
                        />
                      </div>
                    </div>
                  </div>  

                  <div css={css `gap: 20px; display: flex; align-items: center;`}>
                    <div>비밀번호 확인</div>
                    <div css={css`width: 239px`}>
                      <div css={inputBoxStyle}>
                        <input
                          type={showPassword2 ? 'text' : 'password'}
                          value={checkPassword}
                          onChange={handleCheckPassword}
                          css={css`
                            width: 100%;
                            font-size: 16px;
                            outline: none;
                            border: none;
                          `}
                        />
                        <img
                          src={showPassword2 ? eyeOpen : eyeClosed}
                          alt={showPassword2 ? '눈이 떠있는' : '눈이 감겨있는'}
                          onClick={togglePasswordVisibility2}
                          style={{ cursor: 'pointer' }}
                        />
                      </div>
                    </div>
                  </div>
                  <div css={{display: isPasswordSame ? 'none' : 'block', marginLeft: 130}}>
                  <div css={alertStyle}>*비밀번호가 일치하지 않습니다.</div> 
                  </div>
                </div>
              </div>
              )}
            </div>
          </div>

          <div 
            className='accountEdit'
            css={css`
            height: 40px;
            margin-top: 380px;
            display: flex;
            justify-content: space-between;
            gap: 10px;
            `}>

            <div css={css`
            font-size: 18px;
            font-weight: bold;
            `}>계정 정보 수정을 완료하셨나요?</div>

            <div className='btnContainer' css={css`display: flex; flex-direction: row; `}>
              <Link
                to='/account'
                type="button"
                css={grayBtn}>
                취소하기
              </Link>
              <Link
                type="button"
                onClick={accountSubmit}
                css={blueBtn}>
                수정하기
              </Link>
            </div>
          </div>
        </div>    
      </div>
  );
};

export default AccountChange;