// 페이지 버튼 클릭 이벤트 핸들러
//ajax 페이징 핸들러 ,,, 포기 !!!

let currentPage = 1; // 현재 페이지를 전역 변수로 선언

window.loadPage = function (pageNo) {
    // 여기서 AJAX 요청을 보내고 반환된 JSON 데이터를 사용하여 페이지 업데이트
    const userId = document.getElementById('userId').value;
    const size = 4; // 페이지당 카드 수
    // user 데이터 가져오기
    const userSpan = document.getElementById('user');
    const userName = userSpan.textContent; // 또는 innerText를 사용할 수도 있습니다.

    // userName을 사용하여 필요한 작업 수행
    console.log('User Name:', userName);

    // 현재 페이지 번호를 설정
    currentPage = pageNo;
    const dynamicUrl = `/mymap/api/${userId}/${pageNo}?size=${size}`;

    // XMLHttpRequest 객체 생성
    const xhr = new XMLHttpRequest();

    // AJAX 요청 설정 (URL 변경)
    xhr.open('GET', dynamicUrl, true);

    // AJAX 요청 처리
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // 응답 데이터를 사용하여 페이지 업데이트
            const responseData = JSON.parse(xhr.responseText);

            // 카드 HTML 생성 및 초기화
            let cardHtml = '';

            // responseData에서 카드 데이터 추출
            responseData.content.forEach(function (myhotpl) {
                cardHtml += `
                <div id="listContainer" class="column">
                    <div class="cardBox">
                        <div class="useInfo" style="text-align: center; background-color: #FFD9DC; padding: 10px;">
                            <h2 style="margin: 10px 0;"><span th:text="${user.user_name}"></span>님의<br>맛집 리스트</h2>
                        </div>
                        <div class="input-group my-3">
                            <input type="text" class="form-control" placeholder="맛집 검색" id="searchInput">
                            <button class="btn btn-danger" onclick="search()">검색</button>
                        </div>
                        <div class="container-fluid" style="display: flex; flex-direction:column;">
                            <div class="button-container" style="display: flex; flex-basis: 0;">
                                <button id="everyCateButton" class="btn btn-danger btn-lg" data-cate-no="0" onclick="filterCards(0)" style="flex: 1; margin-right: 10px;">전체</button>
                                <button id="restaurantButton" class="btn btn-danger btn-lg" data-cate-no="1" onclick="filterCards(1)" style="flex: 1; margin-right: 10px;">식당</button>
                                <button id="cafeButton" class="btn btn-danger btn-lg" data-cate-no="2" onclick="filterCards(2)"   style="flex: 1; margin-left: 10px;">카페</button>
                            </div>
                        </div>
                        <div class="card my-2" th:each="myhotpl : ${hotplList}" th:attr="data-cate-no=${myhotpl.hotplaceCateNo}">
                            <div class="card-body" >
                                <input type="hidden" id="lat" th:value="${myhotpl.lat}" />
                                <input type="hidden" id="lng" th:value="${myhotpl.lng}" />
                                <div style="display: flex; align-items: center;">
                                    <h5 class="card-title">
                                        <strong th:text="${myhotpl.hotplaceName}" class="font-weight-bold"></strong>
                                        <!-- 카테고리 아이콘 -->
                                        <i th:if="${myhotpl.hotplaceCateNo == 1}" class="fa-solid fa-utensils fa-l" style="color: #c82f09;  margin-left: 5px;"></i>
                                        <i th:if="${myhotpl.hotplaceCateNo == 2}" class="fa-solid fa-mug-hot fa-l" style="color: #c82f09;  margin-left: 5px;"></i>
                                    </h5>
                                </div>
                                <p class="card-text text-muted" th:text="${myhotpl.address}"></p>
                                <div class="emoResult" style="margin: 5px; background-color: #f3c8c8; text-align: center; padding: 5px;">
                                    <p th:text="${myhotpl.avgEmoResult != null ? myhotpl.avgEmoResult.toString() : ''}" style="margin: 0;"></p>
                                </div>
                                <div class="justify-content-end">
                                    <a th:href="@{'/mymap/delete/'+${myhotpl.mapNo}}" class="btn btn-outline-secondary btn-sm btn-delete"
                                        data-toggle="modal" data-target="#deleteModal"
                                        th:attr="data-map-id=${myhotpl.mapNo}" onclick="return false;">삭제</a>
                                    <button type="button" class="btn btn-outline-success btn-sm btn-update-review"
                                        data-bs-toggle="modal" data-bs-target="#myModal"
                                        th:attr="data-review-no=${myhotpl.mapNo}, data-hotplace-no=${myhotpl.hotplaceNo}">내 후기</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal fade" id="myModal" tabindex="-1" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="titleModalLabel">내 후기 수정</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <form id="updateReviewForm">
                                        <div class="mb-3">
                                            <label for="review" class="form-label">REVIEW</label>
                                            <div id="error1"></div>
                                            <textarea class="form-control" id="review" name="review" rows="6"></textarea>
                                            <div id="review-error" class="text-danger"></div>
                                        </div>
                                        <div class="mb-3">
                                            <label for="memo" class="form-label">MEMO</label>
                                            <textarea class="form-control" id="memo" name="memo" rows="6"></textarea>
                                        </div>
                                        <input type="hidden" id="mapNo" name="mapNo" />
                                        <input type="hidden" id="hotplaceNo" name="hotplaceNo" />
                                        <div id="error2"></div>
                                        <div class="d-flex justify-content-center">
                                        <div class="btn-group" role="group" aria-label="Radio buttons">
                                            <input class="btn-check" type="radio" name="recomm" id="recommY" value="Y">
                                            <label class="btn btn-outline-primary mx-1" for="recommY">또갈지도</label>
                                            <input class="btn-check" type="radio" name="recomm" id="recommN" value="N">
                                            <label class="btn btn-outline-danger mx-1" for="recommN">안갈지도</label>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-primary"  onclick="submitForm()">저장</button>
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>`;
            });
            // 카드 HTML을 추가
            const listContainer = document.getElementById('listContainer');
            listContainer.innerHTML = cardHtml;
        }
    };

    // AJAX 요청 보내기
    xhr.send();
};