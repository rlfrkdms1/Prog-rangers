import React, { useRef, useState } from 'react';
import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';
import { SideBar } from '../../components/SideBar/SideBar';
import { profileContentStyle, editBtnStyle, blueBtn, grayBtn, alertStyle } from './AccountStyle';

export const AccountChange = () => {
  const [imgFile, setImgFile] = useState("");
  const imgRef = useRef();

  const saveImgFile = () => {
    const file = imgRef.current.files[0];
    const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onloadend = () => {
          setImgFile(reader.result);
      };
  };

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
                src={imgFile ? imgFile : 'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png'}                  
                alt='profileImg'
                css={css`
                height: 250px;
                border-radius: 50%;
                object-fit: cover;
                `}></img>
                <label htmlFor='uploadProfileImg' css={editBtnStyle}>사진선택</label>
                <input type='file' accept='image/*' id='uploadProfileImg' onChange={saveImgFile} ref={imgRef}
                css={css`display: none;`}></input>
              </div>

              <div  className='contentEdit'
              css={css`
              width: 500px;
              font-size: 18px
              `}>
                <div className='nickname' css={profileContentStyle}>
                  <div css={css`width: 72px`}>닉네임</div>
                  <div>rlfrkdms1</div>
                </div>
                <div css={css`
                  margin: -20px 0 0 126px; 
                  display: flex;
                  flex-direction: row;`}>
                  <div css={css`
                    height: 40px;
                    padding: 0 10px 0 30px;

                    display: flex;
                    justify-content: space-between;
                    align-items: center; 

                    border: 1px solid ${theme.colors.light1};
                    border-radius: 30px;
                    `}
                  >
                    <input
                      type="text"
                      placeholder="rlfrkdms1"
                      css={css`
                        width: 100%;
                        font-size: 16px;
                        outline: none;
                        border: none;
                      `}
                    />
                  </div>
                <button
                  type="button"
                  css={grayBtn}>
                  중복확인
                </button>
               </div>
               <div css={css` margin: 10px 0 10px 146px; `}>
               <div css={alertStyle}>*사용 가능한 닉네임입니다.</div>   
               </div>
                
                <div className='email' css={profileContentStyle}>
                  <div css={css`width: 72px`}>이메일</div>
                  <div>rlfrkdms1@hotmail.com</div>
                </div>

                <div className='github' css={profileContentStyle}>
                 <div css={css`width: 72px`}>깃허브</div>
                 <div>https://github.com/rlfrkdms1</div>
                </div>
                <div css={css`
                    height: 40px;
                    padding: 0 10px 0 30px;
                    margin: -20px 0 30px 126px;

                    display: flex;
                    justify-content: space-between;
                    align-items: center; 

                    border: 1px solid ${theme.colors.light1};
                    border-radius: 30px;
                    `}
                  >
                    <input
                      type="text"
                      placeholder="https://github.com/rlfrkdms1"
                      css={css`
                        width: 100%;
                        font-size: 16px;
                        outline: none;
                        border: none;
                      `}
                    />
                  </div>

                <div className='introduce' css={profileContentStyle}>
                 <div css={css`width: 72px`}>소개</div>
                 <div>가은이의 풀이노트</div>
                </div>
                <div css={css`
                    height: 40px;
                    padding: 0 10px 0 30px;
                    margin: -20px 0 30px 126px;

                    display: flex;
                    justify-content: space-between;
                    align-items: center; 

                    border: 1px solid ${theme.colors.light1};
                    border-radius: 30px;
                    `}
                  >
                    <input
                      type="text"
                      placeholder="풀이노트 겸 오답노트"
                      css={css`
                        width: 100%;
                        font-size: 16px;
                        outline: none;
                        border: none;
                      `}
                    />
                  </div>

                <div className='pw' css={profileContentStyle}>
                 <div css={css`width: 72px`}>비밀번호</div>
                 <div css={css `gap: 20px; display: flex; align-items: center;`}>
                  <div>기존 비밀번호</div>
                  <div css={css`
                      height: 40px;
                      padding: 0 10px 0 30px;

                      display: flex;
                      justify-content: space-between;
                      align-items: center; 

                      border: 1px solid ${theme.colors.light1};
                      border-radius: 30px;
                      `}
                    >
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
                <div css={css` margin: -20px 0 10px 280px; `}>
                 <div css={alertStyle}>*기존 비밀번호가 틀립니다.</div> 
                </div>
                  <div css={css `gap: 20px; display: flex; align-items: center; align-items: center; margin-left: 126px;`}>
                  <div>변경 비밀번호</div>
                  <div css={css`
                      height: 40px;
                      padding: 0 10px 0 30px;
                      margin: 0 0 10px 0;

                      display: flex;
                      justify-content: space-between;
                      align-items: center; 

                      border: 1px solid ${theme.colors.light1};
                      border-radius: 30px;
                      `}
                    >
                      <input
                        type="text"
                        placeholder="rkdms123@"
                        css={css`
                          width: 100%;
                          font-size: 16px;
                          outline: none;
                          border: none;
                        `}
                      />
                    </div>
                  </div>  

                  <div css={css `gap: 20px; display: flex; align-items: center; margin-left: 126px;`}>
                  <div>비밀번호 확인</div>
                  <div css={css`
                      height: 40px;
                      padding: 0 10px 0 30px;

                      display: flex;
                      justify-content: space-between;
                      align-items: center; 

                      border: 1px solid ${theme.colors.light1};
                      border-radius: 30px;
                      `}
                    >
                      <input
                        type="password"
                        placeholder="*********"
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
              <button
                type="button"
                css={grayBtn}>
                취소하기
              </button>
              <button
                type="button"
                css={blueBtn}>
                수정하기
              </button>
            </div>
          </div>
        </div>    
      </div>
  );
}