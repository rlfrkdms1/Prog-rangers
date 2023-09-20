import React from 'react';
import {
  Indicators,
  SolutionDetailHeader,
  SolutionTab,
} from '../components/SolutionDetail';

export const SolutionDetail = () => {
  return (
    <div>
      <SolutionDetailHeader />
      <SolutionTab />
      <Indicators />
    </div>
  );
};
