import { FcLikePlaceholder } from 'react-icons/fc';
import { RiShareBoxLine } from 'react-icons/ri';
import { flexLayout, indiLayout } from './indicatorSytle';

import { css } from '@emotion/react';

const p20 = css`
  padding-right: 20px;
`;

export const Indicators = () => {
  return (
    <div className="indicatorWrap" css={indiLayout}>
      <div className="allIndicators" css={flexLayout}>
        <div className="like" css={flexLayout}>
          <button className="icon" css={p20}>
            <FcLikePlaceholder size="25" />
          </button>
          <div
            css={css`
              padding-right: 20px;
            `}
          >
            <span>36</span>
            <span>개</span>
          </div>
        </div>
        <div className="scrap" css={flexLayout}>
          <button className="icon" css={p20}>
            <RiShareBoxLine size="25" color="#3486A0" />
          </button>
          <div>
            <span>5</span>
            <span>회</span>
          </div>
        </div>
      </div>
    </div>
  );
};
