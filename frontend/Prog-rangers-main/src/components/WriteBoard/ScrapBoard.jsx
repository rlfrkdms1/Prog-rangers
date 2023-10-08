import { css } from "@emotion/react";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { tags } from "../Question/tagsform";
import Delete from '../../assets/icons/solution-tag-delete.svg';
import sortArray from '../../db/autocomplete.json';
import { SubmitButton } from "../../pages/BoardPage/buttonDiv";
import { 
  TitleBox, 
  StyledInput,
  DetailBox,
  DetailInput 
} from "./inputBox";

export const ScrapBoard = () => {
  const token = localStorage.getItem('token');
  const [ Algo, setAlgo ] = useState([]);
  const [ Data, setData ] = useState([]);
  const [ problemTitle, setProblemTitle ] = useState("");
  const [ level, setLevel ] = useState(0);
  const [ link, setLink ] = useState("");
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
  const currentURL = window.location.href.split('/');
  const indexOfSolution = currentURL.indexOf("solution");
  const [ id, setId ] = useState(0);

  //주소에서 solution뒤에 id값 나오면 그 id값이 problem 번호가 됨
  useEffect(() => {
    if(indexOfSolution !== -1 && indexOfSolution < currentURL.length - 1){
      setId(currentURL[indexOfSolution + 1]);
      console.log(id);
    }else{
      alert("해당되는 문제번호가 없습니다.");
    }
  }, []);

  const getSols = async() => {
    try{
      const response = await axios.get(`http://13.124.131.171:8080/api/v1/solutions/${id}`);
      algo = response.data.solution.algorithmName;
      data = response.data.solution.dataStructureName;
      algoName = sortArray.ALGORITHM.find(item => item["value"] === algo).name;
      dataName = sortArray.DATASTRUCTURE.find(item => item["value"] === data).name;
      let codeText = response.data.solution.code;
      codeText = codeText.join('\n');
      setAlgo({
        value: algo,
        name: algoName
      });
      setData({
        value: data,
        name: dataName
      })
      setProblemTitle(response.data.problem.title);
      setLink(response.data.solution.link);
      setLevel(response.data.solution.level);
      setInputs({
        ...inputs,
        code: codeText
      });
    }catch(error){
      console.log(error);
    }
  }
  const postWrite = async() => {
    handleAddCode();
    try{
      const body={
        problemTitle: problemTitle,
        solutionTitle: inputs.solution,
        problemLink: link,
        algorithm: Algo.value,
        dataStructure: Data.value,
        language: "JAVA",
        description: inputs.description,
        code: inputs.code,
        isPublic: true,
      }
      const response = await axios.post(`http://{{HOST}}:8080/api/v1/solutions/new-form/${id-1}`, body, {
        headers: { Authorization: `Bearer ${token}`}
      });
      if(response.status === 201 ) {
        alert("질문이 등록되었습니다.");
        window.location.href = `http://localhost:3000/solution/${id}/detail`
      }
    }catch(error){
      console.log(error);
    }

  }
  const handleInput = (e) => {
    const { name, value } = e.target;
    setInputs({
      ...inputs,
      [name]: value,
    });
  }
  const handleAddCode = () => {
    if(addCode){
      let updatedCode = [ ...inputs.code, addCode ];
      updatedCode = updatedCode.join('/n');
      setInputs({
        ...inputs,
        code: updatedCode,
      });
      setAddCode('');
    }
    console.log(inputs);
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
  useEffect(() => {
    getSols();
  }, []);


  return(
    <div css={css`
    width: 996px;
    height: 929px;
    margin-top: 101px;
  `}>
    <div css={css`${TitleBox}`}>문제 제목은 올린 사람이 작성한 것으로 고정</div>
    <div css={css`${TitleBox} margin-top:44px`}>풀이 제목</div>
      <input placeholder="스크랩한 풀이의 제목을 입력해주세요" css={css`${StyledInput} `} value={inputs.solution} name="solution" onChange={handleInput} />
    <div css={css`display: flex; flex-direction: row; margin: 20px 0 0 20px;`}>
      {TagDisplay1()}
      {TagDisplay2()}
    </div>

    <div css={css`${TitleBox} margin-top: 50px;`}>풀이 설명</div>
    <div css={css`${DetailBox} height: 250px; width: 100%;`}>
      <textarea css={css`${DetailInput}`} value={inputs.description} name="description" onChange={handleInput}/>
    </div>

    <div css={css`${TitleBox} margin-top: 50px;`}>코드</div>
    <div css={css`${DetailBox} height: 250px; width: 100%;`}>
      <div css={css`${DetailInput}`} >
        <textarea css={css`border: none; resize: none; color: #959595; &:placeholder{color: #959595; font-size: 20px; font-weight: 400;}`} 
          placeholder="다른 사람이 작성한 코드가 이미 써져 있음(수정불가)" 
          value={inputs.code} 
          name="code" 
          rows="10"
          cols="30"
          readOnly
        />
        <textarea
          css={css`border: none; resize: none;`}
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
        <button css={css`${SubmitButton} margin-right: 20px; background-color: #F0F0F0;`}>작성 취소</button>
        <button css={css`${SubmitButton} background-color: #C2DBE3;`} onClick={postWrite}>작성 완료</button>
      </div>
  </div>
  )
}