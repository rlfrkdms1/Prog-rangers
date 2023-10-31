import { css } from '@emotion/react';
import { theme } from '../Header/theme';

export const HeaderLayout = css`
  width: 996px;
  margin: 50px auto 0;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`;

export const MyHeaderLayout = css`
  width: 720px;
  margin: 50px auto 0;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`;

export const rowFlex = css`
  display: flex;
  flex-direction: row;
  align-items: center;
`;

export const dot = css`
  display: inline-block;
  width: 5px;
  height: 5px;
  border-radius: 2.5px;
  background-color: ${theme.colors.dark1};
  margin: 2px 20px;
`;

export const colFlex = css`
  display: flex;
  flex-direction: column;
`;

export const fontStyle = css`
  font-size: 16px
  text-align: center;`

export const editStyle = (isOpen) => css`
  display: ${isOpen ? "flex" : "none"}; 
  flex-direction: column; 
  width: 130px;
  height: 40px;
  padding-top: 11px;
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
  width: 130px;
  height: 40px;
  margin-top: 40px;
  padding-top: 11px;
  
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

export const Divline = css`
  display: inline-block; 
  width: 90%; 
  border-bottom: 1px solid #D9D9D9
  align-items: center;
  `;