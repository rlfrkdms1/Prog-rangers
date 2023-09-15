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
} from './pages';
import { KakaoReirect } from './components/SignUp/KakaoRedirect';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    errorElement: <NotFound />,
    children: [
      { index: true, path: '/', element: <MainPage /> },
      { path: 'signUp', element: <SignUp /> },
      { path: 'signIn', element: <SignIn /> },
      // { path: 'login/kakao', element: <KakaoRedirect/>},
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
    ],
  },
]);

const root = ReactDOM.createRoot(
  document.getElementById('root')
);
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
