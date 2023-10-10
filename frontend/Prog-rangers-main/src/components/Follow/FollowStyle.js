import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';

// section

export const MainBody = css`
    width: 1200px;
    height: 100%;
    display: flex;
    justify-content: space-between;
    margin: 0 auto;
  `;

export const RightBody = css`
    width: 800px;
    margin: 50px 110px 0;
  `;


//fonts

export const fontSize18 = css`
  font-size: 18px;
  font-weight: 700;
  color: ${theme.colors.dark1};
`;

export const fontSize12 = css`
  font-size: 12px;
  font-weight: 500;
  color: ${theme.colors.dark1};
`;

export const fontSize14 = css`
  font-size: 14px;
  font-weight: 500;
  color: ${theme.colors.light1};
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
  width: 70px;
  height: 21px;
  background-color: ${theme.colors.main30};
  border-radius: 15px;

  font-size: 12px;
  font-weight: 500;
`;
