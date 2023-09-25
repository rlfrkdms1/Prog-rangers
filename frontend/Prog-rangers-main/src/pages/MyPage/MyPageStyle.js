import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';

export const fontSize12 = css`
  font-size: 12px;
  font-weight: 700;
  color: ${theme.colors.black}
  `;

export const fontSize14 = css`
  font-size: 14px;
  font-weight: 400;
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
  height: 62px; 
  border-bottom: 1px solid #959595
  `;

export const RecentlyTitle = css`
  height: 29px; 
  width: 100%;  
  font-weight: bold;
  font-size: 16px;
  color: ${theme.colors.dark2};
`;

export const ojName = css`
  display: inline-flex;
  justify-content: center;
  align-items: center;
  padding: 5px 15px;
  border-radius: 20px;
  color: white;
  `;