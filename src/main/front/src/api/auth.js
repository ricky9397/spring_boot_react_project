import client from "./client";

// 로그인
export const login = (data) => client.post('/auth/login', data);

// 회원가입
export const register = (data) => client.post('/auth/register', data);

// 로그인상태확인
export const check = (data) => client.post('/auth/check', data);

// 로그아웃
// export const logout = () => client.get('/auth/logout');

export const admin = () => client.get('/admin/dashboard');

export const getToken = () => client.post('/JWT/getToken');

export const googleLogin = (data) => client.post('/oauth2/login/google', data);
