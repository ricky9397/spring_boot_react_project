import React, { useReducer } from "react";
import { Switch, Route, Redirect } from "react-router-dom";
import img from "../assets/img/register_bg_2.png";
// components

import Navbar from "components/Navbars/AuthNavbar.js";
// import Navbar from "components/Navbars/IndexNavbar.js";
import FooterSmall from "components/Footers/FooterSmall.js";

// views

import Login from "views/auth/Login.js";
import Register from "views/auth/Register.js";

const initialState = {
  token : null,
  authenticated : false,
}

const reducer = (state, action) => {
  switch (action.type) {
    case 'SET_TOKEN':
      return {...state, token : action.token, authenticated : action.result};
    default:
      return state;
  }
}

const Auth = () => {

  const [state, dispatch] = useReducer(reducer, initialState);


  const handleLogin = (input) => {
    if (input != "undefined") {
      console.log("로그인성공");
      dispatch({
        type: 'SET_TOKEN',
        token: input,
        result: true
      });
      
    } else {
      console.log("로그인실패");
      dispatch({
        type: 'SET_TOKEN',
        token: null,
        result: false,
      });
    }
  }
  
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