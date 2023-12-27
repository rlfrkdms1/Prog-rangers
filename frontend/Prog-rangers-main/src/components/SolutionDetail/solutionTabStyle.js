import { css } from '@emotion/react';

//solutionTab

export const tapLayout = css`
  width: 996px;
  margin: 50px auto 0;
`;

export const MytapLayout = css`
  width: 740px;
  margin: 50px auto 0;
`;

//viewSolution & lineReview

export const contentLayout = css`
  padding: 30px 20px 50px;
  font-size: 18px;
`;

export const contentMock = css`
  width: 956px;
  height: 500px;
  margin: 0 20px;
  font-size: 16px;
  color: #FFFFFF;
  background-color: #2A3746;

  overflow-y: auto;
  max-height: 500px;
  
  &::-webkit-scrollbar {
    width: 15px;
    height: 15px;
  }
  
  &::-webkit-scrollbar-thumb {
    background-color: #47556F;
    border-radius: 40px;
  }
  
  &::-webkit-scrollbar-track {
    background-color: transparent;
  }
  
  &::-webkit-scrollbar-corner{
  background-color: transparent;
  border-radius: 40px;
  }
`;

export const LineContentMock = css`
width: 740x;
height: 370px;
margin: 20px 10px ;
font-size: 17px;
color: #FFFFFF;
background-color: #2A3746;

overflow-y: auto;
max-height: 370px;

&::-webkit-scrollbar {
  width: 15px;
  height: 15px;
}

&::-webkit-scrollbar-thumb {
  background-color: #47556F;
  border-radius: 40px;
}

&::-webkit-scrollbar-track {
  background-color: transparent;
}

&::-webkit-scrollbar-corner{
background-color: transparent;
border-radius: 40px;
}
`;

export const ProfileContentMock = css`
  width: 807x;
  height: 370px;
  margin: 20px 10px ;
  font-size: 16px;
  color: #FFFFFF;
  background-color: #2A3746;

  overflow-y: auto;
  max-height: 370px;
  
  &::-webkit-scrollbar {
    width: 15px;
    height: 15px;
  }
  
  &::-webkit-scrollbar-thumb {
    background-color: #47556F;
    border-radius: 40px;
  }
  
  &::-webkit-scrollbar-track {
    background-color: transparent;
  }
  
  &::-webkit-scrollbar-corner{
  background-color: transparent;
  border-radius: 40px;
  }
`;