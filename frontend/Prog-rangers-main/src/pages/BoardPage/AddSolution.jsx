import { css } from "@emotion/react";
import { SideBar } from "../../components/SideBar/SideBar";
import { AddMySolution } from "../../components/WriteBoard";
import { ButtonDiv, SubmitButton } from "./buttonDiv";
import { Provider, atom, useAtom } from 'jotai';

export const targetScope = Symbol();
export const targetAtom = atom("");
export const nameScope = Symbol();
export const nameAtom = atom("");

export const AddSolution = () => {
  const refresh = () => {
    localStorage.removeItem('algorithm');
    localStorage.removeItem('datastructure');
  }

  return(
  <div 
    className='container' 
    css={css`
    width: 1200px;
    height: 100%;
    display: flex;
    justify-content: space-between;
    margin: 0 auto;
  ` }
  >
    <SideBar />
      <div
        className='content'
        css={css`
        width: 100%;`}
      >
      <div css={css`height: 100%; width: 810px; display: flex; flex-direction: column;`}>
        <Provider scope={targetScope}>
          <AddMySolution />
        </Provider>
        <div css={css`${ButtonDiv}`}>
          <button onClick={refresh()} css={css`${SubmitButton} margin-right: 20px; background-color: #F0F0F0;`}>작성 취소</button>
          <button css={css`${SubmitButton} background-color: #C2DBE3;`}>작성 완료</button>
        </div>
      </div>

      </div>
    </div>
  );
};