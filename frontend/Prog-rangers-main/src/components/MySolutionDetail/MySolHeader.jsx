import { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import { css } from '@emotion/react';
import { theme } from '../Header/theme';

import { BiSolidLockAlt } from 'react-icons/bi';
import { BiSolidLockOpenAlt } from 'react-icons/bi';

import star1 from '../../assets/icons/star/star1.svg';
import star2 from '../../assets/icons/star/star2.svg';
import star3 from '../../assets/icons/star/star3.svg';
import star4 from '../../assets/icons/star/star4.svg';
import star5 from '../../assets/icons/star/star5.svg';

import {
  MyHeaderLayout,
  colFlex,
  dot,
  editStyle,
  deleteStyle,
  rowFlex,
  fontStyle,
} from '../SolutionDetail/headerStyle';

const starImages = {
  1: star1,
  2: star2,
  3: star3,
  4: star4,
  5: star5,
}

const token = localStorage.getItem('token');

export const MySolHeader = () => {
  // 풀이 API
  const { solutionId } = useParams();
  const [problem, setProblem] = useState(null);
  const [solution, setSolution] = useState(null);

  // 수정 삭제 버튼
  const [showEditButtons, setShowEditButtons] =
    useState(false);
  const [isOpen, setIsOpen] = useState(false);

  const handleToggle = () => {
    setIsOpen(!isOpen);
    setShowEditButtons(!showEditButtons);
  };

  // 풀이 수정 페이지 이동
  const navigate = useNavigate();

  const onClickSols = () => {
    navigate(`/mySolution/${solutionId}/editsolution`);
  };

  useEffect(() => {
    const apiUrl = `http://13.125.13.131:8080/api/v1/solutions/${solutionId}/mine`;

    axios
      .get(apiUrl, {
        method: 'GET',
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        setProblem(response.data.problem);
        setSolution(response.data.solution);
      })
      .catch((error) => {
        console.error('API 요청 오류:', error);
        alert('접근 권한이 없습니다.');
        navigate(-1);
      });
  }, [solutionId]);

  // 풀이 삭제
  const deleteSolution = (solutionId) => {
    const apiUrl = `http://13.125.13.131:8080/api/v1/solutions/${solutionId}`;

    axios
      .delete(apiUrl, {
        method: 'DELETE',
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((response) => {
        console.log('풀이가 성공적으로 삭제되었습니다.');
        alert('풀이가 성공적으로 삭제되었습니다.');
        window.location.href = `http://localhost:3000/mySolution`;
      })
      .catch((error) => {
        console.error('풀이 삭제 중 오류 발생:', error);
      });
  };

  // 풀이 삭제 버튼을 클릭할 때 풀이를 삭제하는 함수 호출
  const handleDeleteSolution = (solutionId) => {
    if (window.confirm('풀이를 삭제하시겠습니까?')) {
      deleteSolution(solutionId);
    }
  };

  return (
    <>
      {problem && solution && (
        <div className="headerAll" css={MyHeaderLayout}>
          <div className="headerLeft">
            <div
              className="problemTitleArea rowFlex"
              css={rowFlex}
            >
              <div
                className="problemTitle"
                css={css`
                  font-size: 20px;
                  font-weight: bold;
                  color: ${theme.colors.dark1};
                  margin-right: 10px;
                `}
              >
                {problem.title}
              </div>
              <div className="icon">
                {solution.public ? <BiSolidLockOpenAlt size="18" color="#D9D9D9" /> : <BiSolidLockAlt size="18" color="#D9D9D9" />}
              </div>
            </div>
            <div
              className="solutionTitle"
              css={css`
                padding: 15px 0;
              `}
            >
              <span
                css={css`
                  padding-right: 20px;
                  color: ${theme.colors.dark1};
                `}
              >
                {solution.title}
              </span>
              <span>
                {starImages[solution.level] && (
                  <img src={starImages[solution.level]} alt={`${solution.level}`} />
                 )}
              </span>
            </div>
            <div css={css`display: flex; gap: 5px;`}>
              <div
                className="AlgoOptions"
                css={css`
                  padding: 8px 20px;
                  background-color: ${theme.colors.light3};
                  border-radius: 20px;
                  text-align: center;
                  color: ${theme.colors.dark1};
                  ${solution.algorithm === null
                    ? 'display: none;'
                    : ''}
                `}
              >
                {solution.algorithm}
              </div>

              <div
                className="DataStrOptions"
                css={css`
                  width: 89px;
                  height: 36px;
                  background-color: ${theme.colors.light3};
                  border-radius: 20px;
                  text-align: center;
                  line-height: 36px;
                  color: ${theme.colors.dark1};
                  ${solution.dataStructure === null
                    ? 'display: none;'
                    : ''}
                `}
              >
                {solution.dataStructure}
              </div>
            </div>
          </div>
          <div css={colFlex}>
            <div
              className="headerRight rowFlex"
              css={rowFlex}
            >
              <div
                className="siteName"
                css={css`
                  background-color: ${problem.ojName ===
                  '프로그래머스'
                    ? '#6AB4AC'
                    : '#3578BF'};
                  box-sizing: border-box;
                  padding: 8px 20px;
                  border-radius: 20px;
                  color: ${theme.colors.white};
                `}
              >
                {problem.ojName}
              </div>
              <button
                onClick={handleToggle}
                className="dropMenu"
                css={colFlex}
              >
                <span css={dot}></span>
                <span css={dot}></span>
                <span css={dot}></span>
              </button>
            </div>

            <div
              className="edit-delete-buttons"
              css={css`
                margin-left: 30px;
                margin-top: 10px;
                ${fontStyle}
              `}
            >
              <button
                css={editStyle(isOpen)}
                onClick={(e) => onClickSols(solution.id)}
              >
                수정하기
              </button>
              <button
                css={deleteStyle(isOpen)}
                onClick={(e) =>
                  handleDeleteSolution(solution.id)
                }
              >
                삭제하기
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
};
