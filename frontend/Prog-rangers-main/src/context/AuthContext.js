import { createContext, useContext, useMemo, useState } from 'react';

const token = localStorage.getItem('token');
const nickname = localStorage.getItem('nickname');

export const IsLoginContext = createContext({ isLogin: nickname !== null && token !== null ? true : false});

export function IsLoginProvider({ children }) {
  const [ isLogin, setIsLogin ] = useState(nickname !== null && token !== null ? true : false);
  const value = useMemo(() => ({ isLogin, setIsLogin }), [ isLogin, setIsLogin ]);

  return (
    <IsLoginContext.Provider value={value}>
      {children}
    </IsLoginContext.Provider>
  );
}

export function useIsLoginState() {
  const context = useContext(IsLoginContext);
  if(!context){ 
    throw new Error('Cannot find isLoginProvider');
  }
  return context.isLogin;
}