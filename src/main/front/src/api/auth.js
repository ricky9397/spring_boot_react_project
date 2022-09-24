import client from "./client";

// 로그인
export const login = ({ id, pw }) => client.post('/auth/login', { id, pw });

// 회원가입
export const register = (data) => client.post('/auth/register', data);

// 로그인상태확인
export const check = () => client.get('/auth/check');

// 로그아웃
export const logout = () => client.post('/api/auth/logout');