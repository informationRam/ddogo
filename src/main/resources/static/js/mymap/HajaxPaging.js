
    //페이지네이션 버튼 ajax 요청
    function loadPage(page,user_id) {
        $.ajax({
            url: "/mymap/hotplaces/" + user_id + "?page=" + page, // 서버 엔드포인트 URL
            type: "GET",
            dataType: "json", // 예상되는 응답 형식 (JSON)
            success: function(data) {
                // 페이지 데이터를 성공적으로 받았을 때 실행되는 코드
                // 받은 데이터를 사용하여 카드 영역을 업데이트
                updateCardArea(data);
            },
            error: function() {
                // 오류 발생 시 실행되는 코드
                alert("데이터 로드 중 오류가 발생했습니다.");
            }
        });
    }

    //카드영역 업데이트
    function updateCardArea(data) {
        var cardContainer = $("#listContainer"); // 카드 컨테이너 선택
        cardContainer.empty(); // 기존 카드를 모두 제거

        $.each(data, function(index, item) {
            var cardHtml = '<div class="card my-2">' +
                '<div class="card-body">' +
                '<input type="hidden" id="lat" value="' + item.lat + '"/>' +
                '<input type="hidden" id="lng" value="' + item.lng + '"/>' +
                '<input type="hidden" id="sido" value="' + item.sido + '"/>' +
                '<input type="hidden" id="gugun" value="' + item.gugun + '"/>' +
                '<input type="hidden" id="review" value="' + item.review + '"/>' +
                '<input type="hidden" id="address" value="' + item.address + '"/>' +
                '<input type="hidden" id="myHotplace" value="' + item.hotplaceName + '"/>' +
                '<div style="display: flex; align-items: center;">' +
                '<h5 class="card-title"><strong>' + item.hotplaceName + '</strong>';

            if (item.hotplaceCateNo === 1) {
                cardHtml += '<i class="fa-solid fa-utensils fa-l" style="color: #c82f09; margin-left: 5px;"></i>';
            } else if (item.hotplaceCateNo === 2) {
                cardHtml += '<i class="fa-solid fa-mug-hot fa-l" style="color: #c82f09; margin-left: 5px;"></i>';
            }

            cardHtml += '</h5></div>' +
                '<p class="card-text text-muted mb-0" style="font-size: 0.8em;">' + item.address + '</p>';

            if (item.avgEmoResult !== null) {
                cardHtml += '<div class="emoResult" style="margin: 5px; background-color: #f3c8c8; text-align: center; padding: 5px; border-radius: 10px;">' +
                    '<strong>리뷰 온도</strong>' +
                    '<a>' + item.avgEmoResult + '&#8451;</a>' +
                    '</div>';
            }

            cardHtml += '<div class="d-flex justify-content-end">' +
                '<button type="button" class="btn btn-outline-danger btn-sm btn-update-review mx-1" data-bs-toggle="modal" data-bs-target="#myModal" data-review-no="' + item.mapNo + '" data-hotplace-no="' + item.hotplaceNo + '">내 후기</button>' +
                '<a href="/mymap/delete/' + item.mapNo + '" class="btn btn-outline-secondary btn-sm btn-delete mx-1" data-toggle="modal" data-target="#deleteModal" data-map-id="' + item.mapNo + '" onclick="return false;">삭제</a>' +
                '</div></div></div>';

            cardContainer.append(cardHtml); // 카드를 카드 컨테이너에 추가
        });
    }

