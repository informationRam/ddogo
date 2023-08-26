var gps_use = null; //gps의 사용가능 여부
var gps_lat = null; // 위도
var gps_lng = null; // 경도
var gps_position; // gps 위치 객체

navigator.geolocation.getCurrentPosition((position) => {
  doSomething(position.coords.latitude, position.coords.longitude);
});


gps_check();
// gps가 이용가능한지 체크하는 함수이며, 이용가능하다면 show location 함수를 불러온다.
// 만약 작동되지 않는다면 경고창을 띄우고, 에러가 있다면 errorHandler 함수를 불러온다.
// timeout을 통해 시간제한을 둔다.
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
function showLocation(position) {
    gps_use = true;
    gps_lat = position.coords.latitude;
    gps_lng = position.coords.longitude;
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

function getLocation() {
    // HTML5의 geolocation으로 사용할 수 있는지 확인합니다
    if (navigator.geolocation) {
        // GeoLocation을 이용해서 접속 위치를 얻어옵니다
        navigator.geolocation.getCurrentPosition(function(position) {
            var gps_lat = position.coords.latitude, // 위도
                gps_lng = position.coords.longitude; // 경도
            var locPosition = new kakao.maps.LatLng(gps_lat, gps_lng); // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
            // 마커 위치를 설정합니다
            marker.setPosition(locPosition);
            // 지도 중심좌표를 접속위치로 변경합니다
            map.setCenter(locPosition);
        });
    } else {
        // GeoLocation을 사용할 수 없을 때 기본 마커 위치를 설정합니다
        // 이미 생성한 마커를 지도 위에 표시합니다
        marker.setMap(map);
     }

    }