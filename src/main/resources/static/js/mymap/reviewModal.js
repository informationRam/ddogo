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

//function getReviewInfo(reviewNo){
    document.querySelectorAll('.btn-update-review').forEach(function(button) {
      button.addEventListener('click', function() {
        var button = event.currentTarget; // 현재 클릭된 버튼
        var hotplaceNo = button.getAttribute('data-hotplace-no'); // data-hotplace-no 값 가져오기
        var mapNo = button.getAttribute('data-review-no'); // data-review-no 값 가져오기

            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        var savedReviewInfo = JSON.parse(xhr.responseText);
                        console.log(savedReviewInfo); // 데이터 확인을 위한 로그
                        if (savedReviewInfo != null) {
                         // 모달 데이터 표시
                            document.getElementById('review').value = savedReviewInfo.review;
                            document.getElementById('memo').value = savedReviewInfo.memo;

                            document.getElementById('hotplaceNo').value = hotplaceNo; // hotplaceNo input에 값 설정
                            document.getElementById('mapNo').value = mapNo; // mapNo input에 값 설정
                            console.log(mapNo);
                            console.log(hotplaceNo);

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

        // Review 필드의 값을 가져옵니다.
            var review = document.getElementById('review').value;

         // Review 필드가 비어 있는지 확인합니다.
         if (review.trim() === '') {
             // Review 필드가 비어 있다면 경고 메시지를 표시하고 함수 종료
             var reviewError = document.getElementById('review-error');
             reviewError.textContent = '후기는 필수 입력 항목입니다.';
            // 저장을 하지 않고 함수 종료
            return;

         } else {
             // Review 필드가 비어 있지 않다면 경고 메시지를 지웁니다.
             var reviewError = document.getElementById('review-error');
             reviewError.textContent = '';
         }

     // 추가할 데이터
         var mapNo = document.getElementById('mapNo').value; // mapNo 값을 가져옵니다.
         var hotplaceNo = document.getElementById('hotplaceNo').value; // hotplaceNo 값을 가져옵니다.

        // mapNo 및 hotplaceNo 값을 formData에 추가합니다
          formData.append('mapNo', mapNo);
          formData.append('hotplaceNo', hotplaceNo);
            console.log(mapNo);
            console.log(hotplaceNo);

      // memo와 recomm 값을 formData에 추가합니다
        var memo = document.getElementById('memo').value;
        var recomm = document.querySelector('input[name="recomm"]:checked').value;
        formData.append('memo', memo);
        formData.append('recomm', recomm);

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

                    } else {
                     alert('후기 수정 중 오류가 발생했습니다.');
                      // 오류 발생 시 모달 창을 다시 열어줍니다.
                             var myModal = new bootstrap.Modal(document.getElementById('myModal'));
                             myModal.show(); // 모달 열기
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

   /*     // 모달 닫기 시 백드롭 제거
        document.getElementById('myModal').addEventListener('hidden.bs.modal', function () {
            // 현재 모달의 백드롭 제거
            var modalBackdrop = document.querySelector('.modal-backdrop');
            if (modalBackdrop) {
                modalBackdrop.parentNode.removeChild(modalBackdrop);
            }
            // 모달을 닫기 위한 추가 코드
            var myModal = new bootstrap.Modal(document.getElementById('myModal'));
            myModal.hide();
        });*/


 });