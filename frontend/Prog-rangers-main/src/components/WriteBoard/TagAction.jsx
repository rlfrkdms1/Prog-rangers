import { tags } from "../Question/tagsform";
import { css } from "@emotion/react";
import Delete from '../../assets/icons/solution-tag-delete.svg';
import { useEffect, useState } from "react";

export const TagAction = ({data}) => {
  const [ isDeleted, setIsDeleted ] = useState(false);
  const deleteHandler = () => {
    setIsDeleted(true);    
  }
  
  return(
    <>
      {/* {isDeleted == false ? 
        <div css={css`${tags}`}>
          {data}
          <img onClick={deleteHandler} css={css`margin-left: 15px; &:hover{ cursor: pointer; }`} src={Delete} />
        </div>
        :
        ""
      } */}
      <div css={css`${tags}`}>
        {data}
        <img onClick={deleteHandler} css={css`margin-left: 15px; &:hover{ cursor: pointer; }`} src={Delete} />
      </div>

    </>
  );
};