import axios from 'axios';
import { API_END_POINT } from './singup';

// TODO response 처리 로직
export const signIn= async (data)=>{
  const response = await axios.post(`${API_END_POINT}/login`,{...data});
  console.log(response)
}
