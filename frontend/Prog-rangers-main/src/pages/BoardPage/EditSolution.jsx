import { css } from "@emotion/react";
import { SideBar } from "../../components/SideBar/SideBar";
import { AddMySolution } from "../../components/WriteBoard";
import { ButtonDiv, SubmitButton } from "./buttonDiv";
import { Provider, atom, useAtom } from 'jotai';

export const targetScope = Symbol();
export const targetAtom = atom("");
export const valueScope = Symbol();
export const valueAtom = atom("");
export const nameScope = Symbol();
export const nameAtom = atom("");

//mypage-my solution-add solution 페이지
//mypage 정보를 미리 가져와서 추가수정기능

export const EditSolution = () => {

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
      </div>

      </div>
    </div>
  );
};