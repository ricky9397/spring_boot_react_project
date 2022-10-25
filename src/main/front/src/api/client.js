import axios from "axios";
import Cookies from "universal-cookie";
import { useHistory } from "react-router-dom";


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
        const token = cookies.get('auth_token');
        if (!token) {
            config.headers['auth_token'] = null;
        } else {
            config.headers['auth_token'] = 'Bearer ' + token;
        }
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

            cookies.remove('auth_token');
            cookies.remove('refresh_token');
            
            cookies.set('auth_token', response.headers.auth_token);
            cookies.set('refresh_token', response.headers.refresh_token);
        }

        if (response.config.url.substring(0,13) === "/oauth2/login") {
            
            cookies.remove('auth_token');
            cookies.remove('refresh_token');
            
            cookies.set('auth_token', response.headers.auth_token);
            cookies.set('refresh_token', response.headers.refresh_token);
        }


        return response;
    },
    async error => {
        const history = useHistory();
        const originalRequest = error.config;
        console.log('originalRequest======', error.response.status);
        console.log(error);
        if (originalRequest.url != "/auth/login") {
            if (error.response.data.message === "401") {

                const user = localStorage.getItem("user");
                const obj = JSON.parse(user);
                try {
                    const rs = await client.post("/auth/login", {
                        userId: obj.userId,
                    }, {
                        headers: {
                            "refresh_token": cookies.get('refresh_token')
                        }
                    });
                } catch (e) {
                    console.log("토큰발급에러 : ", e);
                }
            } else if (error.response.data.message === "402") {
                console.log("토큰 발급 에러");
            } else if (error.response.data.message === "403") {

                cookies.remove('auth_token');
                cookies.remove('refresh_token');

                localStorage.removeItem('user'); // localStorage 에서 user 제거

                history.push("/");

            }
        }
        return Promise.reject(error);
    }
)

export default client;