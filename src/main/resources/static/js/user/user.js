var myModal = new bootstrap.Modal(document.getElementById('myModal'));
var button = document.getElementById('button');
var emailInput = document.getElementById('email');
var modalTextElement = document.getElementById('text');

button.addEventListener('click', openModal);

emailInput.addEventListener('keydown', function (event) {
    if (event.key === 'Enter') {
        openModal();
    }
});

function openModal() {
    console.log("1");
    myModal.show();
    loadText();
    console.log("2-1");
}

function loadText() {
    console.log("2");
    var emailValue = emailInput.value;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/user/searchid', true);

    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    // email 값을 전송
    var params = 'email=' + emailValue;

    xhr.onload = function () {
        console.log("3: ", this.status);
        if (this.status == 200) {
            console.log("4");
            // JSON 응답 파싱
            var responseJson = JSON.parse(this.responseText);

            // user.userid 값을 가져와서 모달 내용에 출력
            var userid = responseJson.userid;
            console.log("userid:", userid);

            // 결과를 모달 내용에 출력
            var modalText = '사용자 ID : ' + userid;
            modalTextElement.innerText = modalText;
        }
    };

    xhr.send(params);
}