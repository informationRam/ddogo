
var hotplList = []; // 마커 데이터를 저장할 배열
var markerList = []; // 마커 객체를 저장할 배열

document.addEventListener("DOMContentLoaded", function () {
    // 데이터 속성에서 userId 값을 읽어옴
    var userIdElement = document.getElementById('userIdContainer');
    var userId = userIdElement.getAttribute('data-user-id');
    console.log('UserId:', userId);

    // 서버로부터 데이터를 가져오고, 데이터가 로드된 후에 마커를 표시
    loadHotplList(userId);
    gps_check(map);
    // 함수로 Ajax 요청 분리
    function loadHotplList(userId) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', '/mymap/hotplaces/' + userId, true);
        xhr.onload = function () {
            if (xhr.status === 200) {
                hotplList = JSON.parse(xhr.responseText);
                console.log(hotplList);
                displayMarkers();
            } else {
                console.error('Error fetching data: ' + xhr.status);
            }
        };
        xhr.onerror = function () {
            console.error('Request failed');
        };
        xhr.send();
    }

    function displayMarkers() {
        // 데이터가 로드된 후에 마커를 표시하는 코드
        var mapContainer = document.getElementById('map');
        var mapOption = {
            center: new kakao.maps.LatLng(37.506016526623334, 127.10691218161601),
            level: 3
        };
        var map = new kakao.maps.Map(mapContainer, mapOption);
        var markerImage = new kakao.maps.MarkerImage(
            '/image/ddogo.png',
            new kakao.maps.Size(45, 55),
            new kakao.maps.Point(15, 35)
        );
           //json contetn 내 배열 반복
        for (var i = 0; i < hotplList.content.length; i++) {
            var hotplData = hotplList.content[i];
            var marker = new kakao.maps.Marker({
                map: map,
                image: markerImage,
                position: new kakao.maps.LatLng(hotplData.lat, hotplData.lng),
                title: hotplData.hotplaceName
            });
              // 마커를 지도에 표시
                    marker.setMap(map);
                    // 생성한 마커를 배열에 추가
                    markerList.push(marker);
        }
    }






 });//window.onload
