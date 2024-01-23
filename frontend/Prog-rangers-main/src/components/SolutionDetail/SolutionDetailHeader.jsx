import { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import { css } from '@emotion/react';
import { theme } from '../Header/theme';
import { BiSolidLockAlt } from 'react-icons/bi';
import { BiSolidLockOpenAlt } from 'react-icons/bi';
import {
  HeaderLayout,
  colFlex,
  dot,
  rowFlex,
} from './headerStyle';

export const SolutionDetailHeader = () => {
  const navigate = useNavigate();
  const onClickName = (nickname) => {
    navigate(`/profile/${nickname}`);
  };

  const { solutionId } = useParams();
  const [problem, setProblem] = useState({});
  const [solution, setSolution] = useState({});

  useEffect(() => {
    const apiUrl = `http://13.125.13.131:8080/api/v1/solutions/${solutionId}`;

    axios
      .get(apiUrl)
      .then((response) => {
        setProblem(response.data.problem);
        setSolution(response.data.solution);
      })
      .catch((error) => {
        console.error('API 요청 오류:', error);
        alert('비공개 풀이를 열람할 수 없습니다.');
        navigate(-1);
      });
  }, []);

  return (
    <>
      {problem && solution && (
        <div className="headerAll" css={HeaderLayout}>
          <div className="headerLeft">
            <div
              className="problemTitleArea rowFlex"
              css={css`
                ${rowFlex}
                gap : 10px;
              `}
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
                {solution.public ? <BiSolidLockOpenAlt size="18" color="#D9D9D9" /> : <BiSolidLockAlt size="18" color="#D9D9D9" />}
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
                  cursor: pointer;
                `}
                onClick={() =>
                  onClickName(solution.nickname)
                }
              >
                {solution.nickname}
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

              <div
                className="LanguageOptions"
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
                {solution.language}
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
              <div className="dropMenu" css={colFlex}>
                <span css={dot}></span>
                <span css={dot}></span>
                <span css={dot}></span>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
};
