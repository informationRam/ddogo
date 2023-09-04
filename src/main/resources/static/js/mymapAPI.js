
// JSON 데이터를 비동기적으로 가져와서 화면에 표시하는 함수
function loadPlaces() {
    const listContainer = document.getElementById('listContainer');
    const mapContainer = document.getElementById('mapContainer'); // 지도생성함수
    const mapObject;

    // 서버에서 JSON 데이터를 가져오기
  fetch('/mymap/userMap/{userNo}') // {userNo}는 로그인한 사용자의 번호로 대체필요
        .then(response => response.json())
        .then(data => {
            // JSON 데이터를 받아와서 처리
            const myHotplList = data; // JSON 데이터를 myHotplList 변수에 저장

            // 좌측에 맛집 리스트 표시 : 부트스트랩 카드 형식
               myHotplList.forEach(myhotpl => {
                   const listItem = document.createElement('div');
                   listItem.className = 'card my-2';
                   listItem.innerHTML = `
                       <div class="card-body">
                           <h5 class="card-title">${myhotpl.hotplaceName}</h5>
                           <p class="card-text">${myhotpl.address}</p>
                           <p class="card-text">${myhotpl.sido}</p>
                           <p class="card-text">${myhotpl.avgEmoResult}</p>
                           <button class="btn btn-primary">찜하기</button>
                       </div>
                   `;
                   listContainer.appendChild(listItem);
               });

        // 우측에 지도에 마커 표시 (지도 관련 코드는 여기에 추가)
        mapObject = new kakao.maps.Map(mapContainer, {
            center: new kakao.maps.LatLng(myHotplList[0].latitude, myHotplList[0].longitude),
            level: 2
        });

        //반복문으로 마커 표시
        myHotplList.forEach(myhotpl => {
                    const position = new kakao.maps.LatLng(myhotpl.latitude, myhotpl.longitude);
                    const marker = new kakao.maps.Marker({
                        position: position,
                        clickable: true // 마커를 클릭했을 때 지도의 클릭 이벤트 발생하지 않도록 설정
                    });
                    marker.setMap(mapObject); //마커를 지도에 표시함

                    //마커 클릭이벤트 설정
                    kakao.maps.event.addListener(marker, 'click', () => {
                        alert(`${myhotpl.hotplaceName} 선택.`);
                    });
                });
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

// 페이지 로드 시 맛집 데이터 가져오기
window.onload = loadPlaces;

 /*   var mapOption = {
        center: new kakao.maps.LatLng(37.506016526623334, 127.10691218161601),
        level: 3
    }; //기본값 석촌*/

/* 웹보고따라한거
   cosnt loadMap = (lat, lng)=>{
        mapObject = new.kakao.maps.Map(mapContainer,{
            center: new kakao.maps.LatLng(lat,lng), //지도의 중심좌표
            level: 3
     });
     loadPlaces();
};


    var map = new kakao.maps.Map(mapContainer, mapOption);
*/

/*
// 서버에서 맛집 정보를 받아오는 함수
const loadPlaces = () =>{
    list.innerText=''; //중복 제외를 위한 리스트 초기화
    const xhr = new XMLHttpRequest();
    xhr.open('GET', '/mymap/userMap/{userNo}');
    xhr.onreadystatechange = function () {
         if (xhr.readyState === XMLHttpRequest.DONE) {
             if (xhr.status >== 200 && xhr.status < 300) {
                 const myHotplList = JSON.parse(xhr.responseText);

                 //저장된 맛집 정보로 마커생성 지도 표시
                 for(const myhotpl of myHotplList){
                 const position = new kakao.maps.LatLng(  //마커 표시 위치 설정
                    myhotpl['latitude'],
                    myhotpl['longtitude']);

                 // 마커 생성 및 지도에 표시하는 로직 작성
                const marker = new kakao.maps.marker({
                    position: position,
                    clickable : true // 마커를 클릭했을 때 지도의 클릭 이벤트 발생하지 않도록 설정
                });

                //마커 클릭 이벤트 처리
                kakao.maps.event.addListener(marker,'click',() => {
                    alert('${myhotpl['hotplaceName'] 선택.');
                });
                marker.setMap(mapObject); //마커를 지도에 표시함
        }
        //DomParser로 html 을 DOM 구조로 변환
        const hotplElement  = new DomParser()
            .parseFromString(placeHtml, 'text/html')
            .querySelector('[rel="item"]');

        hotplElement.addEventListener('click',()=>{  //마커 클릭이벤트 등록: 위치이동
            const latlng = new kakao.maps.latlng(myhotpl['latitude'], myhotpl['longtitude']);
            mapObject.setCenter(latlng); //지도의 중심을 이동시킨다.
        });
        list.append(hotplElement); //html 에 추가
        }
    }else{
        }
    }
   };//loadplace함수끝
   xhr.send();
}
*/

/*      // data-positions 속성 값 가져오기
        var dataPositions = mapContainer.getAttribute('data-positions');
        var positions = JSON.parse(dataPositions);*/
// ----- 현재위치 보여주기
//위치고정!!!
    gps_check(map); // Check GPS and display current location

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




/*
 맛집 리스트를 화면에 뿌려주는 함수
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

}
*/
