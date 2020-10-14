# ACompany
 ACompany test task
 
 A brief report about findings and unusual behaviour of API:
 
 1. '/v1/car-types/built-dates' response body doesn't contain fields: page, pageSize, totalPageCount
 2. '/v1/car-types/main-types' and '/v1/car-types/built-dates' return empty wkda parameter instead of 404 when call with wrong parameters
