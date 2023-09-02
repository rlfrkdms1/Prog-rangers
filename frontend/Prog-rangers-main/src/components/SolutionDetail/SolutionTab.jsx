import { css } from '@emotion/react';

import { useState } from 'react';
import { selectTabStyle } from './solutionTabStyle';

export const SolutionTab = () => {
  const [currentTab, setTab] = useState(0);

  const contentArr = [
    {
      tab: '풀이보기',
      code: '본문 내용과 코드를 출력합니다',
    },
    {
      tab: '한줄리뷰',
      code: '본문 내용과 코드 한줄리뷰를 출력합니다',
    },
  ];

  const selectTabHandler = (index) => {
    setTab(index);
  };

  return (
    <>
      <div
        className="tabArea"
        css={css`
          width: 996px;
          margin: 0 auto;
        `}
      >
        <ul className="tab" css={selectTabStyle}>
          {contentArr.map((el, index) => (
            <li
              className={
                index === currentTab
                  ? 'submenu focused'
                  : 'submenu'
              }
              onClick={() => selectTabHandler(index)}
            >
              {el.tab}
            </li>
          ))}
        </ul>
        <div className="content">
          <p>{contentArr[currentTab].code}</p>
        </div>
      </div>
    </>
  );
};
