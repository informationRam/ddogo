//시군구 카테고리 & 리뷰 모달js
document.addEventListener("DOMContentLoaded", function () {
    var resultDiv = document.getElementById("result");
    resultDiv.style.display = "none"; // 페이지 로드 시 결과 숨기기

    // 페이지 로드 시 초기 데이터를 가져와서 선택 상자를 생성
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/initialData", true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

    xhr.onload = function () {
        if (xhr.status === 200) {
            try {
                var response = JSON.parse(xhr.responseText);
                console.log(response);

                var sidoSelect = document.getElementById("sido");
                var gugunSelect = document.getElementById("gugun");

               // JSON 객체를 가나다순으로 정렬
                var sortedResponse = {};
                Object.keys(response).sort().forEach(function(key) {
                    sortedResponse[key] = response[key];
                });

                // 가나다순으로 정렬된 JSON 객체를 사용하여 시도 목록 초기화
                for (var sido in sortedResponse) {
                    var option = document.createElement("option");
                    option.value = sido;
                    option.textContent = sido;
                    sidoSelect.appendChild(option);
                }

                // 초기 시/도 선택
                sidoSelect.addEventListener("change", function () {
                    var selectedSido = this.value; // 선택한 시/도 값을 가져옴
                    var gugunArr = response[selectedSido];

                    // 시군구 목록 초기화
                    gugunSelect.innerHTML = '<option value="">선택하세요</option>';
                    if (gugunArr) {
                        for (var i = 0; i < gugunArr.length; i++) {
                            var option = document.createElement("option");
                            option.value = gugunArr[i];
                            option.textContent = gugunArr[i];
                            gugunSelect.appendChild(option);
                        }
                    }
                });


            } catch (error) {
                console.error("JSON 파싱 오류:", error);
            }

        } else {
            console.log("Error:", xhr.statusText);
            alert("Error:" + xhr.statusText);
        }
    };

    xhr.onerror = function () {
        console.log("Network Error");
        alert("Network Error");
    };

    xhr.send();

    // 검색 버튼 클릭 시 실행되는 부분
    var sendButton = document.getElementById("sendButton");
    sendButton.addEventListener("click", function () {
        var selectedSido = document.getElementById("sido").value; //선택한 시도값
        var selectedGugun = document.getElementById("gugun").value; //선택한 구군값
        var selectedCategory = parseInt(document.getElementById("category").value); //선택한 카테고리 값
        var resultDiv = document.getElementById("result");

        if (selectedSido === "" || selectedGugun === "" || isNaN(selectedCategory)) {
            // 사용자가 조건을 선택하지 않은 경우 결과를 숨김
            resultDiv.style.display = "none";
            alert("시도, 시군구, 카테고리를 모두 선택해주세요.");
        } else {
            // 선택한 조건에 따라 결과를 표시
            resultDiv.style.display = "block";

            // 이후 검색과 관련된 로직을 수행
            var formData = new FormData();
            formData.append("sido", selectedSido);
            formData.append("gugun", selectedGugun);
            formData.append("hotplace_cate_no", selectedCategory);

            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/main", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            xhr.onload = function () {
                if (xhr.status === 200) {
                    try {
                        var response = JSON.parse(xhr.responseText);
                        console.log(response);

                        // 여기서 response 객체를 활용하여 필요한 처리를 수행
                        // 테이블 업데이트 코드 추가
                        var resultDiv = document.getElementById("result");
                        resultDiv.innerHTML = ""; // 기존 데이터 삭제

                        if (response.monthBestList.length === 0) {
                            // 결과가 없을 때 메시지를 표시
                            var noDataMessage = document.createElement("p");
                            noDataMessage.textContent = "이번달에 저장된 가게가 없습니다.";
                            resultDiv.appendChild(noDataMessage);
                        } else {
                            // 결과가 있을 때 카드에 데이터 추가
                            for (var i = 0; i < response.monthBestList.length; i++) {
                                var entry = response.monthBestList[i];

                                // 카드를 생성하여 데이터 표시
                                var card = document.createElement("div");
                                card.className = "card p-2 mb-2";

                                var cardContent = document.createElement("div");
                                cardContent.className = "mt-1";
                                cardContent.style.padding = "15px";

                                // 순위를 표시하는 열 추가
                                var rankBadge = document.createElement("div");
                                rankBadge.className = "badge";
                                rankBadge.style.display = "inline-block";
                                rankBadge.style.marginRight = "10px";
                                rankBadge.textContent = "순위";
                                rankBadge.style.marginBottom = "10px";

                                var rankSpan = document.createElement("span");
                                rankSpan.textContent = "Best " + (i + 1); // 순위 계산 (0부터 시작하므로 +1)

                                rankBadge.appendChild(rankSpan);

                                // 가게 이름을 표시하는 열 추가
                                var nameH6 = document.createElement("h5");
                                nameH6.className = "heading";
                                nameH6.style.display = "inline-block";
                                nameH6.textContent = entry.hotplace_name;
                                nameH6.setAttribute("data-hotplace-no", entry.hotplace_no); // 제목을 클릭했을 때 hotplace_no data전달
                                nameH6.style.marginBottom = "10px"; // 상하 마진

                                // 가게 주소를 표시하는 열 추가
                                var addressDiv = document.createElement("div");
                                addressDiv.className = "ms-2 c-details";

                                var addressSpan = document.createElement("span");
                                addressSpan.className = "mb-0";
                                addressSpan.textContent = entry.address; // monthBestList의 주소 정보를 표시
                                addressDiv.appendChild(addressSpan);

                                // 리뷰 온도를 표시하는 열 추가
                                var reviewProgress = document.createElement("div");
                                reviewProgress.className = "progress";
                                reviewProgress.style.marginTop = "10px";

                                var reviewProgressBar = document.createElement("div");
                                reviewProgressBar.className = "progress-bar";
                                reviewProgressBar.role = "progressbar";
                                reviewProgressBar.style.width = entry.avg_emo_result + "%"; //리뷰 온도 값 사용

                                reviewProgress.appendChild(reviewProgressBar);

                                var reviewText1 = document.createElement("span");
                                reviewText1.className = "text1";
                                reviewText1.textContent = "리뷰 온도 " + entry.avg_emo_result + "°C";
                                reviewText1.style.float = "left";
                                reviewText1.style.marginTop = "15px";

                                // 찜 수를 표시하는 열 추가
                                var jjimText2 = document.createElement("span");
                                jjimText2.className = "text2";
                                jjimText2.textContent = "이번달 저장수 " + entry.jjim;
                                jjimText2.style.float = "right";
                                jjimText2.style.marginTop = "15px";

                                cardContent.appendChild(rankBadge);
                                cardContent.appendChild(nameH6);
                                cardContent.appendChild(addressDiv);
                                cardContent.appendChild(reviewProgress);
                                cardContent.appendChild(reviewText1);
                                cardContent.appendChild(jjimText2);

                                card.appendChild(cardContent);

                                // 결과 테이블에 카드 추가
                                resultDiv.appendChild(card);

                                // 마지막 리스트에는 수평선을 추가하지 않음
                                if (i < response.monthBestList.length - 1) {
                                    var horizontalLine = document.createElement("hr");
                                    resultDiv.appendChild(horizontalLine);
                                }

                               // 모달창 생성
                              var modal = document.createElement('div');
                              modal.id = 'myModal'+ (i + 1);  // 각각 다른 ID 부여
                              modal.className = 'modal fade';
                              modal.tabIndex = '-1';

                              var dialog = document.createElement('div');
                              dialog.className = 'modal-dialog';

                              var content = document.createElement('div');
                              content.className = 'modal-content';

                              // 모달 헤더 생성
                              var header= document.createElement('div');
                              header.className= 'modal-header';

                              var title= document.createElement('h5');
                              title.className= 'modal-title';
                              title.textContent='Review';

                              var closeButtonHeader= document.createElement('button');
                              closeButtonHeader.type='button';
                              closeButtonHeader.className='btn-close';
                              closeButtonHeader.setAttribute("data-bs-dismiss", "modal");

                              header.appendChild(title);
                              header.appendChild(closeButtonHeader);

                              content.appendChild(header);

                              //모달 바디 생성
                              var body = document.createElement('div');
                              body.className = 'modal-body';

                              content.appendChild(body);
                              dialog.appendChild(content);
                              modal.appendChild(dialog);

                              // 모달창을 body에 추가
                              document.body.appendChild(modal);

                              //모달 푸터 생성
                              var footer=document.createElement('div');
                              footer.className='modal-footer';

                              let closeModalButton=document.createElement('button')
                              closeModalButton.id="closeModal";
                              closeModalButton.type="button";
                              closeModalButton.className="btn btn-danger";
                              closeModalButton.setAttribute("data-bs-dismiss", "modal");
                              closeModalButton.textContent = 'Close';

                              footer.appendChild(closeModalButton);
                              content.appendChild(footer);

                               nameH6.addEventListener("click", (function(i) {
                                   return function () {
                                       var hotplace_no = this.getAttribute('data-hotplace-no'); // hotplace_no정보 받음.
                                       var url = '/review/' + hotplace_no;

                                       var xhr = new XMLHttpRequest();
                                       xhr.open('GET', url, true);
                                       xhr.setRequestHeader('Content-Type', 'application/json');

                                       xhr.onreadystatechange = function () {
                                           if (xhr.readyState === 4 && xhr.status === 200) {
                                               var data = JSON.parse(xhr.responseText);

                                               console.log(data);

                                               // AJAX 요청이 성공적으로 완료된 후에만 모달 창 열기
                                               let reviewModalElmId='myModal'+(i+1);
                                               let reviewModalElm=document.getElementById(reviewModalElmId);

                                               if (reviewModalElm != null) {
                                                   let modalBody = reviewModalElm.querySelector('.modal-body');
                                                   modalBody.innerHTML = '';

                                                   data.forEach(function(reviewList) {
                                                       let reviewBody = document.createElement('div');
                                                       reviewBody.classList.add('modalReviewCard', 'my-2');
                                                       let reviewTextElement = document.createElement('p');
                                                       reviewTextElement.textContent = reviewList.review;

                                                       let emoResultElement = document.createElement('p');
                                                       emoResultElement.style.textAlign='right';
                                                       emoResultElement.textContent='리뷰 온도 '+reviewList.emo_result+'°C';
                                                       emoResultElement.classList.add('text1');

                                                       reviewBody.appendChild(reviewTextElement);
                                                       reviewBody.appendChild(emoResultElement);

                                                      modalBody.appendChild(reviewBody);
                                                   });

                                                  var modalInstance=new bootstrap.Modal(reviewModalElm);
                                                  modalInstance.show();
                                              }
                                           } else if (xhr.readyState === 4) {
                                              alert('요청 실패: ' + xhr.status);
                                           }
                                      };

                                      xhr.send();
                                  };
                               })(i)); // IIFE 종료
                            }
                        }
                    } catch (error) {
                        console.error("JSON 파싱 오류:", error);
                    }
                } else {
                    console.log("Error:", xhr.statusText);
                    alert("Error" + xhr.statusText);
                }
            };

            xhr.onerror = function () {
                console.log("Network Error");
                alert("Network Error");
            };
            xhr.send("sido=" + selectedSido + "&gugun=" + selectedGugun + "&hotplace_cate_no=" + selectedCategory); //매개변수를 전달
        }
    });
});