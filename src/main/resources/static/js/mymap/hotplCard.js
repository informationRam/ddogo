 //      <!-- 카테고리별 카드 출력 -->

document.addEventListener("DOMContentLoaded", function () {

        window.filterCards=function(category) {
          // 모든 카드를 숨깁니다.
          var cards = document.querySelectorAll('.card');
          cards.forEach(function (card) {
              card.style.display = 'none';
          });

          // 선택한 카테고리에 해당하는 카드만 보여줍니다.
          var filteredCards = document.querySelectorAll('.card[data-cate-no="' + category + '"]');
          filteredCards.forEach(function (card) {
              card.style.display = 'block';
          });
        }


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
});