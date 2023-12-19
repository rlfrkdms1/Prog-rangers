import { css } from "@emotion/react";
import { ScrapBoard } from "../../components/WriteBoard/ScrapBoard";

const Scrap = () => {
  // const APIURL = `http://13.124.131.171:8080/api/v1/solutions/new-form/`;
  return(
    <div css={css`
      width: 100%; 
      height: 1700px; 
      display: flex; 
      flex-direction: column; 
      align-items: center; 
    `}>
      <ScrapBoard/>
    </div>
  );
};

export default Scrap;