
    // 페이지네이션 버튼 ajax 요청
    function loadPage(page, user_id) {
        $.ajax({
            url: `/mymap/hotplaces/${user_id}?page=${page}`,
            type: "GET",
            dataType: "json",
            success: function (data) {
                updateCardArea(data);
            },
            error: function () {
                handleError("데이터 로드 중 오류가 발생했습니다.");
            },
        });
    }

// 오류 처리 함수
function handleError(errorMessage) {
    // 오류 메시지를 화면에 표시하는 등의 처리 가능
    alert("페이징오류");
    console.error(errorMessage);
}
// 카드 생성 함수
function createCardItem(item) {
    const cardItem = $("<div>", { class: "card my-2" });
    const cardBody = $("<div>", { class: "card-body" });

    // hotplace 정보
    const hotplaceInfo = $("<div>", { style: "display: flex; align-items: center;" });
    hotplaceInfo.append(`<h5 class="card-title"><strong>${item.hotplaceName}</strong></h5>`);

    // hotplace 카테고리 아이콘
    if (item.hotplaceCateNo === 1) {
        hotplaceInfo.append('<i class="fa-solid fa-utensils fa-l" style="color: #c82f09; margin-left: 5px;"></i>');
    } else if (item.hotplaceCateNo === 2) {
        hotplaceInfo.append('<i class="fa-solid fa-mug-hot fa-l" style="color: #c82f09; margin-left: 5px;"></i>');
    }
  // 주소 정보
    const addressInfo = $("<p>", {
        class: "card-text text-muted mb-0",
        style: "font-size: 0.8em;",
        text: item.address
    });

    // 리뷰 온도 정보 (avgEmoResult가 null이 아닐 때)
    if (item.avgEmoResult !== null) {
        const emoResultInfo = $("<div>", {
            class: "emoResult",
            style: "margin: 5px; background-color: #f3c8c8; text-align: center; padding: 5px; border-radius: 10px;"
        });

        emoResultInfo.append("<strong>리뷰 온도</strong>");
        emoResultInfo.append($("<a>").text(`${item.avgEmoResult}℃`));

        // 카드 본문에 추가
        cardBody.append(emoResultInfo);
    }

    // 내 후기 및 삭제 버튼
    const buttonGroup = $("<div>", { class: "d-flex justify-content-end" });
    buttonGroup.append(`<button type="button" class="btn btn-outline-danger btn-sm btn-update-review mx-1" data-bs-toggle="modal" data-bs-target="#myModal" data-review-no="${item.mapNo}" data-hotplace-no="${item.hotplaceNo}">내 후기</button>`);
    buttonGroup.append(`<a href="/mymap/delete/${item.mapNo}" class="btn btn-outline-secondary btn-sm btn-delete mx-1" data-toggle="modal" data-target="#deleteModal" data-map-id="${item.mapNo}" onclick="return false;">삭제</a>`);

    // 카드 본문에 주소 정보, 버튼 그룹 추가
    cardBody.append(addressInfo);
    cardBody.append(buttonGroup);

    // 카드 아이템에 카드 본문 추가
    cardItem.append(cardBody);

    return cardItem ;
}

// 카드영역 업데이트
function updateCardArea(data) {
    const cardContainer = $("#listContainer");
    cardContainer.empty();

    $.each(data, function (index, item) {
        const cardItem = createCardItem(item);
        cardContainer.append(cardItem);
    });
}
