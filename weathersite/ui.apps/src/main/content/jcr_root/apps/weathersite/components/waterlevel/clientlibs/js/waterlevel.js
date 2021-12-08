anychart.onDocumentReady(function() {

        // set the data
        var data = {
            header: ["Name", "Death toll"],
            rows: [
              ["San-Francisco (1906)", 1500],
              ["Messina (1908)", 87000],
              ["Ashgabat (1948)", 175000],
              ["Chile (1960)", 10000],
              ["Tian Shan (1976)", 242000],
              ["Armenia (1988)", 25000],
              ["Iran (1990)", 50000]
        ]};

        // create the chart
        var chart = anychart.column();

        // add the data
        chart.data(data);

        // set the chart title
        chart.title("The Ground water level for the particular city ");

        // draw
        chart.container("containers");
        chart.draw();
      });