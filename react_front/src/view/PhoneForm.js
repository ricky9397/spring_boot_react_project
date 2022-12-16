import "../assets/styles/phone.css";

const phoneForm = () => {
    return (
        <div className="login">
            <h3>전화번호를 입력하세요.</h3>
            <form>
                <div className="text_area">
                    <input
                        type="text"
                        id="userEmail"
                        name="userEmail"
                        //defaultValue="userEmail"
                        placeholder="000-0000-0000"
                        className="text_input"
                    />
                </div>
                <div className="next_layout">
                    <input
                        type="submit"
                        value="다음"
                        className="btn"
                    />
                </div>
            </form>
        </div>
    )
}

export default phoneForm;