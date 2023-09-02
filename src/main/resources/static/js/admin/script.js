var Labels = [];
var Data = [];

for (var i = 11; i >= 0; i--) {
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
