import React, { useState } from 'react';
import { css } from '@emotion/react';
import { SideBar } from '../../components/SideBar/SideBar';
import { CommentForm } from '../../components/Comment';
import { Provider, atom, useAtom } from 'jotai';
import questions from '../../db/question.json';

const questionAtom = atom(questions);

export const MyComment = () => {
  const [userData, setUserData] = useState({
    nickname: '',
    email: '',
    github: '',
    introduction: '',
    currentModifiedAt: '',
    photo: '',
  });
  
  const [ Questions, setQuestions ] = useAtom(questionAtom);

  return (
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
        width: 800px;
        margin: 100px 110px 0;
        `}
        >

          <div css={css`height: 690px; width: 800px;  margin-top: 20px;`}>
          <CommentForm />
          </div>
      </div>
    </div>
  );
}
