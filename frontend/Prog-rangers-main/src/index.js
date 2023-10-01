import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {
  createBrowserRouter,
  RouterProvider,
} from 'react-router-dom';
import {
  NotFound,
  MainPage,
  SignUp,
  SignIn,
  Problems,
  Solutions,
  SolutionDetail,
  RegisterReview,
  Profile,
  MyPage,
  Account,
  MySolution,
  MyComment,
  Like,
  Follow
} from './pages';
<<<<<<< HEAD
=======
import { KakaoRedirect } from './components/SignUp/KakaoRedirect';
import { NaverRedirect } from './components/SignUp/NaverRedirect';
import { GoogleRedirect } from './components/SignUp/GoogleRedirect';
>>>>>>> e1cb18d0af8a45d3c312367c939a2bfacdeb7498

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    errorElement: <NotFound />,
    children: [
      { index: true, path: '/', element: <MainPage /> },
      { path: 'signUp', element: <SignUp /> },
<<<<<<< HEAD
      { path: 'signIn', element: <SignIn /> },
=======
      { path: 'login', element: <SignIn /> },
      { path: 'login/kakao', element: <KakaoRedirect/> },
      { path: 'login/naver', element: <NaverRedirect/> },
      { path: 'login/google', element: <GoogleRedirect/> },
>>>>>>> e1cb18d0af8a45d3c312367c939a2bfacdeb7498
      { path: 'problems', element: <Problems /> },
      {
        path: 'solutions/:problemId',
        element: <Solutions />,
      },
      {
        path: 'solution/detail/:solutionId',
        element: <SolutionDetail />,
      },
      {
        path: 'registerReview',
        element: <RegisterReview />,
      },
      { path: 'profile/:userId', element: <Profile /> },
      { path: 'myPage', element: <MyPage /> },
      { path: 'account', element: <Account /> },
      { path: 'mySolution', element: <MySolution /> },
      { path: 'myComment', element: <MyComment /> },
      { path: 'like', element: <Like /> },
      { path: 'follow', element: <Follow /> },
    ],
  },
]);

const root = ReactDOM.createRoot(
  document.getElementById('root')
);
root.render(
<<<<<<< HEAD
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
=======
    <RouterProvider router={router} />
>>>>>>> e1cb18d0af8a45d3c312367c939a2bfacdeb7498
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
