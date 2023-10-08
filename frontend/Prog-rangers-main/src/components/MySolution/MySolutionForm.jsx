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

export const MySolutionForm = ({data}) => {
  const navigate = useNavigate();
  const onClickSols = (solutionId) => {
    navigate(`/solution?${solutionId}`);
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
      {data.map((item,index) => (
        <div key={index} css={css`display: inline-block; width: 100%; height: 175px; border-bottom: 1px solid #D9D9D9`}>
          <div css={css`
            display: flex;
            height: 29px;
            margin-top: 30px; 
            padding-left: 8px; 
            align-items: flex-end;
            gap: 10px;
          `}>
            <div
            onClick={(e) => onClickSols(item.solution.solutionId)}
            css={css`
              font-weight: bold;
              font-size: 20px;
              color: #303030;
              &:hover{
                cursor: pointer;
                text-decoration: underline;
              }
            `}>
              {item.problem.title}
            </div>

            <div css={css`
              fontsize: 16px;
              color: #959595
            `}>
              {item.solution.title}
            </div>
          </div>

          <div css={css`
            width: 100%; 
            height: 39px; 
            margin-top: 10px;
            display: flex;
            flex-direction: row;
            justify-content: space-between;
          `}>
            <div css={css`display: flex; justify-content: space-between;`}>
            <div classname="tags" css={css`display: flex;`}>
              <span css={css`${tags}`}>
                {item.solution.algorithm}
              </span>

              <span css={css`${tags}`}>
                {item.solution.dataStructure}
              </span>
              
              <span css={css`${tags}`}>
                {item.solution.language}
              </span>
            </div>
            
            
            <div css={css`
              ${ojNameTag}
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