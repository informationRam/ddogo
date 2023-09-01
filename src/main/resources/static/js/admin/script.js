var Labels = [];
var Data = [];

for (var i = 11; i >= 0; i--) {
    Data.push(activeUserMapData[i+"key"]);
    Labels.push(monthMapData[i+"key"]+"월");
}

    var charts = document.querySelectorAll(".month");

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
                            "rgba(255, 99, 132, 0.2)"
                        ],
                        borderColor: [
                            "rgba(255, 99, 132, 1)"
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
