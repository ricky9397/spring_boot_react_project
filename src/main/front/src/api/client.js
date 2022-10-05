import axios from "axios";
import Cookies from "universal-cookie";
import * as authAPI from '../api/auth';

const client = axios.create();

// API 주소
// client.defaults.baseURL = 'http://localhost:8080';

const cookies = new Cookies();

// const auth_token = cookies.get('auth_token');
// const refresh_token = cookies.get('refresh_token');

// 헤더 설정
// client.defaults.headers.common['auth_token'] = auth_token ? 'Bearer ' + auth_token : null;
// client.defaults.headers.common['refresh_token'] = refresh_token ? 'Bearer ' + refresh_token : null;

// Request Interceptor 헤더 정보
client.interceptors.request.use(
    async config => {
        const token1 = cookies.get('auth_token');
        if (!token1) {
            config.headers['auth_token'] = null;
        }
        config.headers['auth_token'] = 'Bearer '+token1;
        console.log("config=============",config);
        return config;
    },
    error => {
        Promise.reject(error);
    }
)


client.interceptors.response.use(
    response => {
        console.log("interceptors.response=====", response);

        if (response.config.url === "/auth/login") {
            cookies.set('auth_token', response.headers.auth_token);
            cookies.set('refresh_token', response.headers.refresh_token);
        }

        return response;
    },
    async error => {
        const originalRequest = error.config;
        console.log('originalRequest======',error.response.status );
        console.log(error);
        // console.log('originalRequest._retry',originalRequest._retry);
        if (error.response.data.message === "401") {
            // originalRequest.headers['auth_token']
            
        }

        return Promise.reject(error);
    }
)

export default client;