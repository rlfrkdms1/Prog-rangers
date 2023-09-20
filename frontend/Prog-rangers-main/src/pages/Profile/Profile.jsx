import React, { useState, useEffect, useRef } from 'react';
import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';
import { LeftBody, MainBody, RightBody } from './MainBody';
import { Axios } from 'axios';
import {
  alignCenter,
  fontSize14,
  fontSize16,
  fontSizebold16,
  fontSizewhite16,
  fontSize18,
  fontSize20,
  fontSizedark20,
  fontSize24,
  boxStyle
} from './ProfileStyle';

export const Profile = () => {

  // 프로필 사진 API
  // const [imgFile, setImgFile] = useState("");
  // const imgRef = useRef();

  // const saveImgFile = () => {
  //   const file = imgRef.current.files[0];
  //   const reader = new FileReader();
  //     reader.readAsDataURL(file);
  //     reader.onloadend = () => {
  //         setImgFile(reader.result);
  //     };
  // };

  return (
    <div css={css`
    ${MainBody}`}>

      <div css={css`
      ${LeftBody}`}>
        
        <div
          // src={imgFile ? imgFile : 'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png'}                  
          // alt='profileImg'
          css={css`
          width: 250px;
          height: 250px;
          border-radius: 250px;
          object-fit: cover;
          border: 1px solid black;
          `}></div>
          <div css={css`
          ${fontSize20}
          ${alignCenter}
          margin-top: 8px;`}>
            subing
          </div>

          <div css={css`
          ${fontSize14}
          ${alignCenter}
          margin-top: 7px;`}>
            수빈이의 풀이노트
          </div>

          <div css={css`
          width: 245px;
          height: 40px;
          border-radius: 25px;
          margin-top: 10px;
          padding-top: 6px;
          ${fontSize20}
          ${alignCenter}
          background-color: ${theme.colors.main30}
          `}>팔로우</div>

          <div css={css`
          display: flex;
          margin-top: 10px;
          margin-left: 20px;
          gap: 25px;
          `}>
            <div css={css`
            ${fontSizebold16}`}> 팔로우 </div>
            <div> 1 </div>
            <div css={css`
            ${fontSizebold16}
            margin-left: 15px;`}> 팔로잉 </div>
            <div> 3 </div>
          </div>

          <div css={css`
          display: flex;
          margin-top: 30px;
          gap: 20px;
          align-items: center;
          `}>
            <div css={css`
                 width: 30px;
                 height: 30px;`}>
              <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/c2/GitHub_Invertocat_Logo.svg/300px-GitHub_Invertocat_Logo.svg.png" alt="GitHub Logo" />
            </div>
            <div 
              css={css`
              font-size: 16px;
              font-weight: 400;
              color: ${theme.colors.light1};
              `}> 
              깃허브 링크
            </div>
          </div>

          <div css={css`
            width: 250px;
            margin-top: 105px;
            border: 1px solid ${theme.colors.light2};
            `}></div>
          
          <div css={css`
          display: flex;
          align-items: center;
          gap: 10px;          
          ${fontSize20}`}>
            <div css={css`
            margin-top: 10px;`}>
            <svg xmlns="http://www.w3.org/2000/svg" width="25" height="30" viewBox="0 0 25 30" fill="none">
              <path d="M19.2938 21.4465L22.2422 26.8034L18.2539 25.9761L17.3084 29.6683L13.8194 23.519L12.9326 21.9661L18.0725 19.2717L19.2938 21.4465Z" fill="#FF7058"/>
              <path d="M10.7043 23.5031L6.99756 29.9999L5.87496 26.0712L1.88672 27.2526L4.99328 21.6896L6.40905 19.2717L11.3617 22.043L10.7043 23.5031Z" fill="#FF7058"/>
              <path d="M9.04131 0.400092L7.97386 1.75853C7.78301 2.00163 7.49129 2.14313 7.18216 2.14313H5.2606C4.75264 2.14313 4.3245 2.52193 4.26137 3.02627L4.08213 4.47034C4.04076 4.80269 3.83758 5.09223 3.53933 5.24389L1.78758 6.13428C1.36815 6.34763 1.15263 6.82221 1.26801 7.27865L1.66132 8.83302C1.73896 9.14143 1.66712 9.46798 1.46684 9.7147L0.225228 11.243C-0.0744706 11.6123 -0.075196 12.1406 0.223777 12.5107L1.40951 13.9765C1.60399 14.2167 1.67728 14.5338 1.60762 14.835L1.21939 16.5251C1.11344 16.9873 1.3442 17.4612 1.77379 17.6622L3.43774 18.4408C3.79186 18.6063 4.01754 18.9618 4.01754 19.3522V20.5293C4.01754 21.0851 4.46818 21.5358 5.02404 21.5358H6.99712C7.31496 21.5358 7.61393 21.686 7.80333 21.9399L8.88021 23.3826C9.18136 23.786 9.73504 23.905 10.1755 23.6605L11.436 22.9602C11.7016 22.8129 12.0202 22.7933 12.3017 22.9065L14.4388 23.7701C14.8923 23.9537 15.4126 23.7839 15.671 23.3681L16.5106 22.0176C16.6847 21.7368 16.9859 21.5597 17.316 21.5437L19.2942 21.4465C19.8305 21.4204 20.2514 20.9777 20.2514 20.4415V19.472C20.2514 19.1077 20.448 18.7717 20.7658 18.5939L22.6257 17.5519C23.0176 17.3327 23.2164 16.8799 23.1134 16.4431L22.7237 14.7878C22.6497 14.4743 22.7302 14.1449 22.9399 13.9011L24.1322 12.515C24.45 12.1457 24.4573 11.6007 24.1496 11.2234L23.1881 10.0427C22.9929 9.80324 22.9189 9.48685 22.9864 9.18497L23.4123 7.29897C23.5176 6.8331 23.281 6.35706 22.8456 6.16041L21.2245 5.42749C20.919 5.28888 20.702 5.00733 20.6468 4.67642L20.3892 3.13729C20.3094 2.65981 19.9009 2.30713 19.4168 2.29698L17.3937 2.25634C17.0584 2.24981 16.7493 2.07637 16.5679 1.79482L15.7094 0.461773C15.4482 0.0554018 14.9351 -0.107147 14.4867 0.0728176L12.5492 0.853631C12.2857 0.959578 11.989 0.949419 11.7335 0.82533L10.2735 0.116357C9.84607 -0.0904567 9.33375 0.0278264 9.04131 0.400092Z" fill="#FFD15D"/>
              <path d="M12.1322 20.038C16.6148 20.038 20.2487 16.4041 20.2487 11.9215C20.2487 7.43883 16.6148 3.80493 12.1322 3.80493C7.64953 3.80493 4.01562 7.43883 4.01562 11.9215C4.01562 16.4041 7.64953 20.038 12.1322 20.038Z" fill="#F8B64D"/>
              <path d="M8.40396 17.086L8.92789 13.7161C8.94966 13.5767 8.90104 13.4367 8.79872 13.3402L6.32711 11.0137C6.07023 10.772 6.20085 10.341 6.54844 10.2829L9.91407 9.71547C10.0519 9.69225 10.1695 9.603 10.229 9.47673L11.6905 6.38105C11.8414 6.06103 12.2942 6.05378 12.4553 6.36872L14.0032 9.38747C14.0678 9.51301 14.1904 9.59864 14.3305 9.61606L17.6975 10.0297C18.0487 10.0725 18.1975 10.4999 17.9493 10.7524L15.5467 13.1907C15.4487 13.2901 15.4059 13.4309 15.4313 13.568L16.0575 16.9235C16.1221 17.2696 15.7629 17.541 15.4472 17.3828L12.4118 15.8633C12.2863 15.8002 12.1375 15.8038 12.0141 15.8713L9.03166 17.5251C8.7218 17.6963 8.34881 17.4365 8.40396 17.086Z" fill="#FFD15D"/>
            </svg>
            
            </div>
            달성
          </div>


      </div>
 
      <div css={css`
      ${RightBody}`}>

        <div css={css`
        ${fontSize24}
        `}>풀이</div>

        <div css={css`
        width: 835px;
        height: 660px;        
        margin-top: 50px;
        `}>
          <div css={css`
          ${fontSizedark20}`}> 재밌는 레이싱 경기장 설계하기 
          </div>
          <div css={css`
            ${boxStyle}
            ${fontSize16}
            background-color: ${theme.colors.light3}`}>
            Javascript
          </div>
          
          <button css={css`
            width: 30px;
            height: 30px;
            float: right;
            padding-top: 13px;     
          `}>
            <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 30 30" fill="none">
            <path d="M23.7725 3.92673L24.199 3.5H23.5957H18.75C17.851 3.5 17.125 2.77404 17.125 1.875C17.125 0.975962 17.851 0.25 18.75 0.25H28.125C29.024 0.25 29.75 0.975962 29.75 1.875V11.25C29.75 12.149 29.024 12.875 28.125 12.875C27.226 12.875 26.5 12.149 26.5 11.25V6.4043V5.80053L26.0732 6.22756L14.2783 18.0283C14.2783 18.0283 14.2783 18.0283 14.2783 18.0283C13.6435 18.6631 12.6123 18.6631 11.9776 18.0283C11.3428 17.3935 11.3428 16.3623 11.9776 15.7276L11.9776 15.7275L23.7725 3.92673ZM0.25 6.5625C0.25 4.11073 2.23573 2.125 4.6875 2.125H11.25C12.149 2.125 12.875 2.85096 12.875 3.75C12.875 4.64904 12.149 5.375 11.25 5.375H4.6875C4.0338 5.375 3.5 5.9088 3.5 6.5625V25.3125C3.5 25.9662 4.0338 26.5 4.6875 26.5H23.4375C24.0912 26.5 24.625 25.9662 24.625 25.3125V18.75C24.625 17.851 25.351 17.125 26.25 17.125C27.149 17.125 27.875 17.851 27.875 18.75V25.3125C27.875 27.7643 25.8893 29.75 23.4375 29.75H4.6875C2.23573 29.75 0.25 27.7643 0.25 25.3125V6.5625Z" fill="#545454" stroke="white" stroke-width="0.5"/>
            </svg>
          </button>

          <div css={css`
            ${boxStyle}
            ${fontSizewhite16}
            float: right;
            margin-right: 30px;
            background-color: ${theme.colors.programmers}
            `}>
            프로그래머스
          </div>
          <div css={css`
          width: 835px;
          margin-top: 30px;
          border: 1px solid #F0F0F0;
          `}></div>

          <div css={css`
          ${fontSize18} 
          margin-top: 50px;
          margin-left: 10px;`}> 
            이 문제는 처음 도전하는 나에게 너무 어려운 문제였다.
            <br></br>
            다음에 한 번 더 도전해봐야겠다.
          </div>
          <div css={css`
            width: 809px;
            height: 370px;
            margin-top: 50px;
            background-color: #2A3746;
            `}>

          </div>
        </div> 

        <div css={css`
          width: 835px;
          margin-top: 30px;
          border: 1px solid #959595;
          `}></div>

      </div>
    </div>
  )
}
