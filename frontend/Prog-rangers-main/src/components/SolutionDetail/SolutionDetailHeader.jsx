import { useEffect, useState } from 'react';
import { css } from '@emotion/react';
import { theme } from '../Header/theme';

import { BiSolidLockAlt } from 'react-icons/bi';
import {
  HeaderLayout,
  colFlex,
  dot,
  rowFlex,
} from './headerStyle';

export const SolutionDetailHeader = () => {
  // const [data, setData] = useState(null);
  const [problem, setProblem] = useState(null);
  const [solution, setSolution] = useState(null);

  useEffect(() => {
    fetch('http://13.124.131.171:8080/api/v1/solutions/1')
      .then((res) => res.json())
      .then((res) => {
        setProblem(res.problem || null);
        setSolution(res.solution || null);
        // setData(res.data || null);
      });
  }, []);

  return (
    <>
      {problem && solution && (
        <div className="headerAll" css={HeaderLayout}>
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

                  padding-right: 10px;
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
                padding: 15px 0 15px 5px;
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
          <div
            className="headerRight rowFlex"
            css={rowFlex}
          >
            <div
              className="siteName"
              css={css`
                background-color: ${theme.colors
                  .programmers};
                box-sizing: border-box;
                padding: 8px 20px;
                border-radius: 20px;
                color: ${theme.colors.dark1};
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
      )}
    </>
  );
};
