import { css } from '@emotion/react';
import { theme } from '../Header/theme';

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

export const headingStyle = css`
  margin-top: 90px;
  color: ${theme.dark1};
  font-size: 24px;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
  margin-bottom: 50px;
`;

export const formStyle = css`
  width: 600px;
  display: flex;
  flex-direction: column;
`;

export const labelStyle = css`
  color: ${theme.dark2};
  font-size: 16px;
  font-weight: 400;
  margin-bottom: 10px;
  padding-left: 25px;
  margin-top: 10px;
  &::after {
    color: ${theme.main};
    font-size: 16px;
    font-weight: 400;
    content: '*';
  }
`;

export const inputContainerStyle = css`
  position: relative;
  width: 600px;
  height: 50px;
`;

export const inputStyle = css`
  width: 100%;
  height: 100%;
  color: ${theme.dark2};
  border-radius: 12px;
  border: 1px solid;
  padding: 15px 30px;
  font-size: 14px;
  font-weight: 400;
  margin-bottom: 10px;
  &::placeholder {
    color: #959595;
  }
`;

export const inputBoxStyle = css`
    width: 100%;
    height: 100%;
    color: ${theme.dark2};
    border-radius: 12px;
    border: 1px solid;
    padding: 15px 30px;
    font-size: 14px;
    font-weight: 400;
    margin-bottom: 10px;
    &::placeholder {
      color: #959595;
    }
    display: flex;
    justify-content: space-between;
    align-items: center;
`;

export const submitButtonStyle = css`
  width: 600px;
  height: 50px;
  color: #fff;
  font-size: 18px;
  font-weight: 700;
  text-align: center;
  border-radius: 12px;
  background-color: #3486a0;
  margin-top: 50px;
`;
