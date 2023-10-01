import { css } from "@emotion/react";
import { ScrapBoard } from "../../components/WriteBoard/ScrapBoard";
import { SubmitButton } from "./buttonDiv";

export const Scrap = () => {
  return(
    <div css={css`
      width: 100%; 
      height: 1700px; 
      display: flex; 
      flex-direction: column; 
      align-items: center; 
    `}>
      <ScrapBoard/>
      <div css={css`
        margin-top: 100px;
        height: 50px; 
        width: 996px;
        display: flex;
        flex-direction: row;
        justify-content: flex-end;
      `}>
        <button css={css`${SubmitButton} margin-right: 20px; background-color: #F0F0F0;`}>작성 취소</button>
        <button css={css`${SubmitButton} background-color: #C2DBE3;`}>작성 완료</button>
      </div>
    </div>
  );
};