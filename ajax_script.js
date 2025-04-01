$(document).ready(function () {
    $("#submitBtn").click(function () {
        let selectedOption = $("input[name='option']:checked").val();

        if (!selectedOption) {
            alert("Please select an option.");
            return;
        }

        $.getJSON("data.json", function (data) {
            if (data[selectedOption]) {
                $("#resultBox").html("<strong>" + data[selectedOption] + "</strong>").fadeIn();
            } else {
                $("#resultBox").html("<span style='color: red;'>No data found!</span>");
            }
        }).fail(function () {
            $("#resultBox").html("<span style='color: red;'>Error loading data.</span>");
        });
    });
});
