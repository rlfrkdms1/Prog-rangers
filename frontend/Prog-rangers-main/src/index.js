import React from 'react';
import ReactDOM from 'react-dom/client';
import loadable from '@loadable/component';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {
  createBrowserRouter,
  RouterProvider,
} from 'react-router-dom';
import { KakaoRedirect } from './components/SignUp/KakaoRedirect';
import { NaverRedirect } from './components/SignUp/NaverRedirect';
import { GoogleRedirect } from './components/SignUp/GoogleRedirect';

const NotFound = loadable(() => import('./pages/NotFound'));
const MainPage = loadable(() => import('./pages/MainPage/MainPage'));
const SignUp = loadable(() => import('./pages/Auth/SignUp'));
const SignIn = loadable(() => import('./pages/Auth/SignIn'));
const Problems = loadable(() => import('./pages/Problems/Problems'));
const Solutions = loadable(() => import('./pages/Solutions/Solutions'));
const SolutionDetail = loadable(() => import('./pages/SolutionDetail'));
const Profile = loadable(() => import('./pages/Profile/Profile'));
const MyPage = loadable(() => import('./pages/MyPage/MyPage'));
const Account = loadable(() => import('./pages/Account/Account'));
const AccountChange = loadable(() => import('./pages/Account/AccountChange'));
const MySolution = loadable(() => import('./pages/MySolution/MySolution'));
const MySolutionDetail = loadable(() => import('./pages/MySolutionDetail'));
const MyComment = loadable(() => import('./pages/MyComment/MyComment'));
const Like = loadable(() => import('./pages/Like/Like'));
const Follow = loadable(() => import('./pages/Follow/Follow'));
const AddSolution = loadable(() => import('./pages/BoardPage/AddSolution'));
const EditSolution = loadable(() => import('./pages/BoardPage/EditSolution'));
const Scrap = loadable(() => import('./pages/BoardPage/Scrap'));
const WriteSolution = loadable(() => import('./pages/BoardPage/WriteSolution'));

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    errorElement: <NotFound />,
    children: [
      { index: true, path: '/', element: <MainPage /> },
      { path: 'signUp', element: <SignUp /> },
      { path: 'login', element: <SignIn /> },
      { path: 'login/kakao', element: <KakaoRedirect /> },
      { path: 'login/naver', element: <NaverRedirect /> },
      { path: 'login/google', element: <GoogleRedirect /> },
      { path: 'problems', element: <Problems /> },
      {
        path: 'problems/:problemId',
        element: <Solutions />,
      },
      { path: 'solutions/:solutionId',element: <SolutionDetail /> },
      { path : 'solutions/:id/detail/scrap', element: <Scrap />},
      {
        path: 'registerReview',
        element: <WriteSolution />,
      },
      { path: 'profile/:nickname', element: <Profile /> },
      { path: 'myPage', element: <MyPage /> },
      { path: 'account', element: <Account /> },
      { path: 'mySolution', element: <MySolution /> },
      { path: 'mySolution/:solutionId', element: <MySolutionDetail /> },
      { path: 'myComment', element: <MyComment /> },
      { path: 'like', element: <Like /> },
      { path: 'follow', element: <Follow /> },
      { path: 'account/accountChange', element: <AccountChange /> },
      { path: 'myPage/addsolution', element: <AddSolution/> },
      { path: 'mySolution/:solutionId/editsolution', element: <EditSolution/> },
    ],
  },
]);

const root = ReactDOM.createRoot(
  document.getElementById('root')
);
root.render(<RouterProvider router={router} />);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
