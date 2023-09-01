var Labels = [];
var Data = [];

var YLabels = [];
var YData = [];

for (var i = 11; i >= 0; i--) {
    Data.push(activeUserMapData[i+"key"]);
    Labels.push(monthMapData[i+"key"]+"월");
    YData.push(activeUserYMap[i+"key"]);
    YLabels.push(yearMap[i+"key"]+"년");
}

    var charts = document.querySelectorAll(".month");
    var chartY = document.querySelectorAll(".year");

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
                            "#FF8CA1"
                        ],
                        borderColor: [
                            "#FF0841"
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

    chartY.forEach(function (chart) {
        var ctx = chart.getContext("2d");
        var myChart = new Chart(ctx, {
            type: "bar",
            data: {
                labels: YLabels,
                datasets: [
                    {
                        label: "명",
                        data: YData,
                        backgroundColor: [
                            "#FF8CA1"
                        ],
                        borderColor: [
                            "#FF0841"
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
