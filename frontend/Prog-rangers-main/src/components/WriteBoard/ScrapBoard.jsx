import { css } from "@emotion/react";
import { 
  TitleBox, 
  StyledInput,
  DetailBox,
  DetailInput 
} from "./inputBox";
import { TagAction } from "./TagAction";

export const ScrapBoard = () => {
  return(
    <div css={css`
    width: 996px;
    height: 929px;
    margin-top: 101px;
  `}>
    <div css={css`${TitleBox}`}>문제 제목은 올린 사람이 작성한 것으로 고정</div>
    <div css={css`${TitleBox} margin-top:44px`}>풀이 제목</div>
    <input placeholder="스크랩한 풀이의 제목을 입력해주세요" css={css`${StyledInput} `} />
    <div css={css`display: flex; flex-direction: row; margin: 20px 0 0 20px;`}>
      <TagAction data={"선택정렬"}/>
      <TagAction data={"스택"}/>
    </div>

    <div css={css`${TitleBox} margin-top: 50px;`}>풀이 설명</div>
    <div css={css`${DetailBox} height: 250px; width: 100%;`}>
      <textarea css={css`${DetailInput}`} />
    </div>

    <div css={css`${TitleBox} margin-top: 50px;`}>코드</div>
    <div css={css`${DetailBox} height: 250px; width: 100%;`}>
      <textarea css={css`${DetailInput} &:placeholder{color: #959595; font-size: 20px; font-weight: 400;}`} placeholder="다른 사람이 작성한 코드가 이미 써져 있음(수정불가)" />
    </div>

  </div>
  )
}