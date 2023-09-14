import { useState } from 'react';
import { ViewSolution } from './ViewSolution';
import { LineReview } from './LineReview';
import { MAIN_DATA } from './MainData';

// import { css } from '@emotion/react';
// import { wrapLayout } from './solutionTabStyle';

export const SolutionTab = () => {
  const [active, setActive] = useState('viewSolution');

  const handleClickButton = (e) => {
    const { name } = e.target;
    setActive(name);
  };

  const selectComponent = {
    viewSolution: <ViewSolution />,
    lineReview: <LineReview />,
  };

  return (
    <div>
      {' '}
      <div className="tabArea">
        {MAIN_DATA.map((data) => {
          return (
            <button
              onClick={handleClickButton}
              name={data.name}
              key={data.id}
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
