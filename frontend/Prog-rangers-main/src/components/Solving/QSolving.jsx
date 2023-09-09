import { css } from "@emotion/react";
import solvings from '../../db/solving.json';
import { solveStyle, listStyle, ojNameTag, tags } from "./QSolvingStyle";

export const QSolving = ({data}) => {
  
  return(
    <>
      {data.map((item,index) => (
        <div key={index} css={css`display: inline-block; width: 100%; height: 138px; border-bottom: 1px solid #D9D9D9`}>
          <div css={css`
            height: 29px; 
            width: 100%; 
            margin-top: 30px; 
            padding-left: 8px; 
            font-weight: bold;
            font-size: 20px;
            color: #303030;`
          }            
          >
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
                <div key={index} css={css`${tags}`}>{index}</div>
              ))}
            </div>
          </div>
        </div>
      ))}
    </>
  );
};