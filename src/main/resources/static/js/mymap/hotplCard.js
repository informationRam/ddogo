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

// 카드 클릭 이벤트 핸들러
function cardClickHandler(lat, lng) {
    // 선택한 맛집의 좌표를 사용하여 해당 위치에 마커 표시
    const marker = new google.maps.Marker({
        position: { lat, lng },
        map: map, // map은 지도 객체입니다.
        title: '선택한 맛집',
    });







// 지도를 표시할 컨테이너 선택
const mapContainer = document.getElementById('map');


var mapContainer = document.getElementById('map');
var mapOption = {
    center: new kakao.maps.LatLng(37.506016526623334, 127.10691218161601),
    level: 3
};
// 각 카드에 클릭 이벤트 리스너 추가
const cards = document.querySelectorAll('.card');
cards.forEach(function (card) {
    card.addEventListener('click', function () {
      // 클릭한 카드의 위치 정보 가져오기
             const lat = parseFloat(card.querySelector('#lat').value);
             const lng = parseFloat(card.querySelector('#lng').value);

        // 해당 위치에 마커 생성
        const marker = new kakao.maps.Marker({
            position: new kakao.maps.LatLng(lat, lng),
        });

        // 기존 마커 삭제 (선택한 카드의 마커만 표시하기 위해)
        map.removeMarkers();

        // 마커 지도에 추가
        marker.setMap(map);

        // 해당 위치로 지도 중심 이동
        map.panTo(new kakao.maps.LatLng(lat, lng));
    });
});
</script>


//    var markers = []; // 마커를 저장할 배열
//
//
//    // 클릭한 카드의 위치로 지도의 중심을 이동
//    function moveCenter(lat, lng) {
//        var moveLatLon = new kakao.maps.LatLng(lat, lng);
//        map.panTo(moveLatLon);
//    }
//    // 각 카드에 이벤트 리스너를 추가합니다.
//    var cardElements = document.querySelectorAll('.card');
//    cardElements.forEach(function(card, index) {
//        card.addEventListener('click', function() {
//            // 클릭한 카드에 연결된 마커의 좌표를 가져옵니다.
//            var lat = parseFloat(card.querySelector('#lat').value);
//            var lng = parseFloat(card.querySelector('#lng').value);
//
//            // 클릭한 카드에만 마커를 표시하고 나머지 마커를 숨깁니다.
//            setMarkers(lat, lng);
//
//            // 클릭한 카드의 위치로 지도의 중심을 이동시킵니다.
//            moveCenter(lat, lng);
//        });
//    });
//
//    // 모든 마커를 생성하고 지도에 표시하는 함수
//    function displayAllMarkers() {
//     for (var i = 0; i < hotplList.content.length; i++) {
//              var hotplData = hotplList.content[i];
//              var marker = new kakao.maps.Marker({
//                  map: map,
//                  image: markerImage,
//                  position: new kakao.maps.LatLng(hotplData.lat, hotplData.lng),
//                  title: hotplData.hotplaceName
//              });
//            // 생성한 마커를 배열에 추가
//            markers.push(marker);
//        }
//    }
//
//    // 클릭한 카드에만 마커를 표시하고 나머지 마커를 숨기는 함수
//    function setMarkers(clickedLat, clickedLng) {
//        markers.forEach(function(marker) {
//            var markerLat = marker.getPosition().getLat();
//            var markerLng = marker.getPosition().getLng();
//            if (markerLat === clickedLat && markerLng === clickedLng) {
//                marker.setMap(map);
//            } else {
//                marker.setMap(null); // 숨기기
//            }
//        });
//    }
//
//    // 클릭한 카드의 위치로 지도의 중심을 이동시키는 함수
//    function moveCenter(lat, lng) {
//        var moveLatLon = new kakao.maps.LatLng(lat, lng);
//        map.panTo(moveLatLon);
//    }
//
//    // 모든 마커를 생성하고 지도에 표시
//    displayAllMarkers();