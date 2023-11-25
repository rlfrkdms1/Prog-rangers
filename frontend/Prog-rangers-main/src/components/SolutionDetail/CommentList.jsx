import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

import { CommentReplyForm } from './CommentReplyForm';

import { css } from "@emotion/react";
import { theme } from "../Header/theme";
import { 
  rowFlex,
  rowFlexRecomment,
  editStyle,
  deleteStyle, 
} from "./commentsStyle";

import ProfileImg from './profile/default.png';
import dot from '../../assets/icons/dotLight.svg'
const token = localStorage.getItem('token');

export const CommentList = () => {

    const navigate = useNavigate();
    const onClickName = (nickname) => {
      navigate(`/profile/${nickname}`); 
    };

    const { solutionId } = useParams();
    const [ comments, setComments ] = useState([]);
    const [ replies, setReplies ] = useState([]);
    const [ commentIdToDelete, setCommentIdToDelete ] = useState(null);
    const [ newComment, setNewComment ] = useState(null);

    // 수정 삭제 버튼
    const [isOpen, setIsOpen] = useState(false);

    // 답글 버튼
    const [ replyFormVisible, setReplyFormVisible ] = useState(false);
    const [ selectedCommentId, setSelectedCommentId ] = useState(null);
  
    const handleToggle = (commentId) => {
      setIsOpen((prevState) => {
        const updatedState = { ...prevState };
        updatedState[commentId] = !updatedState[commentId];
        return updatedState;
      });
    };

    useEffect(() => {

      const apiUrl = `http://13.124.131.171:8080/api/v1/solutions/${solutionId}`;
  
      axios
      .get(apiUrl, {
        method: "GET",
        headers: {Authorization: `Bearer ${token}`}
      })
          .then((response) => {
            setComments(response.data.comments);
            setNewComment(response.data.newComment);            
            setReplies(response.data.comments.replies);
          })
          .catch((error) => {
            console.error('API 요청 오류:', error);
          });
      }, []); 

    // const api = axios.create({
    //   baseURL: 'http://13.124.131.171:8080/api/v1',  // API의 기본 URL
    // });

    // useEffect(() => {
    //   const fetchData = async () => {
    //     try {
    //       // API 호출하여 팔로잉 데이터를 받아옴
    //       const response = await api.get(`/solutions/${solutionId}`, {
    //         method: 'GET',
    //         headers: {
    //           Authorization: `Bearer ${token}`,
    //         },
    //       });
          
    //       const { comments, newComment } = response.data;
          
    //       setComments(comments);
    //       setNewComment(newComment);
          
    //       // 만약 댓글과 관련된 replies가 있다면 설정
    //       if (comments && comments.replies) {
    //         setReplies(comments.replies);
    //       } else {
    //         setReplies([]);
    //       }   
            
    //     } catch (error) {
    //       console.error("Error fetching following data:", error);
    //     }
    //   };
  
    //   fetchData();
    // }, []);

      // 댓글 삭제
      const handleDeleteComment = (commentId) => {
        
        axios
          .delete((`http://13.124.131.171:8080/api/v1/comments/${commentId}`),{
            method: "DELETE",
            headers: {Authorization: `Bearer ${token}`}
          })
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
      const handleEditComment = (commentId, editValue) => {
        
        axios
        .patch(`http://13.124.131.171:8080/api/v1/comments/${commentId}`, 
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
      const targetComment = updatedComments.find(comment => comment.id === commentId);
    
      if (targetComment) {
        // commentId에 해당하는 댓글이 있으면 그 댓글의 replies에 새로운 답글 추가
        targetComment.replies = [...targetComment.replies, newReply];
    
        // 상태 업데이트
        setComments(updatedComments);
      }
    };    

  return (
    <div className="comments">
        {comments
        .filter((commentItem) => commentItem.content !== '삭제된 댓글입니다')
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

              <div
                css={css`
                margin-left: 20px;
                gap: 15px;
                display: flex;
                align-items: center;       
              `}>
                <button
                  className="addReply"
                  css={css`
                  font-size: 14px;
                  color: ${theme.colors.light1};
                  `}
                  onClick={() => handleReplyClick(commentItem.id)}
                >
                  답글 달기
                </button>
                                
                <div>
                  <button onClick={() => handleToggle(commentItem.id)}
                    css={css`
                    display: flex; 
                    align-item: center;
                  `}>
                    <img src={dot} alt="dot"/>
                  </button>
                </div>

                <div className="edit-delete-buttons" 
                  css={css`
                  display: flex;
                  margin-left: -10px;
                  text-align: center;`}>
                    <button 
                    css={editStyle(isOpen[commentItem.id])} 
                    onClick={() => {
                      if (commentItem.editing) {
                        // 수정 중인 경우, 수정 완료 버튼 > 수정 모드에서 벗어나고 서버에 저장
                        toggleEditComment(commentItem.id);
                        handleEditComment(commentItem.id, commentItem.content);
                      }
                       else {
                        // 수정 중이 아니면 수정 모드로 전환
                        toggleEditComment(commentItem.id);
                      }
                    }}
                    >
                      {commentItem.editing ? '수정 완료' : '수정'} 
                    </button>         
                       
                    <button 
                    css={deleteStyle(isOpen[commentItem.id])} 
                    onClick={() => {
                      setCommentIdToDelete(commentItem.id); // 삭제할 댓글의 ID 설정
                      handleDeleteComment(commentItem.id);  // 댓글 삭제 함수 호출
                    }}
                    >
                      삭제
                    </button>
                </div>
              </div>

              <div css={css` margin-left: 20px;`}>
                {replyFormVisible && selectedCommentId === (commentItem && commentItem.id) && (
                <CommentReplyForm 
                onAddReply={addReply}
                parentId={(commentItem && commentItem.id)} 
                setComments={setComments} 
                comments={comments} 
                />
                )}
              </div>
            </div>

            </div>
            
            {/* 답글 렌더링 */}
            {commentItem.replies && Array.isArray(commentItem.replies) && (
            <div className="repliesSection">
              {commentItem.replies
              .filter((reply) => reply && reply.content !== '삭제된 댓글입니다')
              .map((reply) => (
                <div key={reply && reply.id} className="recomment" css={rowFlexRecomment}>
                 
                <div className="profile">
                  <img
                  src={reply.photo || ProfileImg}
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
                    onClick={() => onClickName(reply.nickname)}
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
                
                  <div className="edit-delete-buttons" 
                    css={css`
                    display: flex;
                    margin-top: 10px;
                    font-size: 12px;
                    text-align: center;`}>
                    <button 
                      css={css`color: ${theme.colors.light1};`} 
                      onClick={() => {
                        handleDeleteComment(reply.id);  // 댓글 삭제 함수 호출
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