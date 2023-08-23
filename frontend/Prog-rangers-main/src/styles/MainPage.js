import { css } from '@emotion/react';
import { theme } from './theme';

export const pageStyle = css`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const innerPageStyle = css`
  width: 100%;
  max-width: 1800px;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const HeadingStyle = css`
  color: #000;
  font-size: 24px;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
  width: 996px;
`;

export const ButtonStyle = css`
  width: 996px;
  height: 100px;
  border-radius: 50px;
  background: ${theme.colors.main};
  font-size: 24px;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
  color: #fff;
  margin: 50px 0;
`;

export const galleryContainerStyle = css`
  display: flex;
  flex-direction: column;
  gap: 185px;
`;

export const galleryStyle = css`
  display: flex;
  justify-content: space-between;
  gap: 40px;
  width: 996px;
`;

export const textStyle = css`
  color: var(--dark-2, #545454);
  font-family: Noto Sans KR;
  font-size: 18px;
  font-style: normal;
  font-weight: 400;
  line-height: normal;
  margin-top: 50px;
  margin-bottom: 10px;
  padding-left: 5px;
  border-left: 5px solid #3486a0;
`;

export const codeBlockStyle = css`
  width: 310px;
  height: 220px;
  background: #2a3746;
`;
