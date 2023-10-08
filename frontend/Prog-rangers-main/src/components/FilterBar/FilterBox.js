import { css } from "@emotion/react";

export const Fragment = css`
  display: flex;
  flex-direction: column;
  position: relative;
  `;

export const SelectBox = css`
  display: flex;
  flex-direction: row;
  border: 1px solid #959595;
  box-shadow: 0px 8px 8px 0px rgba(100, 116, 139, 0.1);
  height: 50px;
`;

export const SelectBox2 = css`
  display: flex;
  flex-direction: row;
  width: 185px;
  border: 1px solid #959595;
  box-shadow: 0px 8px 8px 0px rgba(100, 116, 139, 0.1);
  height: 40px;
`;

export const Wrapper = (props) => css`
  display: ${props.isOpen ? "flex" : "none"}; 
  flex-direction: column; 
  max-height: 200px;
  margin-top: 50px;
  background-color: white;
  border-radius: 0 0 25px 25px;
  border: 1px solid #959595;
  box-shadow: 0px 8px 8px 0px rgba(100, 116, 139, 0.1);
  overflow: scroll;
  overflow-y: auto;
  position: absolute;
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
  
  &::-webkit-scrollbar-corner{
  background-color: transparent;
  border-radius: 40px;
  }
`;

export const Wrapper2 = (props) => css`
  display: ${props.isOpen ? "flex" : "none"}; 
  flex-direction: column; 
  width: 185px;
  max-height: 160px;
  margin-top: 40px;
  background-color: white;
  border-radius: 0 0 25px 25px;
  border: 1px solid #959595;
  box-shadow: 0px 8px 8px 0px rgba(100, 116, 139, 0.1);
  overflow: scroll;
  overflow-y: auto;
  position: absolute;
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
  
  &::-webkit-scrollbar-corner{
  background-color: transparent;
  border-radius: 40px;
  }
`;

export const OptionBox = css`
  box-sizing: border-box;
  font-size: 18px;
  color: #959595;
  width: 100%;
  padding: 6px 0 6px 30px;

  &:hover{
    cursor: pointer;
    background-color: #f4f4f4;
  }
`;