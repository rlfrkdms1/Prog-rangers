import { css } from "@emotion/react";

export const Fragment = css`
  display: flex;
  flex-direction: column;
  position: relative;
`;

export const SelectBox = css`
  display: flex;
  flex-direction: row;
  border-radius: 25px;
  width: 230px;
  border: 1px solid #959595;
  height: 50px;
`;

export const OptionBox = css`
  font-size: 20px;
  // color: #959595;
  color: black;
  width: 230px;
  border: 1px solid black;
  text-align: center;
  &:hover{
    cursor: pointer;
  }
`;
