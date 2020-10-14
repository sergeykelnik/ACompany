# Run tests with:
 mvn clean test
 
# Test report
/target/surefire-reports/emailable-report.html

 ---
 A brief report about findings and unusual behaviour of API rated in criticality:
 
 1. '/v1/car-types/manufacturer' extra key-value "20": "Abarth", appears for each 2nd call
 2. '/v1/car-types/built-dates' response body doesn't contain fields: page, pageSize, totalPageCount
 3. '/v1/car-types/main-types' and '/v1/car-types/built-dates' return 200 OK and empty wkda parameter instead of 404 Not Found when call it with wrong parameters
