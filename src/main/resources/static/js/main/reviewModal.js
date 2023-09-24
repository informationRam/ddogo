//오늘 베스트 리뷰 모달 js
document.addEventListener("DOMContentLoaded", function () {
   // 모달창 생성
  var modal = document.createElement('div');
  modal.id = 'myModal';
  modal.className = 'modal fade';
  modal.tabIndex = '-1';

  var dialog = document.createElement('div');
  dialog.className = 'modal-dialog';

  var content = document.createElement('div');
  content.className = 'modal-content';

  // 모달 헤더 생성
  var header= document.createElement('div');
  header.className= 'modal-header';

  var title= document.createElement('h5');
  title.className= 'modal-title';
  title.textContent='Review';

  var closeButtonHeader= document.createElement('button');
  closeButtonHeader.type='button';
  closeButtonHeader.className='btn-close';
  closeButtonHeader.setAttribute("data-bs-dismiss", "modal");

  header.appendChild(title);
  header.appendChild(closeButtonHeader);

  content.appendChild(header);

  //모달 바디 생성
  var body = document.createElement('div');
  body.className = 'modal-body';

  content.appendChild(body);
  dialog.appendChild(content);
  modal.appendChild(dialog);

  // 모달창을 body에 추가
  document.body.appendChild(modal);

  //모달 푸터 생성
  var footer=document.createElement('div');
  footer.className='modal-footer';

  let closeModalButton=document.createElement('button')
  closeModalButton.id="closeModal";
  closeModalButton.type="button";
  closeModalButton.className="btn btn-danger";
  closeModalButton.setAttribute("data-bs-dismiss", "modal");
  closeModalButton.textContent = 'Close';

  footer.appendChild(closeModalButton);
  content.appendChild(footer);

   document.querySelectorAll('.heading').forEach(function (element) {
       element.addEventListener("click", function () {
           var hotplace_no = this.getAttribute('data-hotplace-no');
           var url = '/review/' + hotplace_no;

           var xhr = new XMLHttpRequest();
           xhr.open('GET', url, true);
           xhr.setRequestHeader('Content-Type', 'application/json');

           xhr.onreadystatechange = function () {
               if (xhr.readyState === 4) {
                   if (xhr.status === 200) {
                       var data = JSON.parse(xhr.responseText);

                      console.log(data);

                      // AJAX 요청이 성공적으로 완료된 후에만 모달 창 열기
                      var reviewModalElm = document.getElementById('myModal');
                      if (reviewModalElm != null) {
                        let modalBody = reviewModalElm.querySelector('.modal-body');
                        modalBody.innerHTML = '';

                        data.forEach(function(reviewList) {
                          let reviewBody = document.createElement('div');
                          reviewBody.classList.add('modalReviewCard', 'my-2');
                          let reviewTextElement = document.createElement('p');
                          reviewTextElement.textContent = reviewList.review;

                          let emoResultElement = document.createElement('p');
                          emoResultElement.style.textAlign = 'right';
                          emoResultElement.textContent ='리뷰 온도 '+reviewList.emo_result+'°C';
                          emoResultElement.classList.add('text1');
                          reviewBody.appendChild(reviewTextElement);
                          reviewBody.appendChild(emoResultElement);

                          modalBody.appendChild(reviewBody);
                        });

                         var modalInstance= new bootstrap.Modal(reviewModalElm);
                         modalInstance.show();
                      }

                   } else { // 요청 실패 시 처리
                       alert('요청 실패: ' + xhr.status);
                   }
               }
           };

           xhr.send();
       });
   });
});