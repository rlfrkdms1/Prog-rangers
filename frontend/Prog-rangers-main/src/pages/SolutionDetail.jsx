import React from 'react';
import {
  Indicators,
  SolutionDetailHeader,
  SolutionTab,
  Comments
} from '../components/SolutionDetail';

const SolutionDetail = () => {
  return (
    <div>
      <SolutionDetailHeader />
      <SolutionTab />
      <Indicators />
      <Comments />
    </div>
  );
};

export default SolutionDetail;