import { css } from "@emotion/react";
import { useEffect, useState } from "react";
import Public from '../../assets/icons/solution-public.svg';
import Private from '../../assets/icons/solution-private.svg';
import Unfilled from '../../assets/icons/solution-unfilled-star.svg';
import Filled from '../../assets/icons/solution-filled-star.svg';
import { 
  StyledInput, 
  TitleBox, 
  Stars, 
  DetailBox, 
  DetailInput
} from "./inputBox"; 
import { FilterBar } from "../FilterBar";
import sort from '../../db/autocomplete.json';
import { useAtomValue } from "jotai";
import { targetAtom, targetScope, nameAtom, nameScope } from "../../pages/BoardPage/AddSolution";
import { TagAction } from "./TagAction";

export const AddMySolution = () => {
  const [ isPublic, setIsPublic ] = useState(true);
  const [ clickedStar, setClickedStar ] = useState([false, false, false, false, false]);
  const target = useAtomValue( targetAtom, targetScope);
  const name = useAtomValue( nameAtom, nameScope );
  const array = [0, 1, 2, 3, 4];

  const TagDisplay1 = () => {
    if(name === "algorithm" && target){
      localStorage.setItem('algorithm', target);
    }
    if(!localStorage.getItem('algorithm'))
      return <div css={css`height: 35px;`}/>
    return <TagAction data={localStorage.getItem('algorithm')} />
  }
  const TagDisplay2 = () => {
    if(name === "datastructure" && target){
      localStorage.setItem('datastructure', target);
    }
    if(!localStorage.getItem('datastructure'))
      return <div css={css`height: 35px;`}/>
    
      return <TagAction data={localStorage.getItem('datastructure')} />

  }
  const publicHandler = () => {
    setIsPublic(!isPublic);
  };
  const fillHandler = (index) => {
    let clickStates = [...clickedStar];
    for(let i=0; i<5; i++){
      clickStates[i] = i <= index ? true : false;
    }
    setClickedStar(clickStates);
  }
  const sendStars = () => {
    let stars = clickedStar.filter(Boolean).length;
  };
  useEffect(() => {
    sendStars();
  }, [clickedStar]);

  return(
    <div>
      <div css={css`
        margin: 100px 30px 0 40px;
        width: 740px;
        display: flex;
        flex-direction: column;
        `}
      >
        <div className="header" css={css`display: flex; flex-direction: row; justify-content: space-between; align-items: flex-end;`}>
          <div css={css`${TitleBox}`}>풀이 제목</div>
          <div css={css `margin-right: 10px; &:hover{cursor: pointer;} `} onClick={publicHandler}>
            <span css={css`
              font-size: 16px;
              color: #959595;
              font-weight: 400;
              margin-right: 10px;
              width: 30px;
            `}>
              { isPublic ? "공개" : "비공개" } 
            </span>
            { isPublic ? <img src={Public}/> : <img src={Private}/>}
          </div>
        </div>
        <input placeholder="풀이 제목을 입력해주세요" css={css`${StyledInput}`} />

        <div css={css`${TitleBox} margin-top: 50px;`}>문제링크</div>
        <input placeholder="문제 링크를 입력해주세요" css={css`${StyledInput}`}/>

        <div placeholder="middle" css={css`display: flex; flex-direction: row; margin-top: 20px; align-items: center;`}>
          <div css={css`${TitleBox}`}>난이도</div>
          <div css={css`${Stars} margin-left: 30px;`}>
            {array.map((item) => (
              <img
                key={item}
                src={clickedStar[item] ? Filled : Unfilled}
                css={css`&:hover{cursor: pointer;}`}
                onClick={() => fillHandler(item)}
              />
            ))}
          </div>
          <div css={css`
            margin-left: 50px; text-decoration: underline; color: #959595; font-size: 14px; font-weight: 400;
          `}>스스로 느끼는 문제의 난이도를 입력해주세요</div>
        </div>
        
        <div css={css`${TitleBox} margin-top: 50px;`}>카테고리 검색</div>
        <div css={css`margin-top: 20px; display: flex; flex-direction: row; margin-left: 20px; width: 720px; justify-content: space-between;`}>
          <FilterBar title="algorithm" options={sort.ALGORITHM} width="350px" secondWidth="270px" />
          <FilterBar title="datastructure" options={sort.DATASTRUCTURE} width="350px" secondWidth="270px" />
        </div>
        <div css={css`margin: 20px 0 0 20px; display: flex; flex-direction: row;`}>
          {TagDisplay1()}
          {TagDisplay2()}
        </div>

        <div css={css`${TitleBox} margin-top: 50px;`}>풀이 설명</div>
        <div css={css`${DetailBox} height: 250px; width: 100%;`}>
          <textarea css={css`${DetailInput}`} />
        </div>

        <div css={css`${TitleBox} margin-top: 50px;`}>코드</div>
        <div css={css`${DetailBox} height: 250px; width: 100%;`}>
          <textarea css={css`${DetailInput}`} />
        </div>
      </div>
    </div>
  );
};