import { tags } from "../Question/tagsform";
import { css } from "@emotion/react";
import Delete from '../../assets/icons/solution-tag-delete.svg';
import { useEffect, useState } from "react";
import { useAtomValue } from "jotai";
import { targetAtom, targetScope, nameAtom, nameScope } from "../../pages/BoardPage/AddSolution";

//title로 넘어오는값은 algorithm, datastructure

export const TagAction = ({title}) => {
  // const [ isData, setIsData ] = useState(JSON.parse(localStorage.getItem(title)) || '');
  const target = useAtomValue(targetAtom, targetScope);
  const name = useAtomValue(nameAtom, nameScope);
  // console.log(target);
  // console.log(name);
  // useEffect(() => {
  //   const handleChange = (e) => {
  //     if(e.key === title){
  //       setIsData(e.newValue);
  //     }
  //   };
  //   window.addEventListener('storage', handleChange);
  //   console.log(isData);
  //   return() => {
  //     window.removeEventListener('storage', handleChange);
  //   }
  // }, []);
  // useEffect(() => {
  //   if(title == 'algorithm'){
  //     console.log('a');
  //   }
  // })
  // console.log(target);
  // console.log(name);
  const [ algo, setAlgo ] = useState([]);
  const [ data, setIsData ] = useState([]);
  useEffect(() => {
    if(title == 'algorithm'){
      setAlgo({...algo, target});
    }else{
      setIsData({...data, target});
    }
  }, [])

  return(
    <>
      {/* { isData!==null ?      
        <div css={css`${tags}`} id='tag'>
          {isData}
          <img  css={css`margin-left: 15px; &:hover{ cursor: pointer; }`} src={Delete} />
        </div>
      : ""
      } */}
        <div css={css`${tags}`} id='tag'>
          {/* {isData} */}
          <img  css={css`margin-left: 15px; &:hover{ cursor: pointer; }`} src={Delete} />
        </div>
    </>
  );
};