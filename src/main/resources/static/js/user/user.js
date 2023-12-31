 // 모달을 생성합니다.
        var myModal = new bootstrap.Modal(document.getElementById('myModal'));

        // 버튼을 가져옵니다.
        var button = document.getElementById('button');

        // 버튼 클릭 이벤트 리스너를 추가합니다.
        button.addEventListener('click', function () {
            // 모달을 표시합니다.
            myModal.show();

            // 모달 내용을 로드하는 함수를 호출합니다.
            loadText();
        });

        function loadText() {
            console.log("2");
            var emailValue = document.getElementById('email').value;
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

                    // 모달 내용 업데이트
                    var modalBody = document.querySelector('.modal-body');
                    modalBody.innerHTML = modalText;
                }
            };
            xhr.send(params);
        }

document.getElementById('button').addEventListener('click', loadText);
    console.log("1");


button.addEventListener('click', function () {
 console.log("1");
    myModal.show();

    // 모달 내용을 로드하는 함수를 호출합니다.
    loadText();
     console.log("2-1");
});

function loadText() {
    console.log("2");
    var emailValue = document.getElementById('email').value;
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
            document.getElementById('text').innerText = modalText;
        }
    };

    xhr.send(params);
}


/*

document.getElementById('button').addEventListener('click', loadText);
    console.log("1");*/

   /* // 폼 요소와 버튼을 가져옵니다.
    var form = document.querySelector('form');
    var button = document.getElementById('button');

    // 버튼 클릭 이벤트 리스너를 추가합니다.
    button.addEventListener('click', loadText);

    // Enter 키를 누를 때 폼이 자동으로 제출되도록 합니다.
    form.addEventListener('keydown', function (e) {
        if (e.key === "Enter") {
            e.preventDefault(); // Enter 키의 기본 동작을 막습니다.
            loadText(); // loadText 함수를 호출하여 "POST" 요청을 보냅니다.
        }
    });

function loadText() {
 console.log("2");
    var emailValue = document.getElementById('email').value;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/user/searchid', true);

    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');

    // email 값을 전송
    var params = 'email=' + emailValue;

    xhr.onload = function () {
     console.log("3: ",this.status );
        if (this.status == 200) {
        console.log("4");
            // JSON 응답 파싱
            var responseJson = JSON.parse(this.responseText);

            // user.userid 값을 가져와서 화면에 출력
            var userid = responseJson.userid;
            console.log("userid:", userid);

            // 결과를 화면에 출력
            document.getElementById('text').innerText = '사용자 ID : ' + userid;

        }
    };

    xhr.send(params);
}
*/