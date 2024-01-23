import React, { useState, useEffect, useRef } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { css } from '@emotion/react';

export const CommentForm = ({ addComment }) => {
  const commentInputRef = useRef(null);

  const focusCommentInput = () => {
    if (commentInputRef.current) {
      commentInputRef.current.focus();
    }
  };

  const { solutionId } = useParams();
  const [nickname, setNickname] = useState('');
  const [content, setContent] = useState('');

  // 로그인한 사용자 닉네임 API 요청
  useEffect(() => {
    const token = localStorage.getItem('token');
    const apiUrl = `http://13.125.13.131:8080/api/v1/members`;

    axios
      .get(apiUrl, {
        method: 'GET',
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        setNickname(response.data.nickname);
      })
      .catch((error) => {
        console.error('Nickname API 요청 오류:', error);
      });
  }, []);

  const handleChange = (e) => {
    const newContent = e.target.value;
    setContent(newContent);
  };

  // 댓글을 서버에 전송&추가
  const handleSubmit = () => {
    const token = localStorage.getItem('token');

    if (content.trim() !== '') {
      const newComment = {
        nickname: nickname,
        content,
      };

      axios
        .post(
          `http://13.125.13.131:8080/api/v1/solutions/${solutionId}/comments`,
          newComment,
          {
            method: 'POST',
            headers: { Authorization: `Bearer ${token}` },
          }
        )
        .then((response) => {
          addComment(...response.data);
          setContent('');
          focusCommentInput(); // 댓글 리스트로 포커스 이동
        })
        .catch((error) => {
          console.error(
            '댓글 등록 저장 API 요청 오류:',
            error
          );
          
      alert('로그인이 필요한 기능입니다.');
        });
    }
  };

  return (
    <div
      className="search"
      css={css`
        width: 996px;
        height: 50px;
        border: 1px solid #111;
        border-radius: 25px;
        margin-top: 30px;
        padding-top: 10px;
        padding-left: 30px;
      `}
    >
      <input
        type="text"
        placeholder="댓글을 입력해주세요"
        value={content}
        onChange={handleChange}
        ref={commentInputRef}
        css={css`
          outline: none;
          border: none;
          width: 90%;
        `}
      />

      <button
        type="submit"
        onClick={handleSubmit}
        css={css`
          width: 80px;
          height: 30px;
          border-radius: 15px;
          align-items: center;
          background-color: #c2dbe3;
        `}
      >
        등록
      </button>
    </div>
  );
};
