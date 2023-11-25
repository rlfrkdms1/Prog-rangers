import { css } from "@emotion/react";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { tags } from "../Question/tagsform";
import Public from '../../assets/icons/solution-public.svg';
import Private from '../../assets/icons/solution-private.svg';
import Unfilled from '../../assets/icons/solution-unfilled-star.svg';
import Filled from '../../assets/icons/solution-filled-star.svg';
import Delete from '../../assets/icons/solution-tag-delete.svg';
import sortArray from '../../db/autocomplete.json';
import { SubmitButton } from "../../pages/BoardPage/buttonDiv";
import { 
  TitleBox, 
  StyledInput,
  Stars,
  DetailBox,
  DetailInput 
} from "./inputBox";

export const ScrapBoard = () => {
  const [ isPublic, setIsPublic ] = useState(true);
  const [ clickedStar, setClickedStar ] = useState([false, false, false, false, false]);
  const array = [0, 1, 2, 3, 4];
  const token = localStorage.getItem('token');
  const [ Algo, setAlgo ] = useState([]);
  const [ Data, setData ] = useState([]);
  const [ problemTitle, setProblemTitle ] = useState("");
  // const [ problemLevel, setLevel ] = useState(0);
  const [ problemLink, setLink ] = useState("");
  let algo = "";
  let data = "";
  let algoName = "";
  let dataName = "";
  const [ inputs, setInputs ] = useState({
    solution: '',
    description: '',
    code: [],
  });
  const [ addCode, setAddCode ] = useState('');
  const { solution, description, code } = inputs;

  const { id } = useParams();

  const getSols = async() => {
    try{
      const response = await axios.get(`http://13.124.131.171:8080/api/v1/solutions/${id}`);
      const algo = response.data.solution.algorithm;
      const data = response.data.solution.dataStructure;

      if (algo !== null) {
        const algoNameObject = sortArray.ALGORITHM.find(item => item["value"] === algo);
        if (algoNameObject) {
          const algoName = algoNameObject.name;
          setAlgo({
            value: algo,
            name: algoName
          });
        }
      }

      if (data !== null) {
        const dataNameObject = sortArray.DATASTRUCTURE.find(item => item["value"] === data);
        if (dataNameObject) {
          const dataName = dataNameObject.name;
          setData({
            value: data,
            name: dataName
          });
        }
      }
      
      let codeText = response.data.solution.code;
      codeText = codeText.join('\n');
      let title = response.data.problem.title
      let link = response.data.solution.link
      setProblemTitle(title);
      setLink(link);
      console.log(problemTitle);
      setInputs({
        ...inputs,
        code: codeText
      });
      
      console.log(response.data.problem.title);
      console.log(response.data.solution.link);
      
    }catch(error){
      console.log(error);
    }
  }
  const postWrite = async() => {
    handleAddCode();

    let star = clickedStar.filter(Boolean).length;
    try{
      const body = {
        problemTitle: problemTitle,
        title: inputs.solution,
        problemLink: problemLink,
        level: star.toString(),
        algorithm: Algo.value,
        dataStructure: Data.value,
        language: "JAVA",
        description: inputs.description,
        code: inputs.code,
        isPublic: true,
      }
      console.log('inputs.solution:', inputs.solution);

      const response = await axios.post(`http://13.124.131.171:8080/api/v1/solutions/${id}`, body, {
        headers: { Authorization: `Bearer ${token}`}
      });
      if(response.status === 201 ) {
        alert("질문이 등록되었습니다.");
        window.location.href = `http://localhost:3000/solutions/${id}`
      }
    }catch(error){
      console.log(error);
    }
  }

  // 작성취소 버튼
  const navigate = useNavigate();
  
  const handleGoBack = () => {
    navigate(-1); // 이전 페이지로 이동
  };

  const handleInput = (e) => {
    const { name, value } = e.target;
    setInputs({
      ...inputs,
      [name]: value,
    });
  }
  const handleAddCode = () => {
    if(addCode){
      let updatedCode = [ addCode ];
      updatedCode = updatedCode.join('\n');
      // setInputs({
      //   ...inputs,
      //   code: updatedCode,
      // });
      // setAddCode('');
    }
  }
  const TagDisplay1 = () => {
    const deleteHandler = () => {
      setAlgo([]);
    }
    return (
      <>
        {Algo.length !== 0 ?
          <div css={css`${tags}`} >
            {Algo.name}
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
        {Data.length !== 0?
          <div css={css`${tags}`} >
            {Data.name}
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

  useEffect(() => {
    getSols();
  }, []);


  return(
    <div css={css`
    width: 996px;
    height: 929px;
    margin-top: 101px;
  `}>
    <div className="header" css={css`display: flex; flex-direction: row; justify-content: space-between; align-items: flex-end;`}>
      <div css={css`${TitleBox}`}>문제 제목은 올린 사람이 작성한 것으로 고정</div>
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
    <div css={css`${TitleBox} margin-top:44px`}>풀이 제목</div>
      <input placeholder="스크랩한 풀이의 제목을 입력해주세요" css={css`${StyledInput} `} value={inputs.solution} name="solution" onChange={handleInput} />
    <div css={css`display: flex; flex-direction: row; margin: 20px 0 0 20px;`}>
      {TagDisplay1()}
      {TagDisplay2()}
    </div>

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

    <div css={css`${TitleBox} margin-top: 50px;`}>풀이 설명</div>
    <div css={css`${DetailBox} height: 250px; width: 100%;`}>
      <textarea css={css`${DetailInput} border: none;`} value={inputs.description} name="description" onChange={handleInput}/>
    </div>

    <div css={css`${TitleBox} margin-top: 50px;`}>코드</div>
    <div css={css`${DetailBox} height: 250px; width: 100%;`}>
      <div css={css`${DetailInput}`} >
        <textarea css={css`border: none; resize: none; color: #959595; &:placeholder{color: #959595; font-size: 20px; font-weight: 400;}`} 
          placeholder="다른 사람이 작성한 코드가 이미 써져 있음(수정불가)" 
          value={inputs.code} 
          name="code" 
          rows="30"
          cols="30"
          readOnly
        />
        </div>
    </div>
    <div css={css`${DetailBox} height: 250px; width: 100%;`}>
      <div css={css`${DetailInput}`} >
        <textarea
          css={css`border: none; resize: none; height: 250px;`}
          type="text"
          value={addCode}
          onChange={(e) => setAddCode(e.target.value)}
          placeholder="새로운 풀이 입력"
        />
      </div>
    </div>
    <div css={css`
        margin-top: 100px;
        height: 50px; 
        width: 996px;
        display: flex;
        flex-direction: row;
        justify-content: flex-end;
      `}>
        <button css={css`${SubmitButton} margin-right: 20px; background-color: #F0F0F0;`} onClick={handleGoBack}>작성 취소</button>
        <button css={css`${SubmitButton} background-color: #C2DBE3;`} onClick={postWrite}>작성 완료</button>
      </div>
  </div>
  )
}