$(document).ready(function () {
    $("#submitBtn").click(function () {
        let selectedFruit = $("input[name='fruit']:checked").val();

        if (!selectedFruit) {
            $("#resultBox").html("❌ Please select a fruit.");
            return;
        }

        // Fetch data from fresh.json
        $.ajax({
            url: "fresh.json", // Ensure fresh.json is in the same directory
            type: "GET",
            dataType: "json",
            beforeSend: function () {
                $("#resultBox").html("🔄 Fetching details...");
            },
            success: function (data) {
                let fruitData = data.fruits.find(f => f.name.toLowerCase() === selectedFruit);

                if (fruitData) {
                    let details = `<h3>${fruitData.name}</h3>
                                   <p><strong>Origin:</strong> ${fruitData.origin}</p>
                                   <h4>Processing Steps:</h4>
                                   <ul>`;

                    fruitData.processing.forEach(step => {
                        details += `<li>${step}</li>`;
                    });

                    details += `</ul>`;
                    $("#resultBox").html(details);
                } else {
                    $("#resultBox").html("⚠️ No details found for the selected fruit.");
                }
            },
            error: function () {
                $("#resultBox").html("❌ Error loading data. Check fresh.json.");
            }
        });
    });
});
