import { css } from '@emotion/react';
import { Register } from '../../components/WriteBoard';
import { Provider, atom } from 'jotai';

export const targetScope = Symbol();
export const targetAtom = atom('');
export const valueScope = Symbol();
export const valueAtom = atom('');
export const nameScope = Symbol();
export const nameAtom = atom('');

//main-review-write 페이지
const WriteSolution = () => {
  const APIURL = `http://13.125.13.131:8080/api/v1/solutions`;

  return (
    <div
      css={css`
        width: 1200px;
        height: 100%;
        display: flex;
        flex-direction: column;
        align-items: center;
        margin: 0 auto;
      `}
    >
      <div
        css={css`
          height: 100%;
          width: 810px;
          display: flex;
          flex-direction: column;
        `}
      >
        <Provider scope={targetScope}>
          <Register postURL={APIURL} />
        </Provider>
      </div>
    </div>
  );
};

export default WriteSolution;
