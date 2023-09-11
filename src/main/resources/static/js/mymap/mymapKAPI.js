var hotplList = []; // 마커 데이터를 저장할 배열
var markerList = []; // 마커 객체를 저장할 배열
var map; // 전역으로 map 변수를 선언

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

    // 지도 초기화
    initializeMap();

    // 페이지가 로드될 때 gps_check 함수 자동 호출
    window.addEventListener('load', function () {
        gps_check();
    });

    // GPS 확인 버튼 클릭 시 gps_check 함수 호출
    document.getElementById('gpsBtn').addEventListener('click', function () {
        gps_check();
    });
        //기본지도 생성
        function initializeMap() {
            var mapContainer = document.getElementById('map');
            var mapOption = {
                center: new kakao.maps.LatLng(37.506016526623334, 127.10691218161601),
                level: 3
            };
            map = new kakao.maps.Map(mapContainer, mapOption);
            displayMarkers(); // 모든 마커를 표시
        }

        function displayMarkers() {
            // 데이터가 로드된 후에 마커를 표시하는 코드
            var markerImage = new kakao.maps.MarkerImage(
                '/image/ddogo.png',
                new kakao.maps.Size(35, 46),
                new kakao.maps.Point(15, 35)
            );
            // hotplList를 이용하여 마커 생성
            if (hotplList.content) {
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
        }

        function gps_check() {
            if (navigator.geolocation) {
                var options = { timeout: 60000 };
                navigator.geolocation.getCurrentPosition(showLocation, errorHandler, options);
            } else {
                alert("GPS 추적이 불가합니다.");
            }
        }

        function showLocation(pos) {
            console.log(pos);
            var gps_lat = pos.coords.latitude;
            var gps_lng = pos.coords.longitude;
            // 지도 중심 이동 및 마커 표시
            map.panTo(new kakao.maps.LatLng(gps_lat, gps_lng));
            var gps_content = '<div><img class="pulse" draggable="false" unselectable="on" src="https://ssl.pstatic.net/static/maps/m/pin_rd.png" alt="" style="width: 20px; height: 20px;"></div>';
            var currentOverlay = new kakao.maps.CustomOverlay({
                position: new kakao.maps.LatLng(gps_lat, gps_lng),
                content: gps_content,
                map: map
            });
            currentOverlay.setMap(map);
        }

        function errorHandler(error) {
            if (error.code == 1) {
                alert("접근차단");
            } else if (error.code == 2) {
                alert("위치를 반환할 수 없습니다.");
            }
        }
    });
