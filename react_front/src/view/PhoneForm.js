import "../assets/styles/phone.css";

const phoneForm = () => {
    return (
        <div className="login">
            <form>
                <div className="phone_area">
                    <input
                        type="text"
                        id="userPhone"
                        name="userPhone"
                        placeholder="000-0000-0000"
                        className="phone_input"
                    />
                </div>
                <h3 className="title_area">전화번호를 입력하세요.</h3>
                <div className="next_layout">
                    <input
                        type="submit"
                        value="다음"
                        className="btn_next"
                    />
                </div>
            </form>
        </div>
    )
}

export default phoneForm;