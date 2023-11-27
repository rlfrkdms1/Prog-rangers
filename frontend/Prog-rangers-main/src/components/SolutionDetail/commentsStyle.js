import { css } from '@emotion/react';
import { theme } from '../Header/theme';

export const wrapStyle = css`
  width: 956px;
  margin: 30px auto;
`;

export const MycommentStyle = css`
  width: 700px;
  margin: 30px auto;
  `;

export const MywrapStyle = css`
  width: 720px;
  margin: 30px auto;
  `;

export const flexLayout = css`
  display: flex;
  flex-direction: row;
  align-items: baseline;

  margin-bottom: 30px;
`;

export const rowFlex = css`
  display: flex;
  flex-direction: row;
  align-items: center;

  margin-bottom: 30px;
`;

export const rowFlexRecomment = css`
  display: flex;
  flex-direction: row;
  align-items: center;

  margin-bottom: 30px;
  margin-left: 50px;
`;

export const editStyle = (isOpen) => css`
  display: ${isOpen ? "flex" : "none"}; 
  flex-direction: column; 
  width: 80px;
  height: 30px;
  padding-top: 8px;
  background-color: ${theme.colors.light3};
  color: ${theme.colors.dark2};
  border-radius: ${isOpen ? '20px 20px 0 0' : '20px'};
  cursor: pointer;

  position: absolute;
  align-items: center;
  
  border-bottom: 1px solid #D9D9D9;

  :hover {
    background-color: ${theme.colors.light2};
  }
`;

export const deleteStyle = (isOpen) => css`
  display: ${isOpen ? "flex" : "none"}; 
  flex-direction: column; 
  width: 80px;
  height: 30px;
  margin-top: 30px;
  padding-top: 8px;
  
  background-color: ${theme.colors.light3};
  color: ${theme.colors.dark2};
  border-radius: ${isOpen ? '0 0 20px 20px' : '20px'};
  cursor: pointer;

  position: absolute;
  align-items: center;

  :hover {
    background-color: ${theme.colors.light2};
  }
`;

