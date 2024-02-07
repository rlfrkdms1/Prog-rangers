import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';


export const fontSize14 = css`
  font-size: 14px;
  font-weight: 400;
  color: ${theme.colors.black}
  `;

export const fontSizeBold14 = css`
  font-size: 14px;
  font-weight: 700;
  color: ${theme.colors.black}
  `;

export const fontSize16 = css`
  font-size: 16px;
  font-weight: 700;
  color: ${theme.colors.dark2}
  `;

export const fontSize20 = css`
  font-size: 20px;
  font-weight: 700;
  color: ${theme.colors.black}
  `;

export const alignCenter = css`
  text-align: center;
  `;

export const dateStyle = css`
  width: 20px;
  height: 20px;
  border-radius: 5px;
  margin-left: 10px;
  margin-top: 5px;
  background-color: ${theme.colors.light2};
  color: transparent;
  `;
  
export const gridStyle = css`
  display: grid;
  grid-template-columns: repeat(13, 25px);
  gap: 2px;
  `;

export const Divline = css`
  display: inline-block; 
  width: 100%; 
  height: 60px; 
  border-bottom: 1px solid #959595
  `;

  export const RecentlyTitle = css`
  height: 30px; 
  width: 60%;  
  margin-left: 10px;
  padding-top: 3px;

  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;

  font-weight: bold;
  font-size: 16px;  
  color: ${theme.colors.dark2};

  &:hover{
    cursor: pointer;
    text-decoration: underline;
  }
`;

export const ojName = css`
  padding: 10px 15px ;
  border-radius: 20px;
  color: white;
  `;

  export const infoStyle = css`
  width: 370px;
  height: 565px;
  border-radius: 5px;
  padding-top: 10px;

  flex-direction: column;
  overflow-y: auto; 
  max-height: 565px;
  
  &::-webkit-scrollbar {
    width: 10px;
  }
  
  &::-webkit-scrollbar-thumb {
    background-color: ${theme.colors.light2};
    border-radius: 40px;
  }
  
  &::-webkit-scrollbar-track {
    background-color: transparent;
  }
  
  &::-webkit-scrollbar-corner{
  background-color: transparent;
  border-radius: 40px;
  }

  background-color: ${theme.colors.light4}
`;

export const badgeStyle = css`
  width: 50px;
  height: 50px;
  margin-right: 6px;
  `;