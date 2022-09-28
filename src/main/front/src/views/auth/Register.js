import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from 'react-redux';
// import { register } from "../../api/auth";
import { changeField, initializeForm, register } from '../../modules/auth';

// export default function Register() {
const Register = () => {

  const [userEmail, setUserEmail] = useState('');
  const [userPassword, setUserPassword] = useState('');
  const [userName, setUserName] = useState('');
  const [userPhone, setUserPhone] = useState('');
  const [userYn, setUserCheck] = useState('');

  const [disabled, setDisabled] = useState(false);
  const [error, setError] = useState(false);

  const dispatch = useDispatch();

  // 컴포넌트가 처음 렌더링 될 때 form 을 초기화함
  useEffect(() => {
    dispatch(initializeForm('register'));
  }, [dispatch]);

  // 이메일 정규식
  const emailRegExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
  // 비밀번호 정규식
  const passwordRegEx = /^[A-Za-z0-9]{6,12}$/;
  // 전화번호 정규식
  const phoneRegExp = /^01(?:0|1|[6-9])(?:\d{3}|\d{4})\d{4}$/;
  // 빈값 정규식
  const replaceRegExp = /\s/g;

  const target = (e) => {
    const { value, name } = e.target;
    dispatch(
      changeField({
        form: 'register',
        key: name,
        value,
      }),
    );
  }

  const onEmailHandler = (e) => {
    setUserEmail(e.currentTarget.value.replace(replaceRegExp, '')); // 공백 제거 
    // if (!emailRegExp.test(e.currentTarget.value) || e.currentTarget.value === "") {
    //   setError(true); // disabled 처리하기 위한 error 로직
    // } else {
    //   setError(false);
    // }
    target(e);
  };

  const onPwHandler = (e) => {
    setUserPassword(e.currentTarget.value.replace(replaceRegExp, '')); // 공백 제거 
    // if (!passwordRegEx.test(e.currentTarget.value) || e.currentTarget.value === "") {
    //   setError(true);
    // } else {
    //   setError(false);
    // }
    target(e);
  };

  const onNameHanlder = (e) => {
    setUserName(e.currentTarget.value);
    target(e);
  };
  const onPhoneHandler = (e) => {

    setUserPhone(e.currentTarget.value.replace(replaceRegExp, ''));
    // if (!phoneRegExp.test(e.currentTarget.value) || e.currentTarget.value === "") {
    //   setError(true);
    // } else {
    //   setError(false);
    // }
    target(e);
  };

  // 체크박스 핸들러
  const onCheckHandler = (checked, name) => {
    if (checked) {
      setUserCheck('Y');
    } else {
      setUserCheck('N');
    }
    dispatch(
      changeField({
        form: 'register',
        key: name,
        value: userYn,
      }),
    );
  }

  // 회원가입 버튼 활성화 / 비활성화
  useEffect(() => {
    setDisabled(!(userEmail && userName && userPhone && userYn && !error));
  }, [userEmail, userPassword, userPhone, userYn]);




  // submit!
  const onSubmit = async (e) => {
    e.preventDefault();

    if (!emailRegExp.test(userEmail)) {
      setError("이메일형식이 잘못되었습니다.");
      return false;
    } 
    if (!passwordRegEx.test(userPassword)) {
      setError("비밀번호는 대소문자8자이상가능합니다.");
      return false;
    }
    if (!phoneRegExp.test(userPhone)) {
      setError("휴대폰입력이 잘못되었습니다.");
      return false;
    }
    if([userEmail, userPassword, userName,userPhone].includes('')){
      setError("빈 칸을 모두 입력하세요.");
      return false;
    }
    if(userYn === "" || userYn === "N"){
      setError("개인정보 보호정책에 동의해주세요.");
      return false;
    }

    // 입력 데이터
    const data = {
      userEmail: userEmail,
      userPassword: userPassword,
      userName: userName,
      userPhone: userPhone,
      userYn: userYn,
    }
    // redux 서버 호출
    dispatch(register(data));
  }

  return (
    <div className="container mx-auto px-4 h-full">
      <div className="flex content-center items-center justify-center h-full">
        <div className="w-full lg:w-6/12 px-4">
          <div className="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded-lg bg-blueGray-200 border-0">
            <div className="rounded-t mb-0 px-6 py-6">
              <div className="text-center mb-3">
                <h6 className="text-blueGray-500 text-sm font-bold">
                  간편 회원가입
                </h6>
              </div>
              <div className="btn-wrapper text-center">
                <button
                  className="bg-white active:bg-blueGray-50 text-blueGray-700 font-normal px-4 py-2 rounded outline-none focus:outline-none mr-2 mb-1 uppercase shadow hover:shadow-md inline-flex items-center font-bold text-xs ease-linear transition-all duration-150"
                  type="button"
                >
                  <img
                    alt="..."
                    className="w-5 mr-1"
                    src={require("assets/img/github.svg").default}
                  />
                  Github
                </button>
                <button
                  className="bg-white active:bg-blueGray-50 text-blueGray-700 font-normal px-4 py-2 rounded outline-none focus:outline-none mr-1 mb-1 uppercase shadow hover:shadow-md inline-flex items-center font-bold text-xs ease-linear transition-all duration-150"
                  type="button"
                >
                  <img
                    alt="..."
                    className="w-5 mr-1"
                    src={require("assets/img/google.svg").default}
                  />
                  Google
                </button>
              </div>
              <hr className="mt-6 border-b-1 border-blueGray-300" />
            </div>
            <div className="flex-auto px-4 lg:px-10 py-10 pt-0">
              <div className="text-blueGray-400 text-center mb-3 font-bold">
                <small>회원가입</small>
              </div>
              <form onSubmit={onSubmit}>
                <div className="relative w-full mb-3">
                  <label
                    className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                    htmlFor="grid-password"
                  >
                    이메일
                  </label>
                  <input
                    type="text"
                    className="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                    autoComplete="userEmail" name="userEmail" placeholder="Email" value={userEmail} onChange={onEmailHandler}
                  />
                </div>

                <div className="relative w-full mb-3">
                  <label
                    className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                    htmlFor="grid-password"
                  >
                    비밀번호
                  </label>
                  <input
                    type="password"
                    className="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                    autoComplete="userPassword" name="userPassword" placeholder="Password" value={userPassword} onChange={onPwHandler}
                  />
                </div>

                <div className="relative w-full mb-3">
                  <label
                    className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                    htmlFor="grid-password"
                  >
                    이름
                  </label>
                  <input
                    type="text"
                    className="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                    autoComplete="userName" name="userName" placeholder="Name" value={userName} onChange={onNameHanlder}
                  />
                </div>

                <div className="relative w-full mb-3">
                  <label
                    className="block uppercase text-blueGray-600 text-xs font-bold mb-2"
                    htmlFor="grid-password"
                  >
                    전화번호
                  </label>
                  <input
                    type="text"
                    className="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                    autoComplete="userPhone" name="userPhone" placeholder="Phone" value={userPhone} onChange={onPhoneHandler}
                  />
                </div>

                <div>
                  <label className="inline-flex items-center cursor-pointer">
                    <input
                      id="customCheckLogin"
                      type="checkbox"
                      className="form-checkbox border-0 rounded text-blueGray-700 ml-1 w-5 h-5 ease-linear transition-all duration-150"
                      name="userYn" onChange={e => { onCheckHandler(e.target.checked, e.target.name); }}
                    />
                    <span className="ml-2 text-sm font-semibold text-blueGray-600">
                      개인정보 보호정책에{" "}
                      <a
                        href="#pablo"
                        className="text-lightBlue-500"
                        onClick={(e) => e.preventDefault()}
                      >
                        동의합니까?
                      </a>
                    </span>
                  </label>
                </div>
                <div className="text-center mt-2">
                <span className="text-red-500 text-sm font-semibold">{error}</span>
                </div>
                <div className="text-center mt-6">
                  <button
                    className="bg-blueGray-800 text-white active:bg-blueGray-600 text-sm font-bold uppercase px-6 py-3 rounded shadow hover:shadow-lg outline-none focus:outline-none mr-1 mb-1 w-full ease-linear transition-all duration-150"
                    type="submit"
                  >
                    회원가입
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Register