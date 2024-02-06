import { tags } from "../Question/tagsform";
import { css } from "@emotion/react";
import Delete from '../../assets/icons/solution-tag-delete.svg';
import { useEffect, useState } from "react";
import { useAtomValue } from "jotai";
import { targetAtom, targetScope, nameAtom, nameScope } from "../../pages/BoardPage/AddSolution";

//title로 넘어오는값은 algorithm, datastructure

export const TagAction = ({title}) => {
  const target = useAtomValue(targetAtom, targetScope);
  const name = useAtomValue(nameAtom, nameScope);

  const [ algo, setAlgo ] = useState([]);
  const [ data, setIsData ] = useState([]);
  useEffect(() => {
    if(title === 'algorithm'){
      setAlgo({...algo, target});
    }else{
      setIsData({...data, target});
    }
  }, [])

  return(
    <>
      <div css={css`${tags}`} id='tag'>
        {/* {isData} */}
        <img  css={css`margin-left: 15px; &:hover{ cursor: pointer; }`} src={Delete} />
      </div>    
    </>
  );
};