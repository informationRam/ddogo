 //      <!-- 카테고리별 카드 출력 -->

document.addEventListener("DOMContentLoaded", function () {

        window.filterCards=function(category) {
         var cards = document.querySelectorAll('.card');
         if (category === 0) {
                 // "전체" 버튼을 클릭한 경우, 모든 카드를 보여줍니다.
                 cards.forEach(function (card) {
                     card.style.display = 'block';
                 });
           } else {
                  // "음식점" 또는 "카페" 버튼을 클릭한 경우, 해당 카테고리의 카드만 보여줍니다.
                  cards.forEach(function (card) {
                      var cateNo = card.getAttribute('data-cate-no');
                     if (cateNo == category) {
                                    card.style.display = 'block';
                                } else {
                                    card.style.display = 'none';
                                }
                            });
                        }
                    }
//          // 모든 카드를 숨깁니다.
//          var cards = document.querySelectorAll('.card');
//          cards.forEach(function (card) {
//              card.style.display = 'none';
//          });
//
//          // 선택한 카테고리에 해당하는 카드만 보여줍니다.
//          var filteredCards = document.querySelectorAll('.card[data-cate-no="' + category + '"]');
//          filteredCards.forEach(function (card) {
//              card.style.display = 'block';
//          });
//        }


  //      <!-- 맛집 카드 삭제 -->

            const deleteButtons = document.querySelectorAll('.btn-delete');

            deleteButtons.forEach(function(button) {
                button.addEventListener('click', function() {
                    if (confirm('정말로 삭제할까요?')) {
                        const mapId = this.getAttribute('data-map-id');
                        window.location.href = '/mymap/delete/' + mapId;
                    }
                });
            });

 // 페이지 버튼 클릭 이벤트 리스너

//    // 페이지 로딩 시 초기 페이지 로딩
//    window.addEventListener('load', function () {
//        loadPage(1);
//    });


    window.loadPage= function (element) {
         // 페이지 번호와 userId 가져오기
         const userIdInput = document.getElementById('userId');
         const userId = userIdInput.value;
         const clickedPageNo  = element.getAttribute('data-page');
         const dynamicUrl = `/mymap/api/${userId}?page=${clickedPageNo}`;

         // XMLHttpRequest 객체 생성
         const xhr = new XMLHttpRequest();

         // AJAX 요청 설정 (URL 변경)
         xhr.open('GET', dynamicUrl, true);

         // AJAX 요청 처리
         xhr.onreadystatechange = function () {
             if (xhr.readyState === 4 && xhr.status === 200) {
                 // 응답 데이터를 사용하여 페이지 업데이트
                 const listContainer = document.getElementById('listContainer');
                 listContainer.innerHTML = '';

                 // JSON 데이터 파싱
                 const responseData = JSON.parse(xhr.responseText);

                 // 카드 HTML 생성 및 초기화
                 let cardHtml = '';

                 // responseData에서 카드 데이터 추출
                 responseData.forEach(function (myhotpl) {
cardHtml += `<div class="card my-2" data-cate-no={{myhotpl.hotplaceCateNo}}>
                         <div class="card-body" >
                             <input type="hidden" id="lat" th:value="${myhotpl.lat}" />
                             <input type="hidden" id="lng" th:value="${myhotpl.lng}" />
                             <!--<h6 class="card-subtitle mb-2 text-muted" th:text="$${myhotpl.hotplaceName}"></h6>-->
                             <h5 class="card-title" th:text="${myhotpl.hotplaceName}"></h5>
                             <p class="card-text" th:text="${myhotpl.address}"></p>

                            <div class="square">
                                 <p th:text="${myhotpl.avgEmoResult != null ? myhotpl.avgEmoResult.toString() : ''}"></p>
                             </div>

                             <div class="justify-content-end">

                                 <a th:href="@{'/mymap/delete/'+${myhotpl.mapNo}}" class="btn btn-outline-secondary btn-sm btn-delete"
                                    data-toggle="modal" data-target="#deleteModal"
                                    th:attr="data-map-id=${myhotpl.mapNo}" onclick="return false;">삭제</a>

                                 <button type="button" class="btn btn-outline-success btn-sm btn-update-review"
                                         data-bs-toggle="modal" data-bs-target="#myModal"
                                         th:attr="data-review-no=${myhotpl.mapNo}" > 내 후기 수정</button>
                                 <i class="bi bi-bookmark-heart-fill"></i>
                             </div>
                         </div>
                     </div>`;
                });

                 // 카드 HTML을 추가
                listContainer.innerHTML = cardHtml;
             }
         };

         // AJAX 요청 보내기
         xhr.send();
    }; //pagination끝




}); // 페이지끝