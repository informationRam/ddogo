//지도생성, 현위치, 카드클릭.js

var hotplList = []; // 마커 데이터를 저장할 배열
var markerList = []; // 마커 객체를 저장할 배열
var map; // 전역으로 map 변수를 선언
var selectedMarker = null; // 선택한 마커를 저장할 변수

 // 커스텀마커
    var markerImage = new kakao.maps.MarkerImage(
        '/image/ddogo.png',
        new kakao.maps.Size(49, 42),
        new kakao.maps.Point(21, 40)
    );



document.addEventListener("DOMContentLoaded", function () {
    // 데이터 속성에서 userId 값을 읽어옴
    var userIdElement = document.getElementById('userIdContainer');
    var userId = userIdElement.getAttribute('data-user-id');
    console.log('UserId:', userId);

     // 기존 지도 초기화 및 데이터 로드
        initializeMap();
        loadHotplList();

    // 서버로부터 데이터를 가져오고, 데이터가 로드된 후에 마커를 표시
    loadHotplList(userId);
    gps_check(map);

  // 클릭한 카드의 위치 정보를 가져와 지도에 표시
    const cards = document.querySelectorAll('.card');
    let infowindow = null; // 이전에 열린 인포윈도우를 저장할 변수

    cards.forEach(function (card) {
        card.addEventListener('click', function () {
            const lat = parseFloat(card.querySelector('#lat').value);
            const lng = parseFloat(card.querySelector('#lng').value);

           // 해당 위치에 마커 생성
            const marker = new kakao.maps.Marker({
                position: new kakao.maps.LatLng(lat, lng),
                 image: markerImage
               });

           // 이전에 선택한 마커가 있다면 지도에서 제거
                  if (selectedMarker) {
                      selectedMarker.setMap(null);
                  }

            // 선택한 마커를 지도에 표시
            marker.setMap(map);

            // 선택한 카드의 내용 가져오기 (맛집 이름)
              const cardTitle = card.querySelector('.card-title strong').textContent;

            // 이전에 열린 인포윈도우가 있다면 닫기
                    if (infowindow) {
                        infowindow.close();
                    }

              // 인포윈도우 생성 및 열기
                infowindow = new kakao.maps.InfoWindow({
                    content:'<div style="padding:10px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis">' + cardTitle + '</div>'
                });
                infowindow.open(map, marker);


            // 선택한 마커를 기억
            selectedMarker = marker;

          // 해당 위치로 지도 중심 이동
            map.panTo(new kakao.maps.LatLng(lat, lng));
        });
    });


    // 함수로 Ajax 요청 분리
        function loadHotplList() {
            var xhr = new XMLHttpRequest();
            xhr.open('GET', '/mymap/hotplaces/' + userId, true);
            xhr.onload = function () {
                if (xhr.status === 200) {
                       console.log(xhr.status); // 서버 응답 상태 코드
                       console.log(xhr.statusText); // 서버 응답 상태 메시지
                    hotplList = JSON.parse(xhr.responseText); // 서버로부터 받은 JSON 데이터를 hotplList에 할당
                    console.log(hotplList);
                    console.log(userId)
                    if (hotplList.length === 0) {
                        console.log("데이터가 비어 있습니다.");
                    } else {
                        console.log(hotplList);
                        // 데이터를 처리하고 출력
                        displayMarkers();
                    }
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

 // 데이터가 로드된 후에 마커를 표시 함수
        function displayMarkers() {

            // 커스텀마커
            var markerImage = new kakao.maps.MarkerImage(
                '/image/ddogo.png',
                new kakao.maps.Size(49, 42),
                new kakao.maps.Point(21, 40)
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