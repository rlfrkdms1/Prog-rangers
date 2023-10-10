import React from 'react';
import {
  Indicators,
  SolutionDetailHeader,
  SolutionTab,
  Comments,
  Recommand,
} from '../components/SolutionDetail';

export const SolutionDetail = () => {
  return (
    <div>
      <SolutionDetailHeader />
      <SolutionTab />
      <Indicators />
      <Comments />
    </div>
  );
};
