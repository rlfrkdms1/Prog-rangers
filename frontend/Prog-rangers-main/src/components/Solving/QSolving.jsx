import { css } from "@emotion/react";
import { tags } from "./QSolvingStyle";
import sort from '../../db/autocomplete.json';
import forTags from './fortagsort.json';
import { useNavigate } from "react-router-dom";

export const QSolving = ({data}) => {
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
  
  return(
    <>
      {data.map((item,index) => (
        <div key={index} css={css`display: inline-block; width: 100%; height: 138px; border-bottom: 1px solid #D9D9D9`}>
          <div 
          onClick={(e) => onClickSols(item.solutionId)}
          css={css`
            height: 29px; 
            width: 100%; 
            margin-top: 30px; 
            padding-left: 8px; 
            font-weight: bold;
            font-size: 20px;
            color: #303030;
            &:hover{
              cursor: pointer;
              text-decoration: underline;
            }
            `
          }            
          >
            {item.solutionName}
          </div>
          <div css={css`
            width: 100%; 
            height: 39px; 
            margin-top: 10px;
            display: flex;
            flex-direction: row;
            justify-content: space-between;
          `}>
            <div css={css`display: flex; flex-direction: row;`}>
              <div key={index} css={css`${tags}`}>
                {getRightName(item.algorithm)}
              </div>
            </div>
          </div>
        </div>
      ))}
    </>
  );
};