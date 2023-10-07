import { css } from "@emotion/react";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { tags } from "../Question/tagsform";
import Delete from '../../assets/icons/solution-tag-delete.svg';
import sortArray from '../../db/autocomplete.json';
import { 
  TitleBox, 
  StyledInput,
  DetailBox,
  DetailInput 
} from "./inputBox";

export const ScrapBoard = () => {
  const [ Algo, setAlgo ] = useState([]);
  const [ Data, setData ] = useState([]);
  let algo = "";
  let data = "";
  let algoName = "";
  let dataName = "";
  const [ inputs, setInputs ] = useState({
    solution: '',
    description: '',
    code: [],
  });
  const { solution, description, code } = inputs;

  //주소에서 solution뒤에 id값 나오면 그 id값이 problem 번호가 됨

  const getSols = async() => {
    try{
      const response = await axios.get(`http://13.124.131.171:8080/api/v1/solutions/2`);
      algo = response.data.solution.algorithmName;
      data = response.data.solution.dataStructureName;
      algoName = sortArray.ALGORITHM.find(item => item["value"] === algo).name;
      dataName = sortArray.DATASTRUCTURE.find(item => item["value"] === data).name;
      let codeText = response.data.solution.code.join(',/n');
      codeText = codeText.replace(/(?:\r\n|\r|\n)/g, '<br>');
      setAlgo({
        value: algo,
        name: algoName
      });
      setData({
        value: data,
        name: dataName
      })
      setInputs({
        ...inputs,
        code: codeText
      });
      // console.log(response.data.solution.code.join(',/n'));
      // console.log(algoName);
      // console.log(dataName);
    }catch(error){
      console.log(error);
    }
  }
  const postCode = () => {
    let contents = document.querySelector('textarea');
    contents = contents.value.replace(/(\n|\r\n)/g, '<br>');
    console.log(contents);
  }
  const postWrite = async() => {
    const response = await axios.post(`http://localhost:3000/api/v1/solutions/2`);

  }
  const handleInput = (e) => {
    const { name, value } = e.target;
    setInputs({
      ...inputs,
      [name]: value,
    });
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
      <textarea css={css`${DetailInput}`} />
    </div>

    <div css={css`${TitleBox} margin-top: 50px;`}>코드</div>
    <div css={css`${DetailBox} height: 250px; width: 100%;`}>
      <textarea css={css`${DetailInput} &:placeholder{color: #959595; font-size: 20px; font-weight: 400;}`} 
        placeholder="다른 사람이 작성한 코드가 이미 써져 있음(수정불가)" 
        value={inputs.code} 
        name="code" 
        rows="10"
        cols="50"
        onChange={handleInput}> 
        {/* {inputs.code.map((text, index) => {
          <React.Fragment key={index}>
            {text}
            {index < text.length - 1 && <br/> }
          </React.Fragment>
        })} */}
        {/* <c:out value="${inputs.code}" /> */}
      </textarea>
    </div>

  </div>
  )
}