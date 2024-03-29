import React, { useState, useEffect, useRef } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

import { CommentReplyForm } from './CommentReplyForm';

import { css } from '@emotion/react';
import { theme } from '../Header/theme';
import {
  rowFlex,
  rowFlexRecomment,
  editStyle,
  deleteStyle,
} from './commentsStyle';

import ProfileImg from './profile/default.png';
import dot from '../../assets/icons/dotLight.svg';
const token = localStorage.getItem('token');

export const CommentList = ( totalCount ) => {
  const navigate = useNavigate();
  const onClickName = (nickname) => {
    navigate(`/profile/${nickname}`);
  };

  const { solutionId } = useParams();
  const [comments, setComments] = useState([]);
  const [replies, setReplies] = useState([]);
  const [commentIdToDelete, setCommentIdToDelete] =useState(null);
  const [newComment, setNewComment] = useState(null);

  // 수정 삭제 버튼
  const [isOpen, setIsOpen] = useState(false);

  // 답글 버튼
  const [replyFormVisible, setReplyFormVisible] =
    useState(false);
  const [selectedCommentId, setSelectedCommentId] =
    useState(null);

  const handleToggle = (commentId) => {
    setIsOpen((prevState) => {
      const updatedState = { ...prevState };
      updatedState[commentId] = !updatedState[commentId];
      return updatedState;
    });
  };

  useEffect(() => {
    const fetchData = async () => {
    const apiUrl = `http://13.125.13.131:8080/api/v1/solutions/${solutionId}`;

    // 토큰이 있는 경우와 없는 경우에 대한 설정
    const axiosConfig = token
      ? { headers: { Authorization: `Bearer ${token}` } }
      : {};

    axios
      .get(apiUrl, axiosConfig)
      .then((response) => {
        setComments(response.data.comments);
        setNewComment(response.data.newComment);
        setReplies(response.data.comments.replies);
      })
      .catch((error) => {
        console.error('API 요청 오류:', error);
      });
    }
    fetchData();
  }, [solutionId, token, totalCount]);
  
  // 댓글 삭제
  const handleDeleteComment = (commentId) => {
    const commentToDelete = comments.find((comment) => comment.id === commentId);
  
    // 댓글이 존재하고 해당 댓글의 mine 속성이 true일 때 삭제
    if (commentToDelete && commentToDelete.mine) {
      axios
        .delete(
          `http://13.125.13.131:8080/api/v1/comments/${commentId}`,
          {
            method: 'DELETE',
            headers: { Authorization: `Bearer ${token}` },
          }
        )
        .then((response) => {
          // 댓글 삭제에 성공한 경우, 화면에서 댓글을 제거
          const updatedComments = comments.filter(
            (comment) => comment.id !== commentId
          );
          setComments(updatedComments);
        })
        .catch((error) => {
          console.error('댓글 삭제 오류:', error);
        });
    } else {
      alert('접근 권한이 없습니다.');
    }
  };
  

  // 댓글 수정
  const handleEditComment = (commentId, editValue) => {
    axios
      .patch(
        `http://13.125.13.131:8080/api/v1/comments/${commentId}`,
        { content: editValue },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )

      .then((response) => {
        const updatedComments = comments.map((comment) => {
          if (comment.id === commentId) {
            return {
              ...comment,
              content: editValue,
              editing: false,
            }; // 수정이 완료되면 편집 모드 종료
          }
          return comment;
        });
        setComments(updatedComments);
      })

      .catch((error) => {
        console.error('댓글 수정 오류:', error);
      });
  };
  
  // 수정 모드를 토글하는 함수
  const toggleEditComment = (commentId) => {
    const commentToUpdate = comments.find((comment) => comment.id === commentId);
  
    // 댓글이 존재하고 해당 댓글의 mine 속성이 true일 때 수정 모드를 토글
    if (commentToUpdate && commentToUpdate.mine) {
      const updatedComments = comments.map((comment) => {
        if (comment.id === commentId) {
          return {
            ...comment,
            editing: !comment.editing,
          };
        }
        return comment;
      });
      setComments(updatedComments);
    } else {
      alert('접근 권한이 없습니다.');
    }
  };
  
  // 댓글 내용 변경 시 호출되는 함수
  const onCommentContentChange = (
    commentId,
    newContent
  ) => {
    const updatedComments = comments.map((comment) => {
      if (comment.id === commentId) {
        comment.content = newContent;
      }
      return comment;
    });

    // 새로운 댓글도 업데이트
    if (newComment && newComment.id === commentId) {
      setNewComment({
        ...newComment,
        content: newContent,
      });
    }
    setComments(updatedComments);
  };

  // 답글 달기
  const handleReplyClick = (commentId) => {
    // 클릭 시 CommentReplyForm 보이도록 상태 업데이트
    setSelectedCommentId(commentId);
    setReplyFormVisible(!replyFormVisible);
  };

  const addReply = (newReply, commentId) => {
    // comments 배열 복사
    const updatedComments = [...comments];

    // commentId에 해당하는 댓글 찾기
    const targetComment = updatedComments.find(
      (comment) => comment.id === commentId
    );

    if (targetComment) {
      // commentId에 해당하는 댓글이 있으면 그 댓글의 replies에 새로운 답글 추가
      targetComment.replies = [
        ...targetComment.replies,
        newReply,
      ];

      // 상태 업데이트
      setComments(updatedComments);
    }
  };
  
  // // 외부 클릭시 dot 닫힘
  // const closeOnOutsideClick = (e) => {
  //   if (isOpen) {
  //     const dotMenus = document.querySelectorAll('.dot-menu');
  //     dotMenus.forEach((dotMenu) => {
  //       if (dotMenu && !dotMenu.contains(e.target)) {
  //         const commentId = dotMenu.getAttribute('data-comment-id');
  //         setIsOpen((prevState) => {
  //           const updatedState = { ...prevState };
  //           updatedState[commentId] = false;
  //           return updatedState;
  //         });
  //       }
  //     });
  //   }
  // };
  
  // React.useEffect(() => {
  //   window.addEventListener('click', closeOnOutsideClick);
  //   return () => {
  //     window.removeEventListener('click', closeOnOutsideClick);
  //   };
  // }, [isOpen]);

  // 외부 클릭시 한줄리뷰 닫힘
  const commentsRef = useRef();

  const closeOnOutsideClick = (event) => {
    if (
      commentsRef.current &&
      !commentsRef.current.contains(event.target)
    ) {
      setIsOpen(false);
    }
  };


  React.useEffect(() => {
    window.addEventListener('click', closeOnOutsideClick);
    return () => {
      window.removeEventListener('click', closeOnOutsideClick);
    };
  }, [isOpen]);

  return (
    <div className="comments" ref={commentsRef}>
      {comments
        .map((commentItem) => (
          <div className="comment" key={commentItem.id}>
            <div css={rowFlex}>
              {/* 댓글 작성자 프로필 */}
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

              {/* 댓글 텍스트 */}
              <div>
                <div
                  className="text"
                  css={css`
                    margin-left: 20px;
                    gap: 10px;
                    display: flex;
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
                    onClick={() =>
                      onClickName(commentItem.nickname)
                    }
                  >
                    {commentItem.nickname}
                  </div>

                  <div className="commnetText">
                    {commentItem.editing ? (
                      <input
                        type="text"
                        value={commentItem.content}
                        onChange={(e) =>
                          onCommentContentChange(
                            commentItem.id,
                            e.target.value
                          )
                        }
                        onBlur={() =>
                          toggleEditComment(commentItem.id)
                        } // 수정 모드에서 벗어날 때
                        css={css`
                        width: ${(commentItem.content.length + 3) * 12}px;
                        border: 1px solid #111;
                        border-radius: 25px;
                        padding-left: 10px;
                      `}
                        
                      />
                    ) : (
                      <div
                        className="commnetText"
                        onClick={() =>
                          toggleEditComment(commentItem.id)
                        }
                      >
                        {commentItem.content}
                      </div>
                    )}
                  </div>
                </div>

                <div
                  css={css`
                    margin-left: 20px;
                    margin-top: 5px;
                    gap: 15px;
                    display: flex;
                    align-items: center;
                  `}
                >
                  <button
                    className="addReply"
                    css={css`
                      font-size: 14px;
                      color: ${theme.colors.light1};
                    `}
                    onClick={() =>
                      handleReplyClick(commentItem.id)
                    }
                  >
                    답글 달기
                  </button>

                  <div>
                    <button
                      onClick={() =>
                        handleToggle(commentItem.id)
                      }
                      css={css`
                        display: flex;
                        align-items: center;
                      `}
                    >
                      <img src={dot} alt="dot" className="dot-menu" data-comment-id={commentItem.id} />
                    </button>
                  </div>

                  <div
                    className="edit-delete-buttons"
                    css={css`
                      display: flex;
                      margin-left: -10px;
                      text-align: center;
                    `}
                  >
                    <button
                      css={editStyle(
                        isOpen[commentItem.id]
                      )}
                      onClick={() => {                   
                        if (commentItem.editing) {
                          // 수정 중인 경우, 수정 완료 버튼 > 수정 모드에서 벗어나고 서버에 저장
                          toggleEditComment(commentItem.id);
                          handleEditComment(
                            commentItem.id,
                            commentItem.content
                          );
                        } 
                          toggleEditComment(commentItem.id);
                      }}
                    >
                      {commentItem.editing
                        ? '수정 완료'
                        : '수정'}
                    </button>

                    <button
                      css={deleteStyle(
                        isOpen[commentItem.id]
                      )}
                      onClick={() => {
                        setCommentIdToDelete(
                          commentItem.id
                        ); // 삭제할 댓글의 ID 설정
                        handleDeleteComment(commentItem.id); // 댓글 삭제 함수 호출
                      }}
                    >
                      삭제
                    </button>
                  </div>
                </div>

                <div
                  css={css`
                    margin-left: 20px;
                  `}
                >
                  {replyFormVisible &&
                    selectedCommentId ===
                      (commentItem && commentItem.id) && (
                      <CommentReplyForm
                        onAddReply={addReply}
                        parentId={
                          commentItem && commentItem.id
                        }
                        setComments={setComments}
                        comments={comments}
                      />
                    )}
                </div>
              </div>
            </div>

            {/* 답글 렌더링 */}
            {commentItem.replies &&
              Array.isArray(commentItem.replies) && (
                <div className="repliesSection">
                  {commentItem.replies
                    .map((reply) => (
                      <div
                        key={reply && reply.id}
                        className="recomment"
                        css={rowFlexRecomment}
                      >
                        <div className="profile">
                          <img
                            src={reply.photo || ProfileImg}
                            alt="profile image"
                            css={css`
                              width: 40px;
                              height: 40px;
                              border: 1px solid
                                ${theme.colors.light1};
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
                            onClick={() =>
                              onClickName(reply.nickname)
                            }
                          >
                            {reply.nickname}
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
                              @{commentItem.nickname}
                            </span>
                            {reply.content}
                          </div>

                          <div
                            className="edit-delete-buttons"
                            css={css`
                              display: flex;
                              margin-top: 10px;
                              font-size: 12px;
                              text-align: center;
                            `}
                          >
                            <button
                              css={css`
                                color: ${theme.colors
                                  .light1};
                              `}
                              onClick={() => {
                                handleDeleteComment(
                                  reply.id
                                );
                              }}
                            >
                              삭제
                            </button>
                          </div>
                        </div>
                      </div>
                    ))}
                </div>
              )}
          </div>
        ))}
    </div>
  );
};
