// 기본 풀이 한줄리뷰 코드창

import React, { useState, useEffect, useRef } from 'react';
import { useParams } from 'react-router-dom';
import { css } from '@emotion/react';
import { theme } from '../Header/theme';
import axios from 'axios';
import hljs from 'highlight.js';
import './github-dark-dimmed.css';
import plusmark from '../../assets/icons/plus-mark.svg';

export const CodeWindow4 = () => {

  const token = localStorage.getItem('token');

  const { solutionId } = useParams();
  const [codeData, setData] = useState({
    solution: { code: [], reviews:[] },
  });

  // 한줄리뷰 추가 버튼
  const [hoveredLine, setHoveredLine] = useState(null);
  const [isBoxVisible, setIsBoxVisible] = useState(false);

  // 한줄리뷰
  const [clickedLineId, setClickedLineId] = useState(null);
  const [isReviewVisible, setIsReviewVisible] =
    useState(true);
  const [nickname, setNickname] = useState('');
  const [content, setContent] = useState('');
  const [reviews, setReviews] = useState([]);

  // 한줄리뷰 더보기
  const [showAllReviews, setShowAllReviews] =
    useState(false);

  // 한줄리뷰 수정삭제
  const [reviewIdToDelete, setReviewIdToDelete] =
    useState(null);
  const [newReview, setNewReview] = useState(null);

  // 수정 중인지 아닌지를 추적하는 상태 추가
  const [editValue, setEditValue] = useState('');

  const handleMouseEnter = (codeLineNumber) => {
    setHoveredLine(codeLineNumber);
  };

  const handleMouseLeave = () => {
    setHoveredLine(null);
  };

  const handleLineClick = (codeLineNumber) => {
    setClickedLineId(codeLineNumber);
    setIsBoxVisible(!isBoxVisible);

    // 해당 코드 줄에 대한 리뷰 정보 찾기
    const reviewForLine = codeData.reviews.find(
      (review) => review.codeLineNumber === codeLineNumber+1
    );

    // 찾은 리뷰 정보의 닉네임을 가져와서 설정
    if (reviewForLine) {
      setNickname(reviewForLine.nickname);
      setContent(reviewForLine.content);
      setIsReviewVisible(true); // 리뷰가 있으면 렌더링
    } else {
      setNickname('');
      setContent('');
      setIsReviewVisible(false); // 리뷰가 없으면 숨김
    }
    // 리뷰 클릭 시에 해당 리뷰의 편집 모드를 토글
    toggleEditReview();
  };

  const handleToggleReviews = () => {
    setShowAllReviews(!showAllReviews);
    setIsReviewVisible(!isReviewVisible);
  }

  const handleReviewChange = (codeLineNumber, value) => {
    const updatedReviews = [...reviews];
    if (!Array.isArray(updatedReviews)) {
    updatedReviews = [];
  }
    updatedReviews[codeLineNumber] = value;
    setReviews(updatedReviews);
  };

  useEffect(() => {
    const apiUrl = `http://13.125.13.131:8080/api/v1/solutions/${solutionId}`;
    
    // 토큰이 있는 경우와 없는 경우에 대한 설정
    const axiosConfig = token
      ? { headers: { Authorization: `Bearer ${token}` } }
      : {};

    axios
      .get(apiUrl, axiosConfig)
      .then((response) => {
        setData(response.data);
      })
      .catch((error) => {
        console.error('API 요청 오류:', error);
      });

    // 사용자 닉네임을 가져오는 API 요청 추가
    if (token) {
      axios
        .get('http://13.125.13.131:8080/api/v1/members', {
          headers: { Authorization: `Bearer ${token}` },
        })
        .then((response) => {
          setNickname(response.data.nickname);
        })
        .catch((error) => {
          console.error('API 요청 오류:', error);
        });
    }
  }, [solutionId, token]);

  const handleSaveReviews = () => {

    if (!token) {
      alert('로그인이 필요한 기능입니다.');
    } else if (
      reviews[clickedLineId] &&
      reviews[clickedLineId].trim() !== ''
    ) {
      const newReview = {
        nickname: nickname,
        codeLineNumber: clickedLineId+1,
        content: reviews[clickedLineId],
      };

      axios
        .post(
          `http://13.125.13.131:8080/api/v1/solutions/${solutionId}/reviews`,
          newReview,
          {
            headers: { Authorization: `Bearer ${token}` },
          }
        )
        .then((response) => {     
          // 리뷰 등록창 비우기
          setReviews(Array(10).fill(''));

          // 최근 리뷰만 저장하도록 업데이트
          setData({
            ...codeData,
            reviews: [...codeData.reviews, newReview],
          });

          // 서버에서 리뷰 데이터를 받아와 업데이트
          axios
            .get(
              `http://13.125.13.131:8080/api/v1/solutions/${solutionId}/reviews`
            )
            .then((response) => {
              // 받아온 리뷰 데이터를 사용하여 리뷰 상태 업데이트
              setReviews(response.data);
            })
            .catch((error) => {
              console.error(
                '리뷰 데이터 로드 중 오류 발생:',
                error
              );
            });

          // 대표 한줄리뷰 가리기
          setIsReviewVisible(false);
          setShowAllReviews(true);
        })
        .catch((error) => {
          console.error('리뷰 저장 중 오류 발생:', error);
        });
    }
  };
  
  // 한줄리뷰 삭제
  const handleDeleteReview = (reviewId) => {
    const reviewToDelete = codeData.reviews.find((review) => review.id === reviewId);

    if (reviewToDelete && reviewToDelete.mine) {
      axios
        .delete(
          `http://13.125.13.131:8080/api/v1/reviews/${reviewId}`,
          {
            method: 'DELETE',
            headers: { Authorization: `Bearer ${token}` },
          }
        )
        .then((response) => {
          const updatedReviews = codeData.reviews.filter(
            (review) => review.id !== reviewId
          );
          setData((prevData) => ({
            ...prevData,
            reviews: updatedReviews,
          }));
        })
        .catch((error) => {
          console.error('리뷰 삭제 오류:', error);
        });
    } else {
      alert('접근 권한이 없습니다.');
    }
  };
  

  // 한줄리뷰 수정
  const handleEditReview = (reviewId, editValue) => {
    const reviewToUpdate = codeData.reviews.find((review) => review.id === reviewId);
  
    if (reviewToUpdate && reviewToUpdate.mine) {
    axios
      .patch(
        `http://13.125.13.131:8080/api/v1/reviews/${reviewId}`,
        { content: editValue },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )

      .then((response) => {
        const updatedReviews = codeData.reviews.map((review) => {
          if (review.id === reviewId) {
            review.content = editValue;
            review.editing = false; // 수정이 완료되면 편집 모드 종료
          }
          return review;
        });
        setReviews(updatedReviews);

        // 한줄리뷰 내용 변경 호출
        onReviewContentChange(reviewId, editValue);
      })

      .catch((error) => {
        console.error('한줄 리뷰 수정 오류:', error);
      });
    } else {
      alert('접근 권한이 없습니다.');
    }
  };
  
  // 수정 모드를 토글하는 함수
  const toggleEditReview = (reviewId) => {
    const updatedReviews = codeData.reviews.map((review) => {
      if (review.id === reviewId) {
        review.editing = !review.editing;
      }
      return review;
    });
    setData({
      ...codeData,
      reviews: updatedReviews,
    });
  };
  
  // 한줄리뷰 내용 변경 시 호출되는 함수
  const onReviewContentChange = (reviewId, newContent) => {
    const updatedReviews = reviews.map((review) => {
      if (review.id === reviewId) {
        review.content = newContent;
      }
      return review;
    });

    // 새로운 한줄리뷰도 업데이트
    if (newReview && newReview.id === reviewId) {
      setNewReview({
        ...newReview,
        content: newContent,
      });
    }
    
    setReviews(updatedReviews);
  };

  // 외부 클릭시 한줄리뷰 닫힘
  const codeWindowRef = useRef();

  const closeOnOutsideClick = (event) => {
    if (
      codeWindowRef.current &&
      !codeWindowRef.current.contains(event.target)
    ) {
      setIsBoxVisible(false);
    }
  };


React.useEffect(() => {
  window.addEventListener('click', closeOnOutsideClick);
  return () => {
    window.removeEventListener('click', closeOnOutsideClick);
  };
}, [isBoxVisible]);

  return (
    <div style={{ position: 'relative' }} ref={codeWindowRef}>
      {codeData.solution.code.map(
        (line, codeLineNumber) => (
          <div
            key={codeLineNumber}
            css={css`
              padding-left: 20px;
              margin: 6px;
            `}
            onMouseEnter={() =>
              handleMouseEnter(codeLineNumber)
            }
            onMouseLeave={handleMouseLeave}
          >
            <pre
              css={css`
                font-size: 18px;
              `}
            >
              <span
                style={{
                  fontFamily:
                    'Consolas, Courier New, monospace',
                }}
              >
                {hoveredLine === codeLineNumber && (                 
                  <button
                    onClick={() =>
                      handleLineClick(codeLineNumber)
                    }
                    style={{ position: 'absolute' }}
                  >
                    <img src={plusmark} alt="plusmark" className="plusmark" data-comment-id={codeLineNumber} />
                  </button>
                )}
                {'    '}
                {codeLineNumber+1} {'  '}
                <span
                  dangerouslySetInnerHTML={{
                    __html: hljs.highlightAuto(line).value,
                  }}
                />
                <br />

                {/* 한줄리뷰 등록 */}
                {clickedLineId === codeLineNumber &&isBoxVisible && (
                    <div
                      style={{
                        width: '830px',
                        height: 'auto',
                        margin: '0 35px',
                        background: '#CCDEF2',
                        color: '#000000',
                      }}
                    >
                      <div
                        style={{
                          display: 'flex',
                          margin: '5px',
                        }}
                      >
                        <input
                          type="text"
                          placeholder={`${codeLineNumber+1}번째 줄 코드 리뷰를 입력하세요`}
                          value={reviews[codeLineNumber] || ''}
                          onChange={(e) =>
                            handleReviewChange(
                              codeLineNumber,
                              e.target.value
                            )
                          }
                          style={{
                            width: '90%',
                            border: 'none',
                            background: 'transparent',
                            outline: 'none',
                            padding: '5px 20px',
                          }}
                        />

                        <button
                          type="button"
                          onClick={handleSaveReviews}
                          css={css`
                            width: 75px;
                            height: 28px;
                            border-radius: 15px;
                            align-items: center;
                            background-color: #f0f0f0;
                            margin: 6px 0;
                          `}
                        >
                          등록
                        </button>
                      </div>

                      <div
                        css={css`
                          padding: 0px 20px;
                        `}
                      >
                        {/* 한줄리뷰 전체보기 */}
                        {showAllReviews && (
                          <div>
                            {codeData.reviews
                              .filter(
                                (review) =>
                                  review.codeLineNumber ===
                                    codeLineNumber+1
                              )
                              .sort((a, b) => a.id - b.id)
                              .map((review) => (
                                <div key={review.id}>
                                  <div
                                    css={css`
                                      display: flex;
                                      align-items: center;
                                      font-family: Noto Sans KR;
                                      font-size: 16px;
                                    `}
                                  >
                                    <div
                                      css={css`
                                        font-size: 14px;
                                        color: #545454;
                                      `}
                                    >
                                      {' '}
                                      @{
                                        review.nickname
                                      }{' '}
                                    </div>

                                    <div className="ReivewText">
                                      {review.editing ? (
                                        <input
                                          type="text"
                                          value={editValue}
                                          onChange={(e) =>
                                            setEditValue(
                                              e.target.value
                                            )
                                          }
                                          onBlur={() =>
                                            handleEditReview(
                                              review.id,
                                              editValue
                                            )
                                          }
                                        />
                                      ) : (
                                        <div>
                                          {review.content}
                                        </div>
                                      )}
                                    </div>

                                    <div
                                      css={css`
                                        margin-left: 20px;
                                        gap: 10px;
                                        display: flex;
                                        align-items: center;
                                      `}
                                    >
                                      <button
                                        className="EditBtn"
                                        css={css`
                                          font-size: 14px;
                                          color: ${theme
                                            .colors.light1};
                                        `}
                                        onClick={() => {
                                          if (
                                            review.editing
                                          ) {
                                            // 편집 중일 때
                                            handleEditReview(
                                              review.id,
                                              editValue
                                            );
                                            toggleEditReview(
                                              review.id
                                            );
                                          } else {
                                            // 편집 모드로 전환
                                            setEditValue(
                                              review.content
                                            );
                                            toggleEditReview(
                                              review.id
                                            );
                                          }
                                        }}
                                      >
                                        {review.editing
                                          ? '수정 완료'
                                          : '수정'}
                                      </button>

                                      <div
                                        css={css`
                                          border-right: 1px
                                            solid #959595;
                                          height: 15px;
                                        `}
                                      ></div>

                                      <button
                                        className="deleteBtn"
                                        css={css`
                                          font-size: 14px;
                                          color: ${theme
                                            .colors.light1};
                                        `}
                                        onClick={() => {
                                          setReviewIdToDelete(
                                            review.id
                                          );
                                          handleDeleteReview(
                                            review.id
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

                        {/* 한줄 미리보기 */}
                        {isReviewVisible && (
                          <div
                            css={css`
                              display: flex;
                              align-items: center;
                              padding: 0 0 4px;
                              font-family: Noto Sans KR;
                              font-size: 16px;
                            `}
                          >
                          {codeData.reviews
                            .filter(
                              (review) =>
                                review.codeLineNumber ===
                                  clickedLineId + 1
                            )
                            .sort((a, b) => a.id - b.id)
                            .slice(0, 1) // 첫 번째 리뷰만 선택
                            .map((filteredReview) => (
                              <div
                                css={css`
                                  font-size: 14px;
                                  color: #545454;
                                `}
                              >
                                {' '}
                                @{filteredReview.nickname}{' '}
                              </div>
                            ))}
                          
                            {codeData.reviews
                            .filter(
                              (review) =>
                                review.codeLineNumber ===
                                  clickedLineId + 1
                            )
                            .sort((a, b) => a.id - b.id)
                            .slice(0, 1) // 첫 번째 리뷰만 선택
                            .map((filteredReview) => (
                              <div>{filteredReview.content}</div>
                            ))}
                          </div>  
                        )} 
                        
                        {/* 더보기 버튼 */}
                        {codeData.reviews.filter(
                          (review) =>
                            review.codeLineNumber ===
                            codeLineNumber+1
                        ).length > 0 && (
                          <div
                            css={css`
                              display: flex;
                              align-items: center;
                              font-family: Noto Sans KR;
                              font-size: 16px;
                            `}
                          >
                            <button
                              onClick={handleToggleReviews}
                              css={css`
                                font-size: 14px;
                                color: #959595;
                                padding: 4px 5px 10px;
                              `}
                            >
                              {showAllReviews
                                ? '접기'
                                : '더보기...'}
                            </button>
                          </div>
                        )}

                      </div>
                    </div>
                  )}
              </span>
            </pre>
          </div>
        )
      )}
    </div>
  );
};