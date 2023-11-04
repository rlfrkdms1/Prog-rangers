import { css } from "@emotion/react";

export const StyledInput = css`
  margin-top: 20px;
  padding-left: 30px;
  width: 100%;
  height: 50px;
  border: 1px solid #959595;
  border-radius: 25px;
  font-size: 16px;
  &: placeholder{
    color: #959595;
    font-size: 16px;
    font-weight: 400;
  }
`;

export const TitleBox = css`
  color: #303030;
  font-weight: 700;
  font-size: 20px;
  margin-left: 20px;
  width: max-content;
`;

export const Stars = css`
  display: flex;
  width: 180px;
  justify-content: space-between;
`;

export const DetailBox = css`
  margin-top: 20px; 
  border-radius: 50px; 
  border: 1px solid #959595;
  vertical-align: top;
  overflow: 'auto';
`;

export const DetailInput = css`
  width: 100%;
  height :100%;
  border-radius: 50px;
  display: flex; 
  flex-direction: column;
  padding: 30px;
`;