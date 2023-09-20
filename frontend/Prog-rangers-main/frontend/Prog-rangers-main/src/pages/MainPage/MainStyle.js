import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';

// section

export const secLayout = css`
  width: 1200px;
  margin: 0 auto;
`;

export const secLight = css`
  width: 100%;
  height: 100vh;
  background-color: ${theme.colors.light4};
`;

export const secMain = css`
  width: 100%;
  height: 100vh;
  background-color: ${theme.colors.main30};
`;

//fonts

export const fontSize40 = css`
  font-size: 40px;
  font-weight: 700;
  color: ${theme.colors.dark1};
`;

export const fontSize50 = css`
  font-size: 50px;
  font-weight: 700;
  color: ${theme.colors.dark1};
`;

export const fontSize70 = css`
  font-size: 70px;
  font-weight: 700;
  color: ${theme.colors.dark1};
`;

export const fontSize26 = css`
  font-size: 26px;
  font-weight: 500;
  color: ${theme.colors.dark2};
`;

export const alignCenter = css`
  text-align: center;
`;

// flex

export const flexRowLayout = css`
  display: flex;
  justify-content: space-between;
  flex-direction: row;
`;

export const flexColLayout = css`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
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
