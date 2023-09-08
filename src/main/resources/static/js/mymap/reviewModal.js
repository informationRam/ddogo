// modalFunctions.js
document.addEventListener("DOMContentLoaded", function () {
// mapNo 전역변수 설정
var mapNo='data-review-no';
console.log(mapNo);
// 모달폼에서 입력한 데이터 가져오는 함수
function getFormData() {
    var form = document.getElementById('updateReviewForm');
    var formData = new FormData(form);
    console.log(formData);
    alert(formData);
    return formData;
}
// JavaScript 코드에서 getReviewInfo() 함수를 호출하는 부분
var reviewNoButton = document.querySelector('.btn-update-review');
reviewNoButton.addEventListener('click', function() {
    var reviewNo = reviewNoButton.getAttribute('data-review-no');
    getReviewInfo(reviewNo);
});

// 서버에서 후기 정보 가져오기
/// getReviewInfo() 함수 수정
//function getReviewInfo(no) {
 //console.log(no);

    document.querySelectorAll('.btn-update-review').forEach(function(button) {
        button.addEventListener('click', function() {
            mapNo = this.getAttribute('data-review-no');
            console.log(mapNo);
            alert('아무거나');

            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        var savedReviewInfo = JSON.parse(xhr.responseText);
                        console.log(savedReviewInfo); // 데이터 확인을 위한 로그
                        if (savedReviewInfo != null) {
                            document.getElementById('inputReview').value = savedReviewInfo.review;
                            document.getElementById('inputMemo').value = savedReviewInfo.memo;

                            selectRadioButton(savedReviewInfo.recomm);
                            console.log('recomm 값 확인:', savedReviewInfo.recomm);

                            openModal();
                        } else {
                            alert('후기 조회 중 오류가 발생했습니다.');
                        }
                    }
                }
            };

            xhr.open('GET', '/mymap/getReview/' + mapNo, true);
            xhr.send();
        });
       });
    // }

// recomm 값을 확인하여 라디오 버튼 표시 설정
function selectRadioButton(recommValue) {
    var radioButtonYes = document.getElementById('myRecommendY');
    var radioButtonNo = document.getElementById('myRecommendN');

    if (recommValue === 'Y') {
        radioButtonYes.checked = true;
        radioButtonNo.checked = false;
    } else if (recommValue === 'N') {
        radioButtonYes.checked = false;
        radioButtonNo.checked = true;
    } else {
        radioButtonYes.checked = false;
        radioButtonNo.checked = false;
    }
}

// 모달 창 열기
function openModal() {
    var myModal = new bootstrap.Modal(document.getElementById('myModal'));
    myModal.show();
}

// 모달 닫기 시 모달 내용 초기화
document.getElementById('myModal').addEventListener('hidden.bs.modal', function () {
    document.getElementById('inputReview').value = '';
    document.getElementById('inputMemo').value = '';

    document.getElementById('myRecommendY').checked = false;
    document.getElementById('myRecommendN').checked = false;
});

});