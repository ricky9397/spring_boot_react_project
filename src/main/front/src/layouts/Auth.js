import React, { useReducer, useEffect, useState } from "react";
import { Switch, Route, Redirect } from "react-router-dom";
import Cookies from "universal-cookie";
import { useDispatch, useSelector } from 'react-redux';

// img
import img from "../assets/img/register_bg_2.png";

// components
import Navbar from "components/Navbars/AuthNavbar.js";
import FooterSmall from "components/Footers/FooterSmall.js";

// views
import Login from "views/auth/Login.js";
import Register from "views/auth/Register.js";

const initialState = {
  token: null,
  authenticated: false,
}

const reducer = (state, action) => {
  switch (action.type) {
    case 'SET_TOKEN':
      return { ...state, token: action.token, authenticated: action.result };
    case 'DELETE_TOKEN':
      return { ...state, token: null, authenticated: false }
    default:
      return state;
  }
}

const Auth = () => {
  const [error, setError] = useState(null);

  const { form, auth, authError } = useSelector(({ auth }) => ({
    form: auth.register,
    auth: auth.auth,
    authError: auth.authError,
  }));

  // 회원가입 성공 / 실패 처리
  useEffect(() => {
    if (authError) {
      // 계정명이 이미 존재할 때
      if (authError.response.status === 409) {
        console.log("이밎 존재하는 계정명.")
        setError('이미 존재하는 계정명입니다.');
        return;
      }
      // 기타 이유
      console.log("에러~~");
      setError('회원가입 실패');
      return;
    }

    if (auth) {
      console.log('회원가입 성공');
      console.log(auth);
    }
  }, [auth, authError]);


  //======================================================== 로그인
  const [state, dispatch] = useReducer(reducer, initialState);

  const handleLogin = (input) => {
    if (input != "undefined") {
      console.log(input);
      console.log("로그인 성공");
      dispatch({
        type: 'SET_TOKEN',
        token: input,
        result: true
      });
      Cookies.set('refresh_token', input.refresh_token, { sameSite: 'strict' });
    } else {
      console.log("로그인 실패");
      dispatch({
        type: 'SET_TOKEN',
        token: null,
        result: false,
      });
    }
  }

  const handleLogout = () => {
    console.log("로그아웃");
    dispatch({
      type: 'DELETE_TOKEN'
    });
    localStorage.setItem('logout', Date.now()); // 로그아웃 시간
    Cookies.remove('refresh_token');
  }

  useEffect(() => {
    window.addEventListener('storage', (e) => {
      if (e.key === 'logout') {
        console.log("로그아웃 감지");
        dispatch({
          type: 'DELETE_TOKEN'
        });
      }
    });
  }, []);

  return (
    <>
      <Navbar transparent />
      <main>
        <section className="relative w-full h-full py-40 min-h-screen">
          <div
            className="absolute top-0 w-full h-full bg-blueGray-800 bg-no-repeat bg-full"
            style={{
              backgroundImage:
                "url(" + img + ")",
              // "url(" + require("assets/img/register_bg_2.png").default + ")",
            }}
          ></div>
          <Switch>
            <Route path="/auth/login" authenticated={state.authenticated} exact component={(props) => <Login {...props} handleLogin={handleLogin} />} />
            <Route path="/auth/register" exact component={Register} />
            <Redirect from="/auth" to="/auth/login" />
          </Switch>
          <FooterSmall absolute />
        </section>
      </main>
    </>
  );
}


export default Auth;