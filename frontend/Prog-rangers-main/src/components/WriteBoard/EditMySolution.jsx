import { css } from "@emotion/react";
import { useEffect, useState } from "react";
import Public from '../../assets/icons/solution-public.svg';
import Private from '../../assets/icons/solution-private.svg';
import Unfilled from '../../assets/icons/solution-unfilled-star.svg';
import Filled from '../../assets/icons/solution-filled-star.svg';
import Delete from '../../assets/icons/solution-tag-delete.svg';
import { tags } from "../Question/tagsform";
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
import { targetAtom, targetScope, nameAtom, nameScope , valueAtom, valueScope} from "../../pages/BoardPage/AddSolution";
import { ButtonDiv, SubmitButton } from "../../pages/BoardPage/buttonDiv";
import { TagAction } from "./TagAction";
import axios from 'axios';
import { useNavigate } from "react-router-dom";

export const EditMySolution = ({postURL}) => {
  const [ isPublic, setIsPublic ] = useState(true);
  const [ clickedStar, setClickedStar ] = useState([false, false, false, false, false]);
  const token = localStorage.getItem('token');
  const target = useAtomValue(targetAtom, targetScope);
  const name = useAtomValue(nameAtom, nameScope);
  const value = useAtomValue(valueAtom, valueScope);
  const array = [0, 1, 2, 3, 4];
  const [ inputs, setInputs ] = useState({
    solution: '',
    link: '',
    description: '',
    code: '',
  });
  const [ algo, setAlgo ] = useState([]);
  const [ data, setData ] = useState([]);

  useEffect(() => {
    if(name == 'algorithm'){
      setAlgo({
        value: value,
        name: target,
      });
    }
    if(name == 'datastructure'){
      setData({
        value: value,
        name: target,
      });
    }
    TagDisplay1();
    TagDisplay2();
  }, [target]);

  const { solution, link, language, description, code } = inputs;

  const handleInput = (e) => {
    const { name, value } = e.target;
    setInputs({
      ...inputs,
      [name]: value,
    });
  };

  const TagDisplay1 = () => {
    const deleteHandler = () => {
      setAlgo([]);
    }
    return (
      <>
        {algo.length !== 0 ?
          <div css={css`${tags}`} >
            {algo.name}
            <img  onClick={deleteHandler} css={css`margin-left: 15px; &:hover{ cursor: pointer; }`} src={Delete} />
          </div>
          : ""
        }
      </>
    )
  }
  const TagDisplay2 = () => {
    const deleteHandler = () => {
      setData([]);
    }
    return (
      <>
        {data.length !== 0?
          <div css={css`${tags}`} >
            {data.name}
            <img  onClick={deleteHandler} css={css`margin-left: 15px; &:hover{ cursor: pointer; }`} src={Delete} />
          </div>
          : ""
        }
      </>
    )
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
  };
  const postWrite = async() => {
    let star = clickedStar.filter(Boolean).length;
    try{
      if(inputs.solution === ''){
        alert('제목을 입력해주세요.');
        return;
      }
      if(inputs.link === ''){
        alert('링크를 입력해주세요.');
        return;
      }
      if(star === 0){
        alert('난이도를 체크해주세요.');
        return;
      }
      if(algo === ''){
        alert('원하는 카테고리를 설정해주세요. 없으면 `알고리즘`으로 선택해주세요.');
        return;
      }
      if(data === ''){
        alert('원하는 카테고리를 설정해주세요. 없으면 `자료구조`로 선택해주세요.');
        return;
      }
     if(inputs.description === ''){
      alert('풀이설명을 기록해주세요.');
      return;
     }
     if(inputs.code === ''){
      alert('코드를 작성해주세요.');
      return;
     }
     const body={
      problemTitle: '몰라요',
      title: inputs.solution,
      problemLink: inputs.link,
      level: star.toString(),
      algorithm: algo.value,
      dataStructure: data.value,
      language: "JAVA",
      description: inputs.description,
      code: inputs.code,
      isPublic: isPublic.toString(),
     };     

     console.log('inputs.title:', inputs.title);
     console.log('inputs.link:', inputs.link);

     const response =  await axios
      .patch(`http://13.124.131.171:8080/api/v1/solutions/${id}`, body,{
        headers: { Authorization: `Bearer ${token}`}
      });
    if(response.status === 201){
      alert('질문이 등록되었습니다.');
      window.location.href = `http://localhost:3000/solutions/${id}`;
    }
    }
    catch(error){
      console.log(error);
    }    
  }

    // 작성취소 버튼
    const navigate = useNavigate();

    const handleGoBack = () => {
      navigate(-1); // 이전 페이지로 이동
    };

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
        <input placeholder="풀이 제목을 입력해주세요" css={css`${StyledInput}`} value={inputs.solution} name="solution" onChange={handleInput} readOnly/>

        <div css={css`${TitleBox} margin-top: 50px;`}>문제링크</div>
        <input placeholder="문제 링크를 입력해주세요" css={css`${StyledInput}`} value={inputs.link} name="link" onChange={handleInput} readOnly/>

        <div placeholder="middle" css={css`display: flex; flex-direction: row; margin-top: 20px; align-items: center;`}>
          <div css={css`${TitleBox}`}>난이도</div>
          <div css={css`${Stars} margin-left: 30px;`}>
            {array.map((item) => (
              <img
                key={item}
                src={clickedStar[item] ? Filled : Unfilled}
                css={css`&:hover{cursor: pointer;}`}
                value={inputs.level}
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

        <div css={css`${TitleBox} margin-top: 50px;`} >풀이 설명</div>
        <div css={css`${DetailBox} height: 250px; width: 100%;`}>
          <textarea css={css`${DetailInput}`} value={inputs.description} name="description" onChange={handleInput} readOnly/>
        </div>

        <div css={css`${TitleBox} margin-top: 50px;`}>코드</div>
        <div css={css`${DetailBox} height: 250px; width: 100%;`}>
          <textarea css={css`${DetailInput}`} value={inputs.code} name="code" onChange={handleInput} readOnly/>
        </div>
        <div css={css`  margin: 100px 30px 80px 40px; justify-content: flex-end; display: flex; flex-direction: row; height: 50px; 
`}>
        <button onClick={handleGoBack} css={css`${SubmitButton} margin-right: 20px; background-color: #F0F0F0;`}>작성 취소</button>
        <button onClick={postWrite} css={css`${SubmitButton} background-color: #C2DBE3;`} >작성 완료</button>
        </div>
      </div>
    </div>
  );
};