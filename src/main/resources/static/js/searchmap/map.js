var currentURL = window.location.href;
function getURLParameters(url) {
    var params = {};
    var queryString = url.split('?')[1]; // 쿼리 문자열 가져오기
    if (queryString) {
        var paramArray = queryString.split('&');
        for (var i = 0; i < paramArray.length; i++) {
            var param = paramArray[i].split('=');
            var paramName = decodeURIComponent(param[0]);
            var paramValue = decodeURIComponent(param[1]);
            params[paramName] = paramValue;
        }
    }
    return params;
}

// 3. URL에서 'lat'와 'lng' 값을 추출하고 변수에 저장
var urlParams = getURLParameters(currentURL);
var lat = urlParams['lat']; // 'lat' 파라미터의 값
var lng = urlParams['lng']; // 'lng' 파라미터의 값

var marker; //마커를 전역변수로 선언

// 마커를 담을 배열입니다
var markers = [];

// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
var mapTypeControl = new kakao.maps.MapTypeControl();

if(lat!=null && lng!=null){
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(lat, lng), // 지도의 중심좌표
            level: 4 // 지도의 확대 레벨
        };
    // 지도를 생성합니다
    var map = new kakao.maps.Map(mapContainer, mapOption);
} else {
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(37.499616, 127.030466), // 지도의 중심좌표
            level: 4 // 지도의 확대 레벨
        };
     //지도를 생성합니다
    var map = new kakao.maps.Map(mapContainer, mapOption);
}

// 지도에 컨트롤을 추가해야 지도위에 표시됩니다
// kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);

// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places();

// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
var infowindow = new kakao.maps.InfoWindow({zIndex:1});

// 키워드로 장소를 검색합니다
if(lat!=null){
    searchPlaces();
}

// 키워드 검색을 요청하는 함수입니다
function searchPlaces() {
    var keyword = document.getElementById('keyword').value;
    if (!keyword.replace(/^\s+|\s+$/g, '')) {
        alert('키워드를 입력해주세요!');
        return false;
    }
    // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
    ps.keywordSearch( keyword, placesSearchCB);
}

// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
        // 정상적으로 검색이 완료됐으면
        // 검색 목록과 마커를 표출합니다
        displayPlaces(data);
        // 페이지 번호를 표출합니다
        displayPagination(pagination);
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert('검색 결과가 존재하지 않습니다.');
        return;
    } else if (status === kakao.maps.services.Status.ERROR) {
        alert('검색 결과 중 오류가 발생했습니다.');
        return;
    }
}

// 검색 결과 목록과 마커를 표출하는 함수입니다
function displayPlaces(places) {
    var listEl = document.getElementById('placesList'),
    menuEl = document.getElementById('menu_wrap'),
    fragment = document.createDocumentFragment(),
    bounds = new kakao.maps.LatLngBounds(),
    listStr = '';
    // 검색 결과 목록에 추가된 항목들을 제거합니다
    removeAllChildNods(listEl);
    // 지도에 표시되고 있는 마커를 제거합니다
    removeMarker();
    for ( var i=0; i<places.length; i++ ) {
        // 마커를 생성하고 지도에 표시합니다
        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
            marker = addMarker(placePosition, i), /*마커에 값이 들어감.*/
            itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다
        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(placePosition);
        // 마커와 검색결과 항목에 mouseover 했을때
        // 해당 장소에 인포윈도우에 장소명을 표시합니다
        // mouseout 했을 때는 인포윈도우를 닫습니다
        function closeOverlay() {
            overlay.setMap(null);
        }
        (function(marker, places) {
            kakao.maps.event.addListener(marker, 'click', function(){ //커스텀오버레이호출.
                displayMyOverlay(marker, places); //검색 결과를 인자로 넣어준다.
            });
            kakao.maps.event.addListener(marker, 'mouseover', function() {
                displayInfowindow(marker, places);
            });
            kakao.maps.event.addListener(marker, 'mouseout', function() {
                infowindow.close();
            });
            itemEl.onmouseover =  function () {
                displayInfowindow(marker, places);
            };
            itemEl.onmouseout =  function () {
                infowindow.close();
            };
        })(marker, places[i]);
        fragment.appendChild(itemEl);
    }

    // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;
    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    map.setBounds(bounds);
}

// 검색결과 항목을 Element로 반환하는 함수입니다 <li>태그로 반환. <li class="item"> <span~
function getListItem(index, places) {
    var el = document.createElement('li'),
    itemStr = '<span class="markerbg marker_' + (index+1) + '"></span>' +
                '<div class="pinfo">' +
                '   <h5>' + places.place_name + '</h5>';
    if (places.road_address_name) {
        itemStr += '    <span>' + places.road_address_name + '</span>' +
                    '   <span class="jibun gray">' +  places.address_name  + '</span>';
    } else {
        itemStr += '    <span>' +  places.address_name  + '</span>';
    }
      itemStr += '  <span class="tel">' + places.phone  + '</span>' +
                '</div>';
    el.innerHTML = itemStr;
    el.className = 'item';
    return el;
}

// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addMarker(position, idx, title) {
    var imageSrc = '/image/cm04.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(45, 37),  // 마커 이미지의 크기
        imgOptions =  {
            spriteSize : new kakao.maps.Size(45, 691), // 스프라이트 이미지의 크기
            spriteOrigin : new kakao.maps.Point(0, (idx*46)+10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(21, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
            marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage
        });

    marker.setMap(map); // 지도 위에 마커를 표출합니다
    markers.push(marker);  // 배열에 생성된 마커를 추가합니다
    return marker;
}

// 지도 위에 표시되고 있는 마커를 모두 제거합니다
function removeMarker() {
    for ( var i = 0; i < markers.length; i++ ) {
        markers[i].setMap(null);
    }
    markers = [];
}

// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
function displayPagination(pagination) {
    var paginationEl = document.getElementById('pagination'),
        fragment = document.createDocumentFragment(),
        i;
    // 기존에 추가된 페이지번호를 삭제합니다
    while (paginationEl.hasChildNodes()) {
        paginationEl.removeChild (paginationEl.lastChild);
    }
    for (i=1; i<=pagination.last; i++) {
        var el = document.createElement('a');
        el.href = "#";
        el.innerHTML = i;
        if (i===pagination.current) {
            el.className = 'on';
        } else {
            el.onclick = (function(i) {
                return function() {
                    pagination.gotoPage(i);
                }
            })(i);
        }
        fragment.appendChild(el);
    }
    paginationEl.appendChild(fragment);
}

// 검색결과 목록 또는 마커를 마우스오버했을 때 호출되는 함수입니다
// 인포윈도우에 장소명을 표시합니다.
// 몸체랑 꼬리가 분리되는게 맘에 안들어서 수정하고 싶음...
function displayInfowindow(marker, places) {
    var content = '<div style="padding:5px; z-index:1; max-width: 150px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; ">'+
                      places.place_name +
                  '</div>';
    infowindow.setContent(content);
    infowindow.open(map, marker);
}

// 마커를 클릭한 경우 커스텀오버레이를 띄우기 위해 호출되는 함수.
function displayMyOverlay(clickedMarker, clickedPlaces){
    marker = clickedMarker;
    places = clickedPlaces;

    var overlay = new kakao.maps.CustomOverlay({
        map: map,
        position: marker.getPosition()
    });

    var coWrap = document.createElement('div');
    coWrap.classList.add('co-wrap');

    var coContent = document.createElement('div');
    coContent.classList.add('co-content');

    var closeButton = document.createElement('button');
    closeButton.id = 'closeButton';
    closeButton.classList.add('co-close');
    closeButton.onclick = function(){overlay.setMap(null);};

    coWrap.appendChild(coContent);
    coContent.appendChild(closeButton);

    var coTitle = document.createElement('div');
    coTitle.classList.add('co-title');
    coTitle.textContent = places.place_name;

    var placeCode = document.createElement('span');
    placeCode.id = 'placeCode';
    placeCode.classList.add('badge', 'bg-warning', 'mx-2');

    if (places.category_group_code === 'CE7') {
       placeCode.textContent = '카페';
    } else if (places.category_group_code === 'FD6') {
       placeCode.textContent = '음식점';
    }

    coContent.appendChild(coTitle);
    coTitle.appendChild(placeCode);

    var coBody = document.createElement('div');
    coBody.classList.add('co-body', 'my-2');

    coContent.appendChild(coBody);

    var coDesc = document.createElement('div');
    coDesc.classList.add('co-desc');

    var ellipsisRoad = document.createElement('div');
    ellipsisRoad.classList.add('ellipsis');
    ellipsisRoad.textContent = places.road_address_name;

    var ellipsisAddress = document.createElement('div');
    ellipsisAddress.classList.add('jibun', 'ellipsis');
    ellipsisAddress.textContent = places.address_name;

    var ellipsisPhone = document.createElement('div');
    ellipsisPhone.classList.add('phone', 'ellipsis');
    ellipsisPhone.textContent = places.phone;

    var detailLink = document.createElement('a');
    detailLink.href = places.place_url;
    detailLink.target = '_blank';
    detailLink.classList.add('link');
    detailLink.textContent = '상세보기';

    coDesc.appendChild(ellipsisRoad);
    coDesc.appendChild(ellipsisAddress);
    coDesc.appendChild(ellipsisPhone);
    coDesc.appendChild(detailLink);

    var btnDiv = document.createElement('div');
    btnDiv.classList.add('btn', 'btn-danger', 'btn-lg');

    var img = document.createElement('img');
    img.src = '/image/ddoBtn4.png';
    img.style.width = '65px';
    img.style.margin = '7px 0px 0px 5px';
    img.onclick = function () {
       openModal(marker, places);
    };

    btnDiv.appendChild(img);

    coBody.appendChild(coDesc);
    coBody.appendChild(btnDiv);

    overlay.setContent(coWrap);
    overlay.setMap(map); //오버레이를 맵에 세팅

}

//해당 마커의 장소정보를 모달의 form태그의 input요소로 넣기 위한 함수.
function openModal(marker, places) {
    if (marker && places) {
        var frmKeyword = document.getElementById('keyword').value;
        var markerLat = marker.getPosition().getLat();
        var markerLng = marker.getPosition().getLng();
        var placeName = places.place_name;
        var placeRoadAddress = places.road_address_name;
        var placeJibunAddress = places.address_name;
        var placeCateCode = places.category_group_code;

        // 각각의 hidden input 요소에 값을 설정
        document.getElementById('transMean').value = frmKeyword;
        document.getElementById('markerLat').value = markerLat;
        document.getElementById('markerLng').value = markerLng;
        document.getElementById('placeName').value = placeName;
        document.getElementById('placeRoadAddress').value = placeRoadAddress;
        document.getElementById('placeJibunAddress').value = placeJibunAddress;
        document.getElementById('placeCateCode').value = placeCateCode;
        var myModal = new bootstrap.Modal(document.getElementById('myModal'));
        myModal.show();
    } else {
        console.log('마커 정보 없음'); // 마커가 없을 때 처리
    }
}

 // 검색결과 목록의 자식 Element를 제거하는 함수입니다
function removeAllChildNods(el) {
    while (el.hasChildNodes()) {
        el.removeChild (el.lastChild);
    }
}

function saveFormData() {
   // 유효성 검사 수행
   if (formValidCheck()) {
      // 폼을 직접 제출
      document.getElementById("modalForm").submit();
   }
}

//유효성검사
function formValidCheck(){
   var review = document.getElementById("inputReview").value;
   var recomBtns = document.getElementsByName("myRecommend");
   var recomChecked = false;

   if(review.trim()===""){
      document.getElementById("error1").innerHTML = "리뷰는 필수 입력사항입니다.";
      document.getElementById("error1").className = "alert alert-secondary";
      /*alert("리뷰는 필수 입력사항입니다.");*/
      document.getElementById("inputReview").focus();
      return false;
   } else {
        // 에러 메시지가 없을 경우 메시지 영역 비우기
        document.getElementById("error1").innerHTML = "";
        document.getElementById("error1").className = "";
   }

   for (var i = 0; i < recomBtns.length; i++) {
       if (recomBtns[i].checked) {
           recomChecked = true;
           break;
       }
   }

   if(!recomChecked){
      /*alert("재방문의사를 선택해주세요.")*/
      document.getElementById("error2").innerHTML = "재방문의사를 선택해주세요.";
      document.getElementById("error2").className = "alert alert-secondary";
      return false;
   } else {
        // 에러 메시지가 없을 경우 메시지 영역 비우기
        document.getElementById("error2").innerHTML = "";
        document.getElementById("error2").className = "";
   }
   return true;
}