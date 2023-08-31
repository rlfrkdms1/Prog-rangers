import { css } from "@emotion/react";

export const Fragment = css`
  display: flex;
  flex-direction: column;
  position: relative;
`;

export const SelectBox = css`
  display: flex;
  flex-direction: row;
  width: 230px;
  border: 1px solid #959595;
  height: 50px;
`;

export const Wrapper = (props) => css`
  display: ${props.isOpen ? "flex" : "none"}; 
  flex-direction: column; 
  width: 230px;
  max-height: 200px;
  border-radius: 0 0 25px 25px;
  border: 1px solid #959595;
  box-shadow: 0px 8px 8px 0px rgba(100, 116, 139, 0.1);
  overflow: hidden;
  overflow-y: auto;
  z-index: 2;

  &::-webkit-scrollbar {
    width: 10px;
  }

  &::-webkit-scrollbar-thumb {
    background: #e2e8f0;
    border-radius: 40px;
  }

  &::-webkit-scrollbar-track {
    background-color: transparent;
  }
`;

export const OptionBox = css`
  font-size: 18px;
  color: #959595;
  width: 100%;
  text-align: center;
  margin-top: 8px;

  &:hover{
    cursor: pointer;
  }
`;
