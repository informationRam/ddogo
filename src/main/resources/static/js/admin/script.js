
var Labels = [1,2,3,4,5,6,7,8,9,10,11,12];
var Data = [];

// 1월부터 12월까지 순회
for (var month = 1; month <= 12; month++) {
    // activeUser 배열에서 해당 월의 데이터를 찾습니다.
    var found = activeUser.find(user => user.month === month);

    // 해당 월의 데이터가 있는 경우, activeUserCnt 값을 Data 배열에 추가
    // 없는 경우, 0을 Data 배열에 추가
    Data.push(found ? found.activeUserCnt : 0);
}

var LocalLabels = ["서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종", "경기", "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주"];
var LocalData = [];

// Convert the List to a dictionary for easy access
var localHotplaceDict = {};
for (var i = 0; i < localHotplaceCnt.length; i++) {
    localHotplaceDict[localHotplaceCnt[i].sido] = localHotplaceCnt[i].hotplaceCnt;
}

// Fill in the Data array
for (var j = 0; j < Labels.length; j++) {
    if (localHotplaceDict.hasOwnProperty(LocalLabels[j])) {
        LocalData.push(localHotplaceDict[LocalLabels[j]]);
    } else {
        LocalData.push(0);
    }
}

const charts = document.querySelectorAll(".month");
const Localcharts = document.querySelectorAll(".local");

charts.forEach(function (chart) {
        var ctx = chart.getContext("2d");
        var myChart = new Chart(ctx, {
            type: "bar",
            data: {
                labels: Labels,
                datasets: [
                    {
                        label: "명",
                        data: Data,
                        backgroundColor: [
                            "#FFD1DC"
                        ],
                        borderColor: [
                            "#FF88A9"
                        ],
                        borderWidth: 1,
                    },
                ],
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                    },
                },
            },
        });
    });

    Localcharts.forEach(function (chart) {
        var ctx = chart.getContext("2d");
        var myChart = new Chart(ctx, {
            type: "bar",
            data: {
                labels: LocalLabels,
                datasets: [
                    {
                        label: "곳",
                        data: LocalData,
                        backgroundColor: [
                            "#E3FCEC"
                        ],
                        borderColor: [
                            "#2E8B57"
                        ],
                        borderWidth: 1,
                    },
                ],
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                    },
                },
            },
        });
    });

    $(document).ready(function () {
        $(".data-table").each(function (_, table) {
            $(table).DataTable();
        });
    });