
window.onload = function () {
  //지도 생성 스크립트
    var mapContainer = document.getElementById('map');
    var mapOption = {
        center: new kakao.maps.LatLng(37.506016526623334, 127.10691218161601),
        level: 3
    }; //기본값 석촌

    //지도 생성
    var map = new kakao.maps.Map(mapContainer, mapOption);

    var positions = [
                {
                    title: '카카오',
                    latlng: new kakao.maps.LatLng(33.450705, 126.570677)
                }
              ];

    //위치고정!!!
    gps_check(map); // Check GPS and display current location


   //새로운 마커이미지 생성
/*   var markerImage = new kakao.maps.MarkerImage(
                             '/static/image/ddogo.png',
                             new kakao.maps.Size(31, 35));*/

     for (var i = 0; i < positions.length; i++) {
            var imageSize = new kakao.maps.Size(24, 35);
            var markerImage = new kakao.maps.MarkerImage(
                               '/static/image/ddogo.png',
                               new kakao.maps.Size(31, 35));

            var marker = new kakao.maps.Marker({
                map: map,
                position: positions[i].latlng,
                title: positions[i].title
            });
        }

    // 마커가 지도 위에 표시되도록 설정합니다
    marker.setMap(map);

    //gps로 위치 불러오기 : gps_check()함수
        gps_check();
         function gps_check(){
             if (navigator.geolocation) {
                 var options = {timeout:60000};
                 navigator.geolocation.getCurrentPosition(showLocation, errorHandler, options);
             } else {
                 alert("GPS_추적이 불가합니다.");
                 gps_use = false;
             }
         }
         // 이용 가능하면, 위도와 경도를 반환하는 showlocation함수.
          function showLocation(pos) {
                  console.log(pos);
                  gps_use = true;
                  gps_lat = pos.coords.latitude;
                  gps_lng = pos.coords.longitude;
                  // 위치 정보가 확인되었으므로 이제 gps_tracking 함수 호출 가능
                  gps_tracking();
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


 }//window.onload