// modalFunctions.js


document.addEventListener("DOMContentLoaded", function () {


    // 모달폼에서 입력한 데이터 가져오는 함수
    function getFormData() {
        var form = document.getElementById('updateReviewForm');
        var formData = new FormData(form);
        console.log(formData);
        alert(formData);
        return formData;
    }
    // 모달활성화버튼 출력값 객체로 만들기
    var showReviewBtn = document.querySelector('.btn-update-review');
    showReviewBtn.addEventListener('click', function() {
        var mapNo = showReviewBtn.getAttribute('data-review-no');

    });

//function getReviewInfo(reviewNo){
    document.querySelectorAll('.btn-update-review').forEach(function(button) {
      button.addEventListener('click', function() {
          mapNo = this.getAttribute('data-review-no');
           // mapNo = reviewNo.getAttribute('data-review-no');
            console.log(mapNo); //mapNo 출력확인

            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        var savedReviewInfo = JSON.parse(xhr.responseText);
                        console.log(savedReviewInfo); // 데이터 확인을 위한 로그
                        if (savedReviewInfo != null) {
                         // 모달 데이터 표시
                            console.log(savedReviewInfo)
                            document.getElementById('review').value = savedReviewInfo.review;
                            document.getElementById('memo').value = savedReviewInfo.memo;

                            selectRadioButton(savedReviewInfo.recomm);
                            console.log('recomm 추천유무:', savedReviewInfo.recomm);

                            openModal();
                        } else {
                            alert('후기 조회 중 오류가 발생했습니다.');
                        }
                    }
                }
            };

            xhr.open('GET', '/mymap/getReview/' + mapNo, true);
            xhr.send();
             }); //버튼 이벤트리스너
       }); // 쿼리셀렉터
//    }//getReviewInfo함수

        // recomm 값을 확인하여 라디오 버튼 표시 설정
        function selectRadioButton(recommValue) {
            var radioButtonYes = document.getElementById('recommY');
            var radioButtonNo = document.getElementById('recommN');

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
        // 모달 창 닫기
        function closeModal() {
            var myModal = new bootstrap.Modal(document.getElementById('myModal'));
            myModal.hide();
        }


  window.submitForm=function() {
          // 폼 데이터 가져오기
          var form = document.getElementById('updateReviewForm');
          var formData = new FormData(form);
     // 추가할 데이터
              formData.append('mapNo', mapNo); // mapNo를 추가하려면 필요한 경우 이렇게 할 수 있습니다.
                console.log(mapNo);
          // AJAX 요청을 이용하여 서버에 폼 데이터를 전송하고 후기를 업데이트합니다.
          var xhr = new XMLHttpRequest();
          xhr.open('POST', '/mymap/updateReview/' + mapNo, true); // 수정 컨트롤러 엔드포인트 경로 설정
          xhr.setRequestHeader('Content-Type', 'application/json');

          // AJAX 요청 완료 후의 처리
          xhr.onload = function () {
              if (xhr.status === 200) {
                  alert('후기가 성공적으로 수정되었습니다.');

                 // 모달을 닫으려면 다음과 같은 코드를 사용하세요.
                        var myModal = new bootstrap.Modal(document.getElementById('myModal'));
                          myModal.hide(); // 모달 닫기

                    } else {
                        alert('후기 수정 중 오류가 발생했습니다.');
                        }
            };

           // 폼 데이터를 JSON 문자열로 변환하여 전송
         var formDataJson = {};
         formData.forEach(function (value, key) {
             formDataJson[key] = value;
         });
         var jsonData = JSON.stringify(formDataJson);

         // 서버에 요청 전송
         xhr.send(jsonData);
         console.log(jsonData);
   } //submitForm끝

        // 모달 닫기 시 백드롭 제거
        document.getElementById('myModal').addEventListener('hidden.bs.modal', function () {
            // 현재 모달의 백드롭 제거
            var modalBackdrop = document.querySelector('.modal-backdrop');
            if (modalBackdrop) {
                modalBackdrop.parentNode.removeChild(modalBackdrop);
            }
            // 모달을 닫기 위한 추가 코드
            var myModal = new bootstrap.Modal(document.getElementById('myModal'));
            myModal.hide();
        });


 });