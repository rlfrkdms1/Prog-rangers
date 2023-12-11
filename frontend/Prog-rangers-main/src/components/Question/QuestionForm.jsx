import { css } from "@emotion/react";
import { ojNameTag, tags } from "./tagsform";
import { useNavigate } from 'react-router-dom';
import forTags from './fortagsort.json';

export const QuestionForm = ({data}) => {
  const navigate = useNavigate();
  const onClickSols = (index) => {
    navigate(`/problems/${index}`);
  }

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
          onClick={(e) => onClickSols(item.id)}
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
          `}>
            {item.title}
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
              {item.tags.map((index) => (
                getRightName(index) && (
                  <div key={index} css={css`${tags}`}> 
                    {getRightName(index)}
                  </div>
                )
              ))}
            </div>

            <div css={css`
              ${ojNameTag}
              background-color: ${item.ojName === "프로그래머스" ? "#6AB4AC" : "#3578BF"};
              color: white;
            `}> 
              {item.ojName}
            </div>
          </div>
        </div>
      ))}
    </>
  );
};