# ACompany
 ACompany test task
 
 A brief report about findings and unusual behaviour of API rated in criticality:
 
 1. '/v1/car-types/built-dates' response body doesn't contain fields: page, pageSize, totalPageCount
 2. '/v1/car-types/manufacturer' has and extra key-value "20": "Abarth", but "020": "Abarth" already exist
 3. '/v1/car-types/main-types' and '/v1/car-types/built-dates' return empty wkda parameter instead of 404 NotFound when call it with wrong parameters
