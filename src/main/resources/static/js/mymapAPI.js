//mymapAPI.js
// JSON 데이터를 비동기적으로 가져와서 화면에 표시하는 함수 //html 타임리프로 userId
function loadPlaces() {
    const listContainer = document.getElementById('listContainer');
    const mapContainer = document.getElementById('mapContainer'); // 지도생성함수

    // 맛집 목록과 마커를 저장할 배열
    var myHotplList = [];

  // AJAX 요청으로 맛집 데이터 가져오기
    $.ajax({
        url: "/mymap/hotplaces/" + userName,
        method: "GET",
        success: function (data) {
            // 맛집 목록을 표시
            myHotplList = data.myHotplList;
            console.log("맛집 목록:", myHotplList);

            // 맛집 목록을 동적으로 생성하여 표시
            listContainer.innerHTML = ''; // 이전 목록을 지웁니다.

            myHotplList.forEach(function (myhotpl, index) {
                      var card = document.createElement('div');
                      card.className = 'card my-2'; // 추가: 카드 컬럼 크기 지정
                      card.innerHTML = `
                           <div class='card my-2'>
                                         <div class="card-body">
                                             <h5 class="card-title">${myhotpl.hotplaceName}</h5>
                                             <p class="card-text">${myhotpl.address}</p>
                                             <p class="card-text text-right" style="height: 100%;">${myhotpl.avgEmoResult}%</p>
                                             <div class="d-flex justify-content-end">
                                                 <a href="#" class="btn btn-outline-secondary btn-sm"><i class="bi bi-trash"></i></a> <!-- 삭제 -->
                                                 <a href="#" class="btn btn-outline-danger btn-sm"><i class="bi bi-heart-fill"></i></a> <!-- 찜 아이콘 -->
                                                 <a href="#" class="btn btn-outline-success btn-sm">내 후기</a> <!-- 내 후기 -->
                                             </div>
                                         </div>
                                     </div>
                                 `;

                // 클릭 이벤트 핸들러를 추가하여 해당 맛집의 위치로 이동
                card.addEventListener('click', function () {
                    var lat = myhotpl.lat;
                    var lng = myhotpl.lng;

                    // 선택된 맛집의 마커만 표시하고 나머지 숨기기
                     showMarker(index);
                    // 해당 맛집의 위치로 지도 이동
                    map.setCenter(new kakao.maps.LatLng(lat, lng));
                });


            });

            // 지도 초기화 및 표시 함수 호출
            initializeMap(myHotplList, mapContainer);
        },
        error: function (error) {
            console.error("데이터 가져오기 실패:", error);
        }

//지도 생성
function initializeMap(mapOption, mapContainer) {

    var mapOption = {
          center: new kakao.maps.LatLng(37.506016526623334, 127.10691218161601),
          level: 3
      }; //기본값 석촌*/

    var map = new kakao.maps.Map(mapContainer, mapOption) //지도 생성
    // html에서 받아온 값으로 마커 생성 : 맛집 마커
    var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

    // 서버에서 받은 맛집 목록 데이터를 사용하여 마커를 지도에 표시
    myHotplList.forEach(function (myhotpl) {
        var position = new kakao.maps.LatLng(myhotpl.latitude, myhotpl.longitude);
        var marker = new kakao.maps.Marker({
            position: position,
            map: map, // 이미 초기화한 지도 객체를 사용
            image: imageSrc,
            clickable: true // 마커를 클릭할 수 있도록 설정
        });

        // 마커를 클릭했을 때 이벤트 처리
        kakao.maps.event.addListener(marker, 'click', function () {
            var infoWindow = new kakao.maps.InfoWindow({
            content: myhotpl.hotplacename // 마커에 마우스 오버 시 표시할 내용 설정
        }); infoWindow.open(map, marker); // 정보 윈도우 열기
             });

        // 마커에 마우스를 올렸을 때 이벤트 처리
        kakao.maps.event.addListener(marker, 'mouseover', function () {
            var infoWindow = new kakao.maps.InfoWindow({
                content: myhotpl.hotplacename // 마커에 마우스 오버 시 표시할 내용 설정
            });
            infoWindow.open(map, marker); // 정보 윈도우 열기
        });

        // 마커에서 마우스를 벗어났을 때 이벤트 처리
        kakao.maps.event.addListener(marker, 'mouseout', function () {
            infoWindow.close(); // 정보 윈도우 닫기



        // 맛집 카드를 클릭했을 때 선택된 맛집의 마커를 표시하고 나머지 마커를 숨기는 함수
        function showMarker(selectedIndex, myMarkers, map) {
            // 모든 마커를 숨깁니다.
            hideMarkers();

            // 선택된 맛집의 마커만 표시합니다.
            myMarkers[selectedIndex].setMap(map);
        }

        // 모든 마커를 지도에서 숨기는 함수
        function hideMarkers(myMarkers) {
            setMarkers(myMarkers, null);
        }

        // 배열에 추가된 마커들을 지도에 표시하거나 삭제하는 함수
        function setMarkers(myMarkers, null) {
            for (var i = 0; i < myMarkers.length; i++) {
                myMarkers[i].setMap(map);
            }
        }

        });
    });
}

    gps_check(); // Check GPS and display current location

// GeoLocation을 이용해서 접속 위치를 얻어옵니다


// 현재위치정보 받기 변수
var gps_use = null; //gps의 사용가능 여부
var gps_lat = null; // 위도
var gps_lng = null; // 경도

//gps로 위치 불러오기
    function gps_check(){
        if (navigator.geolocation) {
            var options = {timeout:60000};
            navigator.geolocation.getCurrentPosition(showLocation, errorHandler, options);
        } else {
            alert("GPS_추적이 불가합니다.");
            gps_use = false;
        }
    }

    // gps 이용 가능 시, 위도와 경도를 반환하는 showlocation함수.
    function showLocation(pos) {
        console.log(pos);
        gps_use = true;
        gps_lat = pos.coords.latitude;
        gps_lng = pos.coords.longitude;


     // 위치 정보가 확인되었으므로 이제 gps_tracking 함수 호출 가능
        gps_tracking();


        // html에서 받아온 값으로 마커 생성 : 맛집 마커
        var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

    }
    // error발생 시 에러의 종류를 알려주는 함수.
    function errorHandler(error) {
        if(error.code == 1) {
            alert("접근차단");
        } else if( err.code == 2) {
            alert("위치를 반환할 수 없습니다.");
        }
        gps_use = false;
    }

      //현재위치 마커 표시
       function gps_tracking(){
            if (gps_use) {
                map.panTo(new kakao.maps.LatLng(gps_lat,gps_lng));  //카카오api 사용자 좌표로 부드럽게 이동
                 // 마커 이미지의 이미지 크기 입니다
                //var gps_content = new kakao.maps.Size(20, 20);
                var gps_content = '<div><img class="pulse" draggable="false" unselectable="on" src="https://ssl.pstatic.net/static/maps/m/pin_rd.png" alt="" style="width: 20px; height: 20px;"></div>';
                var currentOverlay = new kakao.maps.CustomOverlay({   //마커를 사용자의 위도와 경도 좌표에 찍어준다.
                    position: new kakao.maps.LatLng(gps_lat,gps_lng),
                    content: gps_content,
                    map: map
                });
                currentOverlay.setMap(map);
            } else {
              alert("접근차단하신 경우 새로고침, 아닌 경우 잠시만 기다려주세요.");
              gps_check();
            }
        }




