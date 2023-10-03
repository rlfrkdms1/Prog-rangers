import { css } from "@emotion/react";
import { SideBar } from "../../components/SideBar/SideBar";
import { AddMySolution } from "../../components/WriteBoard";
import { ButtonDiv, SubmitButton } from "./buttonDiv";
import { Provider, atom, useAtom } from 'jotai';

export const targetScope = Symbol();
export const targetAtom = atom("");
export const nameScope = Symbol();
export const nameAtom = atom("");

//mypage-my solution-add solution 페이지

export const AddSolution = () => {
  const refresh = () => {
    localStorage.removeItem('algorithm');
    localStorage.removeItem('datastructure');
    // localStorage.clear();
  }

  const APIURL = `http://13.124.131.171:8080/api/v1/solutions`;
  return(
    <div 
      css={css`
      width: 1200px;
      height: 100%;
      display: flex;
      flex-direction: column;
      align-items: center;
      margin: 0 auto;
    ` }
    >
      <div css={css`height: 100%; width: 810px; display: flex; flex-direction: column;`}>
        <Provider scope={targetScope}>
          <AddMySolution postURL={APIURL} />
        </Provider>
        {/* <div css={css`${ButtonDiv} margin-bottom: 80px;`}>
          <button onClick={refresh()} css={css`${SubmitButton} margin-right: 20px; background-color: #F0F0F0;`}>작성 취소</button>
          <button css={css`${SubmitButton} background-color: #C2DBE3;`} >작성 완료</button>
        </div> */}
      </div>
    </div>
  );
};