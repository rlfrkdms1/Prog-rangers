import { css } from "@emotion/react";
import { SideBar } from "../../components/SideBar/SideBar";
import { EditMySolution } from "../../components/WriteBoard";
import { Provider, atom } from 'jotai';
import { useState, useEffect } from 'react';
import { RightBar } from "../../components/MySolutionDetail";
import { useParams } from "react-router-dom";

export const targetScope = Symbol();
export const targetAtom = atom("");
export const valueScope = Symbol();
export const valueAtom = atom("");
export const nameScope = Symbol();
export const nameAtom = atom("");

//mypage-my solution-add solution 페이지
//mypage 정보를 미리 가져와서 추가수정기능

const EditSolution = () => {
  const currentURL = window.location.href.split('/');
  const indexOfSolution = currentURL.indexOf("solutions");
  const id =  useParams();

  //주소에서 solution뒤에 id값 나오면 그 id값이 problem 번호가 됨
  // useEffect(() => {
  //   if(indexOfSolution !== -1 && indexOfSolution < currentURL.length - 1){
  //     setId(currentURL[indexOfSolution + 1]);
  //     console.log(id);
  //   }else{
  //     alert("해당되는 문제번호가 없습니다.");
  //   }
  // }, []);

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
      >
      <div css={css`height: 100%; width: 810px; display: flex; flex-direction: column;`}>
        <Provider scope={targetScope}>
          <EditMySolution />
        </Provider>
      </div>

      </div>
    <RightBar/>
    </div>
  );
};

export default EditSolution;