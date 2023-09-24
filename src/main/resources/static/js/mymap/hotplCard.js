// 카테고리별 카드 출력

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

});

//맛집 카드 삭제

const deleteButtons = document.querySelectorAll('.btn-delete');

deleteButtons.forEach(function(button) {
    button.addEventListener('click', function() {
        if (confirm('정말로 삭제할까요?')) {
            const mapId = this.getAttribute('data-map-id');
            window.location.href = '/mymap/delete/' + mapId;
        }
    });
});

document.addEventListener("DOMContentLoaded", function () {
    // 초기 페이지 로드 시 검색하지 않은 상태로 설정
    var searchInput = "";
    var currentPage = 1;
 // totalPages 변수를 사용할 수 있게 됩니다.
    console.log("Total Pages: " + totalPages);
    // 페이지 로드 시 초기 데이터 로딩
    loadHotplaces(searchInput, currentPage);

    // 검색 버튼 클릭 시
    document.getElementById('searchButton').addEventListener('click', function () {
        searchInput = document.getElementById('searchInput').value;
        currentPage = 1; // 검색 시 첫 번째 페이지로 설정
        loadHotplaces(searchInput, currentPage);
    });

    // 페이지 번호를 클릭했을 때 해당 페이지로 이동
    function goToPage(pageNo) {
        currentPage = pageNo;
        loadHotplaces(searchInput, currentPage);
    }

//    // 검색창 이벤트 처리 함수
//    function handleSearch() {
//        // 검색어 가져오기
//        var searchInput = document.getElementById('searchInput').value;
//        // 페이지 번호 초기화 (검색 시 첫 번째 페이지로 설정)
//        var page = 1;
//        // 검색어를 서버로 전송하고 검색 결과를 업데이트하는 함수 호출
//        searchHotplaces(searchInput, page);
//    }
//
//    // 페이지 번호를 클릭했을 때 해당 페이지로 이동
//    function goToPage(pageNo) {
//        // 검색어 가져오기
//        var searchInput = document.getElementById('searchInput').value;
//        // 페이지 번호 초기화 (검색 시 첫 번째 페이지로 설정)
//        currentPage = pageNo;
//        // 검색어를 서버로 전송하고 검색 결과를 업데이트하는 함수 호출
//        loadHotplaces(searchInput, currentPage);
//    }

    //검색결과 업데이트 함수
    function updateHotplaceList(searchResult) {
        // listContainer 요소 선택
        var cardList  = document.getElementById('cardList');
        // 이전 검색 결과를 지우기
        cardList.innerHTML = '';

        // 검색 결과를 반복하면서 HTML 카드를 생성하고 추가
        searchResult.forEach(function (result) {
            var cardDiv = document.createElement('div');
            cardDiv.classList.add('card', 'my-2');

            var cardBodyDiv = document.createElement('div');
            cardBodyDiv.classList.add('card-body');

            var cardTitle = document.createElement('h5');
            cardTitle.classList.add('card-title');
            cardTitle.textContent = result.hotplaceName;

            var cardSubtitle = document.createElement('h6');
            cardSubtitle.classList.add('card-subtitle', 'mb-2', 'text-muted');
            cardSubtitle.textContent = result.address;

            // 감정결과 박스
            var emoResultDiv = document.createElement('div');
            emoResultDiv.classList.add('emoResult');
            emoResultDiv.style.backgroundColor = '#f3c8c8';
            emoResultDiv.style.textAlign = 'center';
            emoResultDiv.style.padding = '5px';
            emoResultDiv.style.borderRadius = '10px';
            var emoStrong = document.createElement('strong');
            emoStrong.textContent = '리뷰 온도 ';
            var emoTemperature = document.createElement('a');
            emoTemperature.textContent = result.avgEmoResult != null ? result.avgEmoResult.toString() + '℃' : '';
            emoResultDiv.appendChild(emoStrong);
            emoResultDiv.appendChild(emoTemperature);

               var updateReviewButton = createButton('button', 'btn btn-outline-danger btn-sm btn-update-review mx-1', '내 후기', {
                   'data-bs-toggle': 'modal',
                   'data-bs-target': '#myModal',
                   'data-review-no': result.mapNo,
                   'data-hotplace-no': result.hotplaceNo
               });

               var deleteLink = createButton('a', 'btn btn-outline-secondary btn-sm btn-delete mx-1', '삭제', {
                   'href': '/mymap/delete/' + result.mapNo,
                   'data-toggle': 'modal',
                   'data-target': '#deleteModal',
                   'data-map-id': result.mapNo,
                   'onclick': 'return false;'
               });

               cardBodyDiv.appendChild(cardTitle);
               cardBodyDiv.appendChild(cardSubtitle);
               cardBodyDiv.appendChild(emoResultDiv);
               cardBodyDiv.appendChild(updateReviewButton);
               cardBodyDiv.appendChild(deleteLink);
               cardDiv.appendChild(cardBodyDiv);

               return cardDiv;
        });
    }

    // 이전 페이지로 이동 버튼에 이벤트 리스너 추가
    var previousPageButtons = document.querySelectorAll('.qna-page-link[aria-label="Previous (with search)"], .qna-page-link[aria-label="Previous (without search)"]');
    previousPageButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            if (currentPage > 1) {
                currentPage--;
                // 검색어 가져오기
                var searchInput = document.getElementById('searchInput').value;
                // 페이지 번호 초기화 (검색 시 첫 번째 페이지로 설정)
                var page = currentPage;
                // 검색어를 서버로 전송하고 검색 결과를 업데이트하는 함수 호출
                 searchHotplaces(searchInput, page, userId);
            }
        });
    });

    // 다음 페이지로 이동 버튼에 이벤트 리스너 추가
    var nextPageButtons = document.querySelectorAll('.qna-page-link[aria-label="Next (with search)"], .qna-page-link[aria-label="Next (without search)"]');
    nextPageButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            if (currentPage < totalPages) {
                currentPage++;
                // 검색어 가져오기
                var searchInput = document.getElementById('searchInput').value;
                // 페이지 번호 초기화 (검색 시 첫 번째 페이지로 설정)
                var page = currentPage;
                // 검색어를 서버로 전송하고 검색 결과를 업데이트하는 함수 호출
                searchHotplaces(searchInput, page, userId);
            }
        });
    });

    /* 검색어 서버 전송 */
    function searchHotplaces(searchInput, page) {

       // 여기서 {user_id}는 실제 사용자 ID로 변경해야 합니다.
          var url = '/hotplaces/' + userId + '?search=' + encodeURIComponent(searchInput) + '&page=' + page;

       // XMLHttpRequest 객체 생성
            var xhr = new XMLHttpRequest();
            xhr.open('GET', url, true);

        // 서버 응답을 처리하는 콜백 함수
            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        // 검색 결과를 화면에 업데이트하는 함수 호출
                       var searchResult = JSON.parse(xhr.responseText);
                           updateHotplaceList(searchResult);
                    } else {
                        console.error('검색 요청 실패');
                            }
                        }
                    };

                // 서버로 요청 전송
                xhr.send();
            }


    // 함수: 버튼 생성
        function createButton(tag, classes, text, attributes) {
            var button = document.createElement(tag);
            button.classList = classes;
            button.textContent = text;
            for (var key in attributes) {
                button.setAttribute(key, attributes[key]);
            }
            return button;
        }













//검색창 이벤트 리스너
//// 검색어 입력 필드와 검색 버튼을 가져옵니다 : 한페이지에 대한 결과 반환
//document.addEventListener('DOMContentLoaded', function () {
//    var searchInput = document.getElementById('searchInput');
//    var searchButton = document.querySelector('.btn-danger');
//
//    // 검색 버튼 클릭 시 검색 함수 호출
//    searchButton.addEventListener('click', function(){
//        // 입력된 검색어 가져오기
//        var searchText = document.getElementById('searchInput').value.trim().toLowerCase();
//        filterCards(searchText);
//    });
//
//    function filterCards(searchText) {
//        var cards = document.querySelectorAll('.card');
//        cards.forEach(function (card) {
//            var hotplaceNameElement = card.querySelector('.card-body #myHotplace');
//            var addressElement = card.querySelector('.card-body #address');
//
//            if (hotplaceNameElement && addressElement) {
//                var hotplaceName = hotplaceNameElement.value.toLowerCase();
//                var address = addressElement.value.toLowerCase();
//
//                if (hotplaceName.includes(searchText) || address.includes(searchText)) {
//                    card.style.display = 'block';
//                } else {
//                    card.style.display = 'none';
//                }
//            }
//        });
//    }
//
