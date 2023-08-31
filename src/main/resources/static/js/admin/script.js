var Labels = [];
var Data = [];

for (var i = 11; i >= 0; i--) {
    Data.push(activeUserListData[i]['0']); // activeUserMap
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
                        label: "# of Votes",
                        data: Data,
                        backgroundColor: [
                            "rgba(255, 99, 132, 0.2)",
                            "rgba(54, 162, 235, 0.2)",
                            "rgba(255, 206, 86, 0.2)",
                            "rgba(75, 192, 192, 0.2)",
                            "rgba(153, 102, 255, 0.2)",
                            "rgba(255, 159, 64, 0.2)",
                        ],
                        borderColor: [
                            "rgba(255, 99, 132, 1)",
                            "rgba(54, 162, 235, 1)",
                            "rgba(255, 206, 86, 1)",
                            "rgba(75, 192, 192, 1)",
                            "rgba(153, 102, 255, 1)",
                            "rgba(255, 159, 64, 1)",
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
