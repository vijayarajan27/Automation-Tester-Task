/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 57.7897722074833, "KoPercent": 42.2102277925167};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.5734150580916659, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.6288306451612903, 500, 1500, "GET /products/categories"], "isController": false}, {"data": [0.6265593561368209, 500, 1500, "GET /products"], "isController": false}, {"data": [0.6362721417069244, 500, 1500, "GET /products/1"], "isController": false}, {"data": [0.0, 500, 1500, "\"GET Products API\" "], "isController": false}, {"data": [0.6329572925060435, 500, 1500, "GET /users"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 10931, 4614, 42.2102277925167, 588.6095508187738, 0, 469591, 209.0, 325.0, 366.0, 638.7200000000012, 16.278650708198, 52.859926034225225, 1.367920184015321], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET /products/categories", 2480, 916, 36.935483870967744, 770.8149193548386, 0, 469591, 205.0, 273.0, 298.9499999999998, 346.19000000000005, 3.722119747196028, 4.768265331793956, 0.31404799095584934], "isController": false}, {"data": ["GET /products", 2485, 891, 35.85513078470825, 474.1018108651914, 0, 469591, 208.0, 309.0, 397.39999999999964, 988.239999999998, 3.702776560902852, 29.20082637526113, 0.29225370016897173], "isController": false}, {"data": ["GET /products/1", 2484, 899, 36.191626409017715, 583.5559581320447, 0, 469542, 205.0, 277.5, 301.0, 375.75000000000045, 3.714098172406523, 5.425408224656588, 0.29623820467312495], "isController": false}, {"data": ["\"GET Products API\" ", 1000, 1000, 100.0, 255.3910000000003, 197, 1860, 228.0, 309.0, 357.0, 741.97, 16.413892718797193, 23.25217048084499, 2.067765781957849], "isController": false}, {"data": ["GET /users", 2482, 908, 36.583400483481064, 660.5088638195009, 0, 469591, 304.0, 374.0, 397.0, 439.1700000000001, 3.717778380264918, 11.497390489276562, 0.2831989267948167], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["Non HTTP response code: java.net.NoRouteToHostException/Non HTTP response message: No route to host: connect", 65, 1.4087559601213697, 0.5946390998078859], "isController": false}, {"data": ["Non HTTP response code: java.net.SocketException/Non HTTP response message: An established connection was aborted by the software in your host machine", 8, 0.17338534893801474, 0.07318635074558595], "isController": false}, {"data": ["Non HTTP response code: java.net.UnknownHostException/Non HTTP response message: No such host is known (fakestoreapi.com)", 11, 0.23840485478977028, 0.10063123227518068], "isController": false}, {"data": ["404/Not Found", 1000, 21.673168617251843, 9.148293843198244], "isController": false}, {"data": ["Non HTTP response code: java.net.UnknownHostException/Non HTTP response message: fakestoreapi.com", 3497, 75.79107065452969, 31.991583569664257], "isController": false}, {"data": ["Non HTTP response code: java.net.SocketException/Non HTTP response message: A connection attempt failed because the connected party did not properly respond after a period of time, or established connection failed because connected host has failed to respond", 33, 0.7152145643693107, 0.301893696825542], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 10931, 4614, "Non HTTP response code: java.net.UnknownHostException/Non HTTP response message: fakestoreapi.com", 3497, "404/Not Found", 1000, "Non HTTP response code: java.net.NoRouteToHostException/Non HTTP response message: No route to host: connect", 65, "Non HTTP response code: java.net.SocketException/Non HTTP response message: A connection attempt failed because the connected party did not properly respond after a period of time, or established connection failed because connected host has failed to respond", 33, "Non HTTP response code: java.net.UnknownHostException/Non HTTP response message: No such host is known (fakestoreapi.com)", 11], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["GET /products/categories", 2480, 916, "Non HTTP response code: java.net.UnknownHostException/Non HTTP response message: fakestoreapi.com", 894, "Non HTTP response code: java.net.NoRouteToHostException/Non HTTP response message: No route to host: connect", 10, "Non HTTP response code: java.net.SocketException/Non HTTP response message: A connection attempt failed because the connected party did not properly respond after a period of time, or established connection failed because connected host has failed to respond", 7, "Non HTTP response code: java.net.SocketException/Non HTTP response message: An established connection was aborted by the software in your host machine", 3, "Non HTTP response code: java.net.UnknownHostException/Non HTTP response message: No such host is known (fakestoreapi.com)", 2], "isController": false}, {"data": ["GET /products", 2485, 891, "Non HTTP response code: java.net.UnknownHostException/Non HTTP response message: fakestoreapi.com", 850, "Non HTTP response code: java.net.NoRouteToHostException/Non HTTP response message: No route to host: connect", 25, "Non HTTP response code: java.net.SocketException/Non HTTP response message: A connection attempt failed because the connected party did not properly respond after a period of time, or established connection failed because connected host has failed to respond", 10, "Non HTTP response code: java.net.UnknownHostException/Non HTTP response message: No such host is known (fakestoreapi.com)", 5, "Non HTTP response code: java.net.SocketException/Non HTTP response message: An established connection was aborted by the software in your host machine", 1], "isController": false}, {"data": ["GET /products/1", 2484, 899, "Non HTTP response code: java.net.UnknownHostException/Non HTTP response message: fakestoreapi.com", 872, "Non HTTP response code: java.net.NoRouteToHostException/Non HTTP response message: No route to host: connect", 17, "Non HTTP response code: java.net.SocketException/Non HTTP response message: A connection attempt failed because the connected party did not properly respond after a period of time, or established connection failed because connected host has failed to respond", 7, "Non HTTP response code: java.net.SocketException/Non HTTP response message: An established connection was aborted by the software in your host machine", 2, "Non HTTP response code: java.net.UnknownHostException/Non HTTP response message: No such host is known (fakestoreapi.com)", 1], "isController": false}, {"data": ["\"GET Products API\" ", 1000, 1000, "404/Not Found", 1000, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["GET /users", 2482, 908, "Non HTTP response code: java.net.UnknownHostException/Non HTTP response message: fakestoreapi.com", 881, "Non HTTP response code: java.net.NoRouteToHostException/Non HTTP response message: No route to host: connect", 13, "Non HTTP response code: java.net.SocketException/Non HTTP response message: A connection attempt failed because the connected party did not properly respond after a period of time, or established connection failed because connected host has failed to respond", 9, "Non HTTP response code: java.net.UnknownHostException/Non HTTP response message: No such host is known (fakestoreapi.com)", 3, "Non HTTP response code: java.net.SocketException/Non HTTP response message: An established connection was aborted by the software in your host machine", 2], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
