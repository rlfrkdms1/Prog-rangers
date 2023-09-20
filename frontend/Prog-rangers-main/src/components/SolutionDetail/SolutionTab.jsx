import { useState } from 'react';
import { ViewSolution } from './ViewSolution';
import { LineReview } from './LineReview';
import { MAIN_DATA } from './MainData';

import { css } from '@emotion/react';
import { btnStyle, tapLayout } from './solutionTabStyle';
import { theme } from '../Header/theme';

import beforeClick from './tabIcons/beforeC.svg';
import afterClick from './tabIcons/afterC.svg';

export const SolutionTab = () => {
  const [active, setActive] = useState('viewSolution');

  const handleClickButton = (e) => {
    const { name } = e.target;
    setActive(name);
  };

  // const [color, setColor] = useState('');

  // const toggleActive = (e) => {
  //   setColor((prev) => {
  //     return e.target.value;
  //   });
  // };

  const selectComponent = {
    viewSolution: <ViewSolution />,
    lineReview: <LineReview />,
  };

  return (
    <div css={tapLayout}>
      <div
        className="tabArea"
        css={css`
          border-bottom: 1px solid ${theme.colors.light3};
        `}
      >
        {MAIN_DATA.map((data) => {
          return (
            <button
              onClick={handleClickButton}
              name={data.name}
              key={data.id}
            >
              <img
                src={beforeClick}
                css={css`
                  position: relative;
                `}
              />
              {data.text}
            </button>
          );
        })}
      </div>
      <div>
        {active && <div>{selectComponent[active]}</div>}
      </div>
    </div>
  );
};
