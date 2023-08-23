import {
  ButtonStyle,
  innerPageStyle,
  pageStyle,
  galleryContainerStyle,
} from '../styles/MainPage';
import Gallery from '../components/Home/Gallery';
import { useNavigate } from 'react-router-dom';

const mockProblems = [
  {
    id: 1,
    text: '숫자 비교하기',
  },
  {
    id: 2,
    text: '두 수의 곱',
  },
  { id: 3, text: '두 수의 차' },
];
const mockSolutions = [
  { id: 1, text: '숫자 비교하기' },
  { id: 2, text: '두 수의 곱' },
  { id: 3, text: '두 수의 차' },
];

export default function Home() {
  const navigate = useNavigate();

  function handleClickReviewButton() {
    navigate('registerReview');
  }

  function handleClickSolution(solutionId) {
    navigate(`solution/detail/${solutionId}`);
  }

  function handleClickProblem(problemId) {
    navigate(`solutions/${problemId}`);
  }

  return (
    <div css={pageStyle}>
      <div css={innerPageStyle}>
        <button css={ButtonStyle} onClick={handleClickReviewButton}>
          리뷰 쓰러 갈래요
        </button>
        <div css={galleryContainerStyle}>
          <Gallery
            title="추천풀이"
            Items={mockSolutions}
            type="solution"
            handleClick={handleClickSolution}
          />
          <Gallery
            title="많이 푼 문제"
            Items={mockProblems}
            type="problem"
            handleClick={handleClickProblem}
          />
        </div>
      </div>
    </div>
  );
}
