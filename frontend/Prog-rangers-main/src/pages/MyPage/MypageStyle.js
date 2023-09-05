import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';

export const fontSize16 = css`
  font-size: 16px;
  font-weight: 700;
  color: ${theme.colors.dark2}
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