$(".answer-write input[type=submit]").click(addAnswer);


function addAnswer(e) { // e = 이벤트 정보
    e.preventDefault(); // 서버로 데이터가 바로 전송되지 않도록 막기
    console.log("click!");

    var queryString = $(".answer-write").serialize(); // html에서 answer-write 클래스의 값 읽어오기
    console.log("query : " + queryString); // 입력값이 키=값 형태로 온다

    var url = $(".answer-write").attr("action");
    console.log("url : " + url);

    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onSuccess
    });
}

function onError() {

}

function onSuccess(data, status) { // answer 데이터, 상태
    // 객체의 getter 메서드에 대해서만 json데이터로 바꿔진다.
    console.log(data);
}

String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};
