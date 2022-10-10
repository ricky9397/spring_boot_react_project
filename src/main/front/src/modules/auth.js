import { createAction, handleActions } from 'redux-actions';
import produce from 'immer';
import { takeLatest } from 'redux-saga/effects';
import createRequestSaga, {
    createRequestActionTypes
} from '../api/createRequestSaga';
import * as authAPI from '../api/auth';

// 성공여부 actions redux 
const CHANGE_FIELD = 'auth/CHANGE_FIELD';
const INITIALIZE_FORM = 'auth/INITIALIZE_FORM';

const [REGISTER, REGISTER_SUCCESS, REGISTER_FAILURE] = createRequestActionTypes(
    'auth/REGISTER'
);

const [LOGIN, LOGIN_SUCCESS, LOGIN_FAILURE] = createRequestActionTypes(
    'auth/LOGIN'
);

const [OAUTH2_LOGIN, OAUTH2_LOGIN_SUCCESS, OAUTH2_LOGIN_FAILURE] = createRequestActionTypes(
    'oauth2/LOGIN'
);

export const changeField = createAction(
    CHANGE_FIELD,
    ({ form, key, value }) => ({
        form, // register , login
        key, // username, password
        value // 실제 바꾸려는 값
    })
);

export const initializeForm = createAction(INITIALIZE_FORM, form => form); // register / login

/* 서버로 보낼 파라미터 데이터 */
export const register = createAction(REGISTER, ({ userEmail, userPassword, userName, userPhone, userYn }) => ({
    userEmail,
    userPassword,
    userName,
    userPhone,
    userYn
}));

export const login = createAction(LOGIN, ({ userEmail, userPassword }) => ({
    userEmail,
    userPassword
}));

export const googleLogin = createAction(OAUTH2_LOGIN, ({email, googleId, name}) => ({
    googleId,
    email,
    name
}));


//saga 생성 api axios호출 (createRequestSaga -> registerSaga)
const registerSaga = createRequestSaga(REGISTER, authAPI.register);
const loginSaga = createRequestSaga(LOGIN, authAPI.login);
const googleLoginSaga = createRequestSaga(OAUTH2_LOGIN, authAPI.googleLogin);

export function* authSaga() {
    yield takeLatest(REGISTER, registerSaga);
    yield takeLatest(LOGIN, loginSaga);
    yield takeLatest(OAUTH2_LOGIN, googleLoginSaga);
}

const initialState = {
    register: {
        userEmail: '',
        userPassword: '',
        userName: '',
        userPhone: '',
        userYn: '',
    },
    login: {
        userEmail: '',
        userPassword: ''
    },
    googleLogin: {
        googleId: '',
        email: '',
        name: ''
    },
    auth: null,
    authError: null
};

const auth = handleActions(
    {
        [CHANGE_FIELD]: (state, { payload: { form, key, value } }) =>
            produce(state, draft => {
                draft[form][key] = value; // 예: state.register.username을 바꾼다
            }),
        [INITIALIZE_FORM]: (state, { payload: form }) => ({
            ...state,
            [form]: initialState[form],
            authError: null // 폼 전환 시 회원 인증 에러 초기화
        }),
        // 회원가입 성공
        [REGISTER_SUCCESS]: (state, { payload: auth }) => ({
            ...state,
            authError: null,
            auth
        }),
        // 회원가입 실패
        [REGISTER_FAILURE]: (state, { payload: error }) => ({
            ...state,
            authError: error
        }),
        // 로그인 성공
        [LOGIN_SUCCESS]: (state, { payload: auth }) => ({
            ...state,
            authError: null,
            auth
        }),
        // 로그인 실패
        [LOGIN_FAILURE]: (state, { payload: error }) => ({
            ...state,
            authError: error
        }),
        // OAuth2 로그인 성공
        [OAUTH2_LOGIN_SUCCESS]: (state, { payload: auth }) => ({
            ...state,
            authError: null,
            auth
        }),
        // OAuth2 로그인 실패
        [OAUTH2_LOGIN_FAILURE]: (state, { payload: error }) => ({
            ...state,
            authError: error
        })
    },
    initialState
);

export default auth;
