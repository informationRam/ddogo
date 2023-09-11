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

    });

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


//검색창
// 검색어 입력 필드와 검색 버튼을 가져옵니다
var searchInput = document.getElementById('searchInput');
var searchButton = document.querySelector('.btn-danger');

// 검색 버튼 클릭 시 검색 함수 호출
searchButton.addEventListener('click', search);

// 검색어 입력 필드에서 Enter 키 입력 시 검색 함수 호출
searchInput.addEventListener('keydown', function (event) {
    if (event.key === 'Enter') {
        search();
    }
});

function search() {
    // 입력된 검색어 가져오기
    var searchText = document.getElementById('searchInput').value.trim().toLowerCase();

    // 모든 카드 요소 가져오기
    var cards = document.querySelectorAll('.card');

    // 카드를 순회하면서 검색어와 일치하는 카드 표시/숨김 설정
    cards.forEach(function (card) {
        var shouldDisplay = false;

        // 카드에서 음식점 이름과 주소 가져오기
        var hotplaceNameElement = card.querySelector('.card-title strong');
        var addressElement = card.querySelector('.card-body .address');

        if (hotplaceNameElement && addressElement) {
            var hotplaceName = hotplaceNameElement.textContent.toLowerCase();
            var address = addressElement.textContent.toLowerCase();

            // 검색어가 음식점 이름 또는 주소에 포함되어 있다면 해당 카드를 표시
            if (hotplaceName.includes(searchText) || address.includes(searchText)) {
                shouldDisplay = true;
            }
        }

        // 검색어와 일치하는 부분이 하나라도 있으면 카드 표시
        if (shouldDisplay) {
            card.style.display = 'block';
        } else {
            card.style.display = 'none';
        }
    });
}





//        // 각 카드의 속성 가져오기
//        var title = card.querySelector('#hotplaceName').textContent.toLowerCase();
//        var sido = card.querySelector('#sido').value.toLowerCase();
//        var gugun = card.querySelector('#gugun').value.toLowerCase();
//        var review = card.querySelector('#review').value.toLowerCase();
//        var address = card.querySelector('#address').value.toLowerCase();
//
//        // 검색어와 일치하는 부분이 하나라도 있으면 카드 표시
//        if (
//            title.includes(searchText) ||
//            sido.includes(searchText) ||
//            gugun.includes(searchText) ||
//            review.includes(searchText) ||
//            address.includes(searchText) ||
//            hotplaceCateNo.includes(searchText)
//        ) {
//            card.style.display = 'block';
//        } else {
//            card.style.display = 'none';
//        }
//    });
//}




