import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { css } from '@emotion/react';
import { theme } from '../Header/theme';

import { BiSolidLockAlt } from 'react-icons/bi';
import {
  MyHeaderLayout,
  colFlex,
  dot,
  editStyle,
  deleteStyle,
  rowFlex,
  fontStyle
} from './headerStyle';

export const MySolHeader = () => {
  // 풀이 API
  const [problem, setProblem] = useState(null);
  const [solution, setSolution] = useState(null);

  // 수정 삭제 버튼
  const [showEditButtons, setShowEditButtons] = useState(false);
  const [isOpen, setIsOpen] = useState(false);

  const handleToggle = () => {
    setIsOpen(!isOpen);
    setShowEditButtons(!showEditButtons); 
  };

  const handleSelect = (path) => {
    setIsOpen(false);
  };

  // 풀이 수정 페이지 이동
  const navigate = useNavigate();

  const onClickSols = (solutionId) => {
    navigate(`/EditSolutions/${solutionId}`);
  };

  useEffect(() => {
    fetch('http://13.124.131.171:8080/api/v1/solutions/1')
      .then((res) => res.json())
      .then((res) => {
        setProblem(res.problem || null);
        setSolution(res.solution || null);
      });
  }, []);

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

                `}
              >
                {problem.title}
              </div>
              <div className="icon">
                <BiSolidLockAlt size="18" color="#D9D9D9" />
              </div>
            </div>
            <div
              className="solutionTitle"
              css={css`
                padding: 15px 0 15px 0;
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
              <span
                css={css`
                  color: ${theme.colors.light1};
                `}
              >
                {solution.nickname}
              </span>
            </div>
            <div
              className="options"
              css={css`
                width: 89px;
                height: 36px;
                background-color: ${theme.colors.light3};
                border-radius: 20px;
                text-align: center;
                line-height: 36px;
                color: ${theme.colors.dark1};
              `}
            >
              {solution.algorithmName}
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
                background-color: ${problem.ojName === "프로그래머스" ? "#6AB4AC" : "#3578BF"};
                box-sizing: border-box;
                padding: 8px 20px;
                border-radius: 20px;
                color: ${theme.colors.white};
              `}
            >
              {problem.ojName}
            </div>
              <button onClick={handleToggle} className="dropMenu" css={colFlex}>
                <span css={dot}></span>
                <span css={dot}></span>
                <span css={dot}></span>
              </button>
          </div>
          
            <div className="edit-delete-buttons" 
              css={css`
                margin-left: 30px;
                margin-top: 10px;
                ${fontStyle}`}>
              <button css={editStyle(isOpen)} onClick={(e) => onClickSols(solution.id)}>수정하기</button>          
              <button css={deleteStyle(isOpen)} onClick={(e) => onClickSols(solution.id)}>삭제하기</button>
          </div>
        </div>
      </div>
    )}
  </>
  );
};
