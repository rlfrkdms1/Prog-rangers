import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { css } from '@emotion/react';

export const CommentReplyForm = ({
  onAddReply,
  parentId,
  comments,
  setComments,
}) => {
  const { solutionId } = useParams();
  const [nickname, setNickname] = useState('');
  const [content, setContent] = useState('');
  const [showReplyForm, setShowReplyForm] = useState(true);

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

    if (!token) {
      alert('답글을 작성하려면 로그인이 필요합니다.');
    } else if (content.trim() !== '') {
      const newReply = {
        nickname: nickname,
        content,
        parentId,
      };

      axios
        .post(
          `http://13.125.13.131:8080/api/v1/solutions/${solutionId}/comments`,
          newReply,
          {
            method: 'POST',
            headers: { Authorization: `Bearer ${token}` },
          }
        )
        .then((response) => {
          const commentId = response.data.id; // 새로 추가된 댓글의 ID
          const newReply =
            response.data.replies &&
            response.data.replies.length > 0
              ? response.data.replies[0]
              : null;

          // 이 부분에서 onAddReply 함수를 호출하여 부모 컴포넌트에 전달
          onAddReply(parentId, newReply);

          // 댓글 목록 업데이트
          setComments((prevComments) => {
            const updatedComments = [...prevComments];
            const targetComment = updatedComments.find(
              (comment) => comment.id === parentId
            );

            if (targetComment) {
              targetComment.replies = [
                ...targetComment.replies,
                newReply,
              ];
            }

            return updatedComments;
          });

          // 댓글이 등록되면 replyForm 숨기기
          setShowReplyForm(false);
          setContent('');
        })
        .catch((error) => {
          console.error(
            '댓글 등록 저장 API 요청 오류:',
            error
          );
        });
    }
  };

  return (
    <>
      {showReplyForm && ( // showReplyForm이 true인 경우에만 replyForm 렌더링
        <div
          className="replyForm"
          css={css`
            width: 650px;
            height: 40px;
            border: 1px solid #111;
            border-radius: 25px;
            margin-top: 20px;
            padding-top: 5px;
            padding-left: 30px;
          `}
        >
          <input
            type="text"
            placeholder="댓글을 입력해주세요"
            value={content}
            onChange={handleChange}
            css={css`
              outline: none;
              border: none;
              width: 87%;
            `}
          />

          <button
            type="submit"
            onClick={handleSubmit}
            css={css`
              width: 70px;
              height: 28px;
              border-radius: 15px;
              align-items: center;
              background-color: #c2dbe3;
            `}
          >
            등록
          </button>
        </div>
      )}
    </>
  );
};
