import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

import { css } from "@emotion/react";
import { theme } from "../Header/theme";
import { 
  rowFlex,
  rowFlexRecomment 
} from "./commentsStyle";

import ProfileImg from './profile/default.png';

export const CommentList = () => {

    const navigate = useNavigate();
    const onClickName = (nickname) => {
      navigate(`/profile/${nickname}`); 
    };

  const { solutionId } = useParams();
  const [ comments, setComments ] = useState([]);
  const [commentIdToDelete, setCommentIdToDelete] = useState(null);
  const [newComment, setNewComment] = useState(null);

    useEffect(() => {
        const apiUrl = `http://13.124.131.171:8080/api/v1/solutions/${solutionId}`;
        
        axios
          .get(apiUrl)
          .then((response) => {
            setComments(response.data.comments);
          })
          .catch((error) => {
            console.error('API 요청 오류:', error);
          });
      }, [solutionId]); 
      
      // 댓글 삭제
      const handleDeleteComment = (commentId) => {
        axios
          .delete(`http://13.124.131.171:8080/api/v1/solutions/${solutionId}/comments/${commentId}`)
          .then((response) => {
            // 댓글 삭제에 성공한 경우, 화면에서 댓글을 제거
            const updatedComments = comments.filter((comment) => comment.id !== commentId);
            setComments(updatedComments);
          })
          .catch((error) => {
            console.error('댓글 삭제 오류:', error);
          });
      }; 
    
      // 댓글 수정
      const editComment = (commentId, editValue) => {
        const token = localStorage.getItem('token');

        axios
        .patch(`http://13.124.131.171:8080/api/v1/solutions/${solutionId}/comments/${commentId}`, 
        { content: editValue },
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        })
             
          .then((response) => {
            const updatedComments = comments.map(comment => {
              if (comment.id === commentId) {
                comment.content = editValue;
                comment.editing = false; // 수정이 완료되면 편집 모드 종료
              }
              return comment;
            });
            setComments(updatedComments);
          })

          .catch((error) => {
            console.error('댓글 수정 오류:', error);
          });
      };      
      
      // 편집 모드를 토글하는 함수
      const toggleEditComment = (commentId) => {
        const updatedComments = comments.map((comment) => {
          if (comment.id === commentId) {
            comment.editing = !comment.editing;
          }
          return comment;
        });
        setComments(updatedComments);
      };  
      
      // 댓글 내용 변경 시 호출되는 함수
      const onCommentContentChange = (commentId, newContent) => {
        const updatedComments = comments.map((comment) => {
          if (comment.id === commentId) {
            comment.content = newContent;
          }
          return comment;
        });
        setComments(updatedComments);
      };

  return (
    <div className="comments">
        {comments.map((commentItem) => (
          <div className="comment" css={rowFlex} key={commentItem.id}>
            <div className="profile">
              <img
                src={ProfileImg}
                alt="profile image"
                css={css`
                  width: 50px;
                  height: 50px;
                  border: 1px solid ${theme.colors.light1};
                  border-radius: 100%;
                `}
              />
            </div>
            <div
              className="text"
              css={css`
                margin-left: 20px;
              `}
            >    
            <div
            className="nickname"
            css={css`
              font-size: 14px;
              color: ${theme.colors.light1};
              margin-bottom: 5px;
              cursor: pointer;
            `}
            onClick={() => onClickName(commentItem.nickname)} 
          >
            {commentItem.nickname}
          </div>
          
          <div className="commnetText">            
            {commentItem.editing ? ( 
              <input
                type="text"
                value={commentItem.content}
                onChange={(e) => onCommentContentChange(commentItem.id, e.target.value)}
                onBlur={() => toggleEditComment(commentItem.id)} // 수정 모드에서 벗어날 때
              />
            ) : (
              <div className="commnetText" onClick={() => toggleEditComment(commentItem.id)}>
                {commentItem.content}
              </div>
            )}
          </div>
            
        </div>
          
          <button
            onClick={() => {
              if (commentItem.editing) {
                // 수정 중인 경우, 수정 완료 버튼 > 수정 모드에서 벗어나고 서버에 저장
                toggleEditComment(commentItem.id);
                editComment(commentItem.id, commentItem.content);
              } else {
                // 수정 중이 아니면 수정 모드로 전환
                toggleEditComment(commentItem.id);
              }
            }}
            css={css`
              color: ${commentItem.editing ? 'green' : 'red'}; // 수정 중일 때 초록색, 아닐 때 빨간색
              border: none;
              cursor: pointer;
            `}
          >
            {commentItem.editing ? '수정 완료' : '수정하기'} 
          </button>

            <button
            onClick={() => {
              setCommentIdToDelete(commentItem.id); // 삭제할 댓글의 ID 설정
              handleDeleteComment(commentItem.id);  // 댓글 삭제 함수 호출
            }}
            css={css`
            color: blue;
            border: none;
            cursor: pointer;
            `}
            >
            삭제하기
            </button>

          </div>
        ))}
        
        {comments.replies && Array.isArray(comments.replies) && comments.replies.map((replyItem) => (
          <div key={replyItem.id} className="recomment" css={rowFlexRecomment}>
            <div className="profile">
              <img
                src={ProfileImg}
                alt="profile image"
                css={css`
                  width: 40px;
                  height: 40px;
                  border: 1px solid ${theme.colors.light1};
                  border-radius: 100%;
                `}
              />
            </div>
            <div
              className="text"
              css={css`
                margin-left: 20px;
              `}
            >
              <div
                className="nickname"
                css={css`
                  font-size: 12px;
                  color: ${theme.colors.light1};
                  margin-bottom: 5px;
                `}
                onClick={() => onClickName(replyItem.nickname)} 
              >
                {replyItem.nickname}
              </div>
              <div
                className="commnetText"
                css={css`
                  font-size: 14px;
                `}
              >
                <span
                  className="tag"
                  css={css`
                    font-size: 12px;
                    color: ${theme.colors.main};
                    margin-right: 10px;
                  `}
                >
                  @{comments.nickname}
                </span>
                {replyItem.content}
              </div>
            </div>

            // 추가된 댓글
            <div
                className="nickname"
                css={css`
                  font-size: 14px;
                  color: ${theme.colors.light1};
                  margin-bottom: 5px;
                  cursor: pointer;
                `}
                onClick={() => onClickName(newComment.nickname)} 
              >
                {newComment.nickname}
              </div>
              <div className="commnetText">
                {newComment.content}
              </div>

          </div>
        ))}
    
    </div>
  );
};