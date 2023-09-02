
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
const charts = document.querySelectorAll(".month");

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
                            "#E5E5E5"
                        ],
                        borderColor: [
                            "#B0B0B0"
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