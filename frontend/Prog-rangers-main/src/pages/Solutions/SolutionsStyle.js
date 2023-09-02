import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';

export const fontSize16 = css`
  font-size: 16px;
  font-weight: 400;
  color: ${theme.colors.dark1};
`;

export const fontSize20 = css`
  font-size: 20px;
  font-weight: 700;
  color: ${theme.colors.dark1};
`;

export const fontSize24 = css`
  font-size: 24px;
  font-weight: 700;
  color: ${theme.colors.dark1};
`;

// button
export const buttonSytle = css`
  width: 996px;
  height: 70px;
  background-color: ${theme.colors.white};
  border-radius: 40px;

  font-size: 20px;
  font-weight: 700;

  box-shadow: 5px 5px 10px rgba(52, 134, 160, 0.3);
`;

export const listSytle = css`
  align-items: center;
  transform: translate(36%, 216%);
  width : 60%;
  height : 95px;
  padding: 10px;
  border-bottom: 1px solid gray;
  flex-shrink: 0;
  color: ${theme.colors.dark2}
  `;

export const hashtagStyle = css`
  display: inline-flex;
  padding: 8px 20px;
  flex-direction: column;
  justify-content: center;
  margin-top: 9px;
  align-items: center;
  border-radius: 20px;
  background-color: ${theme.colors.light3}
  `;

export const selectStyle = css`
  border: 1px solid light1;
  border-radius: 25px;
  display: inline-black;
  width: 230px;
  height: 50px;
  flex-shrink: 0;
  text-indent: 30px;
  font-weight : 700;

  // 화살표
  appearance: none;
  background-image: url('data:image/svg+xml;charset=utf-8,%3Csvg xmlns="http://www.w3.org/2000/svg" width="19" height="10" viewBox="0 0 19 10" fill="none"%3E%3Cpath d="M0.5 1L9.5 9L18.5 1" stroke="%23959595"%3E%3C/path%3E%3C/svg%3E'); /* 이미지 SVG 코드를 여기에 입력하세요 */
  background-repeat: no-repeat;
  background-position: right 19px center;
  color: ${theme.colors.light1}
  `; 