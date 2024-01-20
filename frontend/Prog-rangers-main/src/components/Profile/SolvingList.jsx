import React, { useState, useEffect } from 'react';
import { css } from '@emotion/react';
import sharemark from '../../assets/icons/share-mark.svg';
import {
  fontSize16,
  fontSizewhite16,
  fontSize18,
  fontSizedark20,
  boxStyle,
} from '../../pages/Profile/ProfileStyle';
import { tags } from '../MySolution/tagsform';
import { CodeWindow } from './CodeWindow';
import { useNavigate } from 'react-router-dom';
import { ProfileContentMock } from '../SolutionDetail/solutionTabStyle';

export const SolvingList = ({ list }) => {
  const [updatedList, setUpdatedList] = useState(null);

  const navigate = useNavigate();
  const onClickSols = (solutionId) => {
    navigate(`/solutions/${solutionId}`);
  };
  const onClickScrape = (solutionId) => {
    navigate(`/solutions/${solutionId}/detail/scrap`);
  };

  useEffect(() => {
    console.log(list);
    setUpdatedList(list); 
  }, [list]);

  return (
    <>
    {updatedList && (
      <div>
      {updatedList.map((item) => (
        <div
          key={item.solution.id}
          css={css`
            width: 835px;
            height: auto;
            margin-top: 50px;
          `}
        >
          <div
            css={css`
            width: 835px;
            margin-top: 30px;
            `}
          >
            <div
              css={css`
                ${fontSizedark20}
                cursor: pointer;
              `}
              onClick={() => onClickSols(item.solution.id)}
            >
              {item.problemTitle}
            </div>

            <div
              css={css`
                ${fontSize16}
                margin-top: 15px;
                cursor: pointer;
              `}
              onClick={() => onClickSols(item.solution.id)}
            >
              {item.solution.title}
            </div>

            <div
              css={css`
                ${boxStyle}
                margin-top: 15px;
              `}
            >
              <div
                classname="tags"
                css={css`
                  display: flex;
                `}
              >
                {item.solution.language && (
                  <div
                    key={item.solution.language}
                    css={css`
                      ${tags}
                    `}
                  >
                    {item.solution.language}
                  </div>
                )}
                {item.solution.algorithm && (
                  <div
                    key={item.solution.algorithm}
                    css={css`
                      ${tags}
                    `}
                  >
                    {item.solution.algorithm}
                  </div>
                )}
                {item.solution.dataStructure && (
                  <div
                    key={item.solution.dataStructure}
                    css={css`
                      ${tags}
                    `}
                  >
                    {item.solution.dataStructure}
                  </div>
                )}
              </div>
            </div>

            <button
              onClick={() => onClickScrape(item.solution.id)}
              css={css`
                width: 30px;
                height: 30px;
                float: right;
                padding-top: 4px;
                margin-top: 15px;
              `}
            >
              <img src={sharemark} alt="share_mark" />
            </button>

            <div
              css={css`
                ${boxStyle}
                ${fontSizewhite16}
                padding: 8px 20px;
                border-radius: 20px;
                float: right;
                margin: 15px 30px;
                background-color: ${item.solution.ojName ===
                '프로그래머스'
                  ? '#6AB4AC'
                  : '#3578BF'};
              `}
            >
              {item.solution.ojName}
            </div>

            <div
              css={css`
                width: 835px;
                margin-top: 30px;
                border: 1px solid #f0f0f0;
              `}
            ></div>

            <div
              css={css`
                ${fontSize18}
                margin: 30px 10px;
              `}
            >
              {item.solution.description
                .split('\n')
                .map((paragraph, index) =>
                  paragraph.trim() ? (
                    <p key={index}>{paragraph}</p>
                  ) : (
                    <br key={index} />
                  )
                )}
            </div>

            <div
              css={css`
                ${ProfileContentMock}
              `}
            >
              <div
                css={css`
                  padding: 20px 40px 20px;
                  font-size: 16px;
                  font-weight: 700;
                `}
              >
                {item.problemTitle}
              </div>

              <div
                css={css`
                  width: 100%;
                  border-bottom: 1px solid #1a2333;
                `}
              ></div>

              <div
                css={css`
                  margin-top: 3px;
                  width: 100%;
                  border-bottom: 1px solid #1a2333;
                `}
              ></div>

              <CodeWindow />
            </div>

            <div
              css={css`
                margin-top: 50px;
                width: 100%;
                border-bottom: 1px solid #959595;
              `}
            ></div>
          </div>
        </div>
      ))}
      </div>
    )}
    </>
  );
};
