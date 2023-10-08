import React from 'react';
import {
  Indicators,
  SolutionDetailHeader,
  SolutionTab,
  Comments,
  Recommand
} from '../components/SolutionDetail';

export const MySolutionDetail = () => {
  return (
    <div>
      <SolutionDetailHeader />
      <SolutionTab />
      <Indicators />
      <Comments />
      {/* <Recommand /> */}
    </div>
  );
};