import "../assets/styles/button.css";

const jenderForm = () => {
    return (
        <div className="login">
            <form>
                <div className="button_area">
                    <a href="" title="Button push blue/green" class="button btnPush btnBlueGreen">남자</a>
                    <a href="" title="Button push lightblue" class="button btnPush btnLightBlue">여자</a>
                    <div class="clear"></div>
                </div>
                <h3 className="title_area">성별을 선택 하세요.</h3>
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

export default jenderForm;