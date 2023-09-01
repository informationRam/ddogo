
/*// 맛집 정보를 저장할 배열
var myHotplList = [];

// 서버에서 맛집 정보를 받아오는 함수
function fetchMyHotplaces() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/api/myhotplaces', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                var data = JSON.parse(xhr.responseText);
                myHotplList = data;
                renderHotplaceList();
            } else {
                console.error('Error fetching data:', xhr.statusText);
            }
        }
    };
    xhr.send();
}
// 맛집 리스트를 화면에 뿌려주는 함수
function renderHotplaceList() {
    var hotplaceListContainer = document.getElementById('hotplaceList');
    for (var i = 0; i < myHotplList.length; i++) {
        var hotplace = myHotplList[i];
        var card = document.createElement('div');
        card.className = 'card my-2';

        var cardBody = document.createElement('div');
        cardBody.className = 'card-body';

        var title = document.createElement('h5');
        title.className = 'card-title';
        title.textContent = hotplace.hotplaceName;

        var address = document.createElement('p');
        address.className = 'card-text';
        address.textContent = hotplace.address;

        var button = document.createElement('button');
        button.className = 'btn btn-primary';
        button.textContent = '찜하기';

        cardBody.appendChild(title);
        cardBody.appendChild(address);
        cardBody.appendChild(button);
        card.appendChild(cardBody);
        hotplaceListContainer.appendChild(card);
    }
fetchMyHotplaces(); // 맛집 정보를 받아옵니다.
}*/

// 사용자의 현재 위치를 확인하고 맛집 정보를 받아옵니다.
gps_check(map);

// 지도 생성 함수 실행
    var mapContainer = document.getElementById('map');
    var mapOption = {
        center: new kakao.maps.LatLng(37.506016526623334, 127.10691218161601),
        level: 3
    }; //기본값 석촌

    var map = new kakao.maps.Map(mapContainer, mapOption);

    //위치고정!!!
    gps_check(map); // Check GPS and display current location


// 현재위치 보여주기

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

/*      // data-positions 속성 값 가져오기
        var dataPositions = mapContainer.getAttribute('data-positions');
        var positions = JSON.parse(dataPositions);*/

        // html에서 받아온 값으로 마커 생성
        var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

            for (var i = 0; i < positions.length; i++) {
            var imageSize = new kakao.maps.Size(20, 25);
            var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

            var marker = new kakao.maps.Marker({
                map: map,
                position: new kakao.maps.LatLng(positions[i].latlng.getLat(), positions[i].latlng.getLng()),
                title: positions[i].title
            });
            console.log("Created marker:", marker);
        }
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

