// // 모달 열기 함수
//    function openModal(button) {
//        var reviewNo = button.getAttribute('data-review-no');
//        var xhr = new XMLHttpRequest();
//        xhr.onreadystatechange = function () {
//            if (xhr.readyState === 4) {
//                if (xhr.status === 200) {
//                    var data = JSON.parse(xhr.responseText);
//                    document.getElementById('review').value = data.review;
//                    document.getElementById('memo').value = data.memo;
//                    $('#myModal').modal('show');
//                } else {
//                    alert('후기 조회 중 오류가 발생했습니다.');
//                }
//            }
//        };
//        xhr.open('GET', '/mymap/getReview/' + reviewNo, true);
//        xhr.send();
//    }
//
//    // 모달 닫기 시 모달 내용 초기화
//    $('#myModal').on('hidden.bs.modal', function () {
//        document.getElementById('review').value = '';
//        document.getElementById('memo').value = '';
//    });
//
//
//
//function updateReview() {
//    // 폼 데이터 가져오기
//    var form = document.getElementById('updateReviewForm'); //후기입력폼
//    var formData = new FormData(form);
//
//    // AJAX 요청을 이용하여 서버에 폼 데이터를 전송하고 후기를 업데이트합니다.
//    var xhr = new XMLHttpRequest();
//    xhr.open('POST', '/mymap/updateReview', true); // 수정 컨트롤러 엔드포인트 경로 설정
//    xhr.setRequestHeader('Content-Type', 'application/json');
//
//    // AJAX 요청 완료 후의 처리
//    xhr.onload = function () {
//        if (xhr.status === 200) {
//            alert('후기가 성공적으로 수정되었습니다.');
//            // 모달을 닫으려면 다음과 같은 코드를 사용하세요.
//            $('#myModal').modal('hide');
//        } else {
//            alert('후기 수정 중 오류가 발생했습니다.');
//        }
//    };
//
//    // 폼 데이터를 JSON 문자열로 변환하여 전송
//    var formDataJson = {};
//    formData.forEach(function (value, key) {
//        formDataJson[key] = value;
//    });
//    var jsonData = JSON.stringify(formDataJson);
//
//    // 서버에 요청 전송
//    xhr.send(jsonData);
//}