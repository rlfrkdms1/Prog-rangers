import React from "react";
import { css } from "@emotion/react";
import { ojNameTag, tags } from "./tagsform";
import { useNavigate } from 'react-router-dom';
import forTags from './fortagsort.json';
import star1 from '../../assets/icons/star/star1.svg';
import star2 from '../../assets/icons/star/star2.svg';
import star3 from '../../assets/icons/star/star3.svg';
import star4 from '../../assets/icons/star/star4.svg';
import star5 from '../../assets/icons/star/star5.svg';

import { BiSolidLockAlt } from 'react-icons/bi';
import { BiSolidLockOpenAlt } from 'react-icons/bi';

export const MySolutionForm = ({data}) => {

  const navigate = useNavigate();
  const onClickSols = (solutionId) => {
    navigate(`/mySolution/${solutionId}`);
  };

  const getRightName = (targetValue) => {
    const condition = (element) => element.value === targetValue;
    const foundIndex = forTags.findIndex(condition) ;
    const foundElement = forTags[foundIndex];
    return foundElement?.name;
  }

  const starImages = {
    1: star1,
    2: star2,
    3: star3,
    4: star4,
    5: star5,
  }

  return(
    <>
      {data.map((item) => (
        <div key={item.solution.id} css={css`display: inline-block; width: 100%; height: 175px; border-bottom: 1px solid #D9D9D9`}>
          <div css={css`
          height: 29px;
          margin-top: 30px;
          padding-left: 8px;
          display: flex;
          justify-content: space-between;
          `}>
            <div css={css`
            display: flex;
            align-items: flex-end;
            gap: 10px;
            `}>
              <div
              onClick={(e) => onClickSols(item.solution.id)}
              css={css`
                font-weight: bold;
                font-size: 20px;
                color: #303030;
                &:hover {
                  cursor: pointer;
                  text-decoration: underline;
                }
              `}
              >
                {item.problem.title}
              </div>

              <div css={css`
              font-size: 16px;
              color: #959595;
              `}>
                {item.solution.title}
              </div>

              <div className="icon">
                {item.solution.public ? <BiSolidLockOpenAlt size="18" color="#D9D9D9" /> : <BiSolidLockAlt size="18" color="#D9D9D9" />}
              </div>
            </div>
              
            <div css={css`
              font-size: 12px;
              color: #959595;
              display: flex;
              align-items: center;
               padding: 10px 10px;
              float: right;
              ${item.solution.scrapped ? 'display: flex;' : 'display: none;'}
              `}>
                <span css={css`margin: 5px`}>
                스크랩한 풀이
                </span>
                <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 14 14" fill="none">
                  <path d="M11.0821 1.82069L11.2527 1.65H11.0113H8.75C8.32124 1.65 7.975 1.30376 7.975 0.875C7.975 0.446244 8.32124 0.1 8.75 0.1H13.125C13.5538 0.1 13.9 0.446244 13.9 0.875V5.25C13.9 5.67876 13.5538 6.025 13.125 6.025C12.6962 6.025 12.35 5.67876 12.35 5.25V2.98867V2.74717L12.1793 2.91798L6.67499 8.42499C6.37225 8.72774 5.88049 8.72774 5.57774 8.42499C5.275 8.12225 5.275 7.63049 5.57774 7.32774L5.57776 7.32772L11.0821 1.82069ZM0.1 3.0625C0.1 1.90913 1.03413 0.975 2.1875 0.975H5.25C5.67876 0.975 6.025 1.32124 6.025 1.75C6.025 2.17876 5.67876 2.525 5.25 2.525H2.1875C1.89165 2.525 1.65 2.76665 1.65 3.0625V11.8125C1.65 12.1084 1.89165 12.35 2.1875 12.35H10.9375C11.2334 12.35 11.475 12.1084 11.475 11.8125V8.75C11.475 8.32124 11.8212 7.975 12.25 7.975C12.6788 7.975 13.025 8.32124 13.025 8.75V11.8125C13.025 12.9659 12.0909 13.9 10.9375 13.9H2.1875C1.03413 13.9 0.1 12.9659 0.1 11.8125V3.0625Z" fill="#959595" stroke="white" stroke-width="0.2"/>
                </svg>
            </div>
          </div>
      

          <div css={css`
            width: 100%; 
            height: 39px; 
            margin-top: 10px;
          `}>
            <div css={css`display: flex; justify-content: space-between;`}>
            <div classname="tags" css={css`display: flex;`}>
            {item.solution.algorithm && (<div key={item.solution.algorithm} css={css`${tags}`}>{item.solution.algorithm}</div>)}
            {item.solution.dataStructure && (<div key={item.solution.dataStructure} css={css`${tags}`}>{item.solution.dataStructure}</div>)}
            {item.solution.language && (<div key={item.solution.language} css={css`${tags}`}>{item.solution.language}</div>)}
            </div>
            
            
            <div css={css`
              ${ojNameTag}
              float: right;
              background-color: ${item.problem.ojName === "프로그래머스" ? "#6AB4AC" : "#3578BF"};
              color: white;
            `}> 
              {item.problem.ojName}
            </div>
          </div>
        </div>
          
          <div css={css`margin-top: 10px;`}>
            {starImages[item.solution.level] && (
            <img src={starImages[item.solution.level]} alt={`${item.solution.level}`} />
            )}
          </div>
        </div>
      ))}
    </>
  );
};