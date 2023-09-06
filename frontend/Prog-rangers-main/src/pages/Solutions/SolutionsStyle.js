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

export const filterStyle = css`
  margin: 25px 0 0 0;
  height: 50px;
  width: 998px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  z-index: 2;
  `;

  export const ojNameTag = css`
  display: flex;
  padding: 8px 20px;
  border-radius: 20px;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  `;