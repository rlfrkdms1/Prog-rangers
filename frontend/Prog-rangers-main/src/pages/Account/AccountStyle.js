import { css } from '@emotion/react';
import { theme } from '../../components/Header/theme';

export const profileContentStyle = css`
    display: flex;
    flex-direction: row;
    align-items: center;
    gap: 54px;
    margin-bottom: 30px;
`;

export const editBtnStyle = css`
    width: 120px;
    height: 50px;
    font-size: 18px;
    text-decoration: none;
    color: inherit;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 25px;
    background: ${theme.colors.main30};
    margin-top: 30px;
    cursor: pointer;
`;

export const deleteBtnStyle = css`
    width: 100px;
    height: 40px;
    border: 1px solid ${theme.colors.light1};
    border-radius: 25px;
    color: ${theme.colors.dark1};
    font-size: 14px;
    font-weight: 700;
    margin-left: 10px;
`;

export const grayBtn = css`
    width: 120px;
    height: 40px;
    font-size: 14px;
    text-decoration: none;
    color: inherit;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 20px;
    margin-left: 10px;
    background: ${theme.colors.light2};
`;

export const blueBtn = css`
    width: 120px;
    height: 40px;
    font-size: 14px;
    text-decoration: none;
    color: inherit;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 20px;
    margin-left: 10px;
    background: ${theme.colors.main30};
`;

export const alertStyle = css`
    font-size:12px; 
    color:${theme.colors.main};
`