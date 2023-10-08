import { useState } from 'react';
import { ViewSolution } from './ViewSolution';
import { LineReview } from './LineReview';
import { MAIN_DATA } from './MainData';

import { css } from '@emotion/react';
import { tapLayout } from './solutionTabStyle';
import { theme } from '../Header/theme';

import beforeClick from './tabIcons/beforeC.svg';
import afterClick from './tabIcons/afterC.svg';

export const SolutionTab = () => {
  const [active, setActive] = useState('viewSolution'); //content 내용 변경
  const [isActive, setIsActive] = useState('viewSolution'); //background-image 변경

  const handleClickButton = (e) => {
    const name = e.target.name;
    setActive(name);
    setIsActive(name);
  };

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
              name={data.name}
              key={data.id}
              css={css`
                background-image: ${isActive === data.name
                  ? `url(${afterClick})`
                  : `url(${beforeClick})`};
                background-repeat: no-repeat;
                width: 125px;
                height: 40px;

                margin-right: 5px;
                padding-left: 30px;

                text-align: left;
                line-height: 40px;
              `}
              onClick={handleClickButton}
            >
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
