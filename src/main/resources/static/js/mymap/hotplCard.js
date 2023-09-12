//<!-- 카테고리별 카드 출력 -->

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

//<!-- 맛집 카드 삭제 -->

const deleteButtons = document.querySelectorAll('.btn-delete');

deleteButtons.forEach(function(button) {
    button.addEventListener('click', function() {
        if (confirm('정말로 삭제할까요?')) {
            const mapId = this.getAttribute('data-map-id');
            window.location.href = '/mymap/delete/' + mapId;
        }
    });
});


//검색창 이벤트 리스너
// 검색어 입력 필드와 검색 버튼을 가져옵니다
document.addEventListener('DOMContentLoaded', function () {
    var searchInput = document.getElementById('searchInput');
    var searchButton = document.querySelector('.btn-danger');

    // 검색 버튼 클릭 시 검색 함수 호출
    searchButton.addEventListener('click', function(){
        // 입력된 검색어 가져오기
        var searchText = document.getElementById('searchInput').value.trim().toLowerCase();
        filterCards(searchText);
    });

    function filterCards(searchText) {
        var cards = document.querySelectorAll('.card');
        cards.forEach(function (card) {
            var hotplaceNameElement = card.querySelector('.card-body #myHotplace');
            var addressElement = card.querySelector('.card-body #address');

            if (hotplaceNameElement && addressElement) {
                var hotplaceName = hotplaceNameElement.value.toLowerCase();
                var address = addressElement.value.toLowerCase();

                if (hotplaceName.includes(searchText) || address.includes(searchText)) {
                    card.style.display = 'block';
                } else {
                    card.style.display = 'none';
                }
            }
        });
    }
});
