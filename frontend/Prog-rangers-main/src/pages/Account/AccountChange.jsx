import React, { useEffect, useRef, useState } from 'react';
import { checkNickname, inquireProfile, settingProfile } from './ProfileApi';
import { css } from '@emotion/react';
import { SideBar } from '../../components/SideBar/SideBar';
import { profileContentStyle, profileContentInputStyle, inputBoxStyle, editBtnStyle, blueBtn, grayBtn, alertStyle } from './AccountStyle';
import { Link } from 'react-router-dom';
import eyeOpen from '../../assets/icons/mypage-eye-open.svg';
import eyeClosed from '../../assets/icons/mypage-eye-closed.svg';
import axios from 'axios';

export const AccountChange = () => {
  const [userData, setUserData] = useState({
    nickname: '',
    email: '',
    github: '',
    introduction: '',
    currentModifiedAt: '',
    photo: '',
  });

  useEffect(() => {
    const apiUrl = 'http://{{HOST}}:8080/prog-rangers/mypage/account-settings';

    axios.get(apiUrl)
      .then(response => {
        const data = response.data;
        setUserData(data);
      })
      .catch(error => {
        console.error('사용자 정보를 가져오는 중 오류 발생:', error);
      });
  }, []);
  
  const [photo, setPhoto] = useState("");
  const imgRef = useRef();

  const savephoto = () => {
    const file = imgRef.current.files[0];
    const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onloadend = () => {
          setPhoto(reader.result);
      };
  };

  const [nickname, setNickname] = useState('');
  const [email, setEmail] = useState('');
  const [github, setGithub] = useState('');
  const [introduction, setIntroduction] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(true);
  const [showPassword2, setShowPassword2] = useState(false);
  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };
  const togglePasswordVisibility2 = () => {
    setShowPassword2(!showPassword2);
  };
  const [changePassword, setChangePassword] = useState('');
  const [checkPassword, setCheckPassword] = useState('');

  const [isNicknameAvailable, setIsNicknameAvailable] = useState(false);

  const nicknameDuplicationCheck = () => {
    checkNickname(nickname)
    .then((response) => {
      if (response.available) {
        setIsNicknameAvailable(true);
      } else {
        setIsNicknameAvailable(false);
      }
    })
    .catch((error) => {
      console.error('닉네임 중복 확인 중 오류 발생:', error);
      setIsNicknameAvailable(false);
    });
  }

  const [currentPassword, setCurrentPassword] = useState('');
  const [isPwCorrect, setIsPwCorrect] = useState('false');

  const pwCheck = () => {
    if (currentPassword !== password ) {
      setIsPwCorrect(false);
    } else {
      setIsPwCorrect(true);
  }
}

const changeProfile = () => {
  if ( isNicknameAvailable == true && isPwCorrect == true && changePassword == checkPassword) {
    settingProfile()
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
                src={photo ? photo : 'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png'}                  
                alt='profileImg'
                css={css`
                height: 250px;
                border-radius: 50%;
                object-fit: cover;
                `}></img>
                <label htmlFor='uploadProfileImg' css={editBtnStyle}>사진선택</label>
                <input type='file' accept='image/*' id='uploadProfileImg' onChange={savephoto} ref={imgRef}
                css={css`display: none;`}></input>
              </div>

              <div  className='contentEdit'
              css={css`
              width: 500px;
              font-size: 18px;
              `}>
                <div className='nickname' css={profileContentStyle}>
                  <div css={css`width: 72px`}>닉네임</div>

                  <div css={profileContentInputStyle}>
                    <div>{userData.nickname}rlfrkdms1_API1</div>
                    <div css={css`
                      display: flex;
                      flex-direction: row;`}>
                      <div css={inputBoxStyle}>
                        <input
                          type="text"
                          value={nickname}
                          onChange={(e) => setNickname(e.target.value)}
                          placeholder={userData.nickname}
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
                    <div id='usableNicknameAlert' css={{display: isNicknameAvailable ? 'block' : 'none'}}>
                      <div css={alertStyle}>*사용 가능한 닉네임입니다.</div> 
                    </div>  
                  </div>
                </div>
                
                <div className='email' css={profileContentStyle}>
                  <div css={css`width: 72px`}>이메일</div>
                  <div>{userData.email}rlfrkdms1@gmail.com_API2</div>
                </div>

                <div className='github' css={profileContentStyle}>
                 <div css={css`width: 72px`}>깃허브</div>

                 <div css={profileContentInputStyle}>
                  <div>{userData.github}https://github.com/rlfrkdms1_API3</div>
                  
                  <div css={css`width: 374px;`}>
                    <div css={inputBoxStyle}>
                      <input
                        type="text"
                        value={github}
                        onChange={(e) => setGithub(e.target.value)}
                        placeholder="https://github.com/rlfrkdms1"
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
                  <div>{userData.introduction}가은이의 풀이노트_API4</div>
                
                  <div css={css`width: 374px;`}>                
                    <div css={inputBoxStyle}>
                        <input
                          type="text"
                          value={introduction}
                          onChange={(e) => setIntroduction(e.target.value)}
                          placeholder="풀이노트 겸 오답노트"
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

                <div className='pw' css={profileContentStyle}>
                 <div css={css`width: 72px`}>비밀번호</div>

                 <div css={profileContentInputStyle}>
                  <div css={css `gap: 20px; display: flex; align-items: center;`}>
                    <div>기존 비밀번호</div>
                    <div css={css`width: 239px`}>
                      <div css={inputBoxStyle}>
                          <input
                            type="text"
                            placeholder="rkdms123"
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
                  <div id='uncorrectPwAlert' css={{display: isPwCorrect ? 'none' : 'block', marginLeft: 150}}>
                  <div css={alertStyle}>*기존 비밀번호가 틀립니다.</div> 
                  </div>

                  <div css={css `gap: 20px; display: flex; align-items: center; align-items: center;`}>
                    <div>변경 비밀번호</div>
                    <div css={css`width: 239px`}>
                      <div css={inputBoxStyle}>
                        <input
                          type={showPassword ? 'text' : 'password'}
                          value={password}
                          onChange={(e) => setPassword(e.target.value)}
                          // placeholder="rkdms123@"
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
                          // placeholder="*********"
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
                </div>
              </div>
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
                to='/account'
                type="button"
                onClick={changeProfile}
                css={blueBtn}>
                수정하기
              </Link>
            </div>
          </div>
        </div>    
      </div>
  );
}