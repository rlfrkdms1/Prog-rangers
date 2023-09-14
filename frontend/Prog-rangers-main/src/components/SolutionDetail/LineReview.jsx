import {
  contentLayout,
  contentMock,
} from './solutionTabStyle';

export const LineReview = () => {
  return (
    <div>
      <div className="contentWrap">
        <div className="contentText" css={contentLayout}>
          여기에 본문내용을 출력합니다. <br />
          이 문제는 이렇게 저렇게 해서 풀었다. <br />
          아래의 코드에는 한줄리뷰가 달립니다. <br />
        </div>
        <div className="codeArea" css={contentMock}></div>
      </div>
    </div>
  );
};
