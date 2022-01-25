# virtual-health
Test task

1) Create 4 random GET/POST request to the https://superhero.qa-test.csssr.com resource;
2) Make their validation;
3) Create bug reports if any bugs will be found;


Bugs found: 

    1. Field «phone» isn’t saved by POST https://superhero.qa-test.csssr.com/superheroes (Priority - Critical)
        a) Send request POST by the url https://superhero.qa-test.csssr.com/superheroes with body of JSON according model https://superhero.qa-test.csssr.com/swagger-ui.html#/;

AR: Response with code 200, superhero created without data in field phone;
ER: Response with code 400 and message about wrong field;

    2. Field “id” could be sent as String value (Priority - Critical)
        a) Send request POST by the url https://superhero.qa-test.csssr.com/superheroes with body of JSON according model https://superhero.qa-test.csssr.com/swagger-ui.html#/;

AR: Response with code 200, superhero created with “id” generated;
ER: Response with code 400 and message about wrong field;

    3. Field “gender” could be send and saved with value different from “M” and “F”. (Priority - Critical)
        a) Send request POST by the url https://superhero.qa-test.csssr.com/superheroes with body of JSON according model https://superhero.qa-test.csssr.com/swagger-ui.html#/;

AR: Response with code 200, superhero created with “gender” as we sent;
ER: Response with code 400 and message about wrong field;

    4. Request GET by the URL  https://superhero.qa-test.csssr.com/superheroes returns correct list of objects only on the (%2 = 0, multiple of 2) request; (Priority - Critical)
        a) Send request GET  by the url https://superhero.qa-test.csssr.com/superheroes (returned list of values with 286 items);
        b) Send this request again

AR: On the second attempt API returns actual list of objects;
ER: Permanent response of correct list of values;

    5. Request GET by the URL  https://superhero.qa-test.csssr.com/superheroes/{ID} returns value only on the (%2 = 0, multiple of 2) request; (Priority - Critical)
        a) Send request GET  by the url https://superhero.qa-test.csssr.com/superheroes/{ID} (response that object isn’t found);
        b) Send request again;

AR: On the second attempt response with correct data;
ER: Permanent response with correct data;

    6. Request GET by the URL  https://superhero.qa-test.csssr.com/superheroes/{ID} with ID non-exist gives incorrect error code; (Priority - Normal)
        a) Send request GET  by the url https://superhero.qa-test.csssr.com/superheroes/{ID} with ID non-exist;

AR: ErrorCode is 400;
ER: ErrorCode is 404;

    7. Request PUT by the URL  https://superhero.qa-test.csssr.com/superheroes/{ID} returns value only on the (%2 = 0, multiple of 2) request; (Priority - Critical)
        a) Send request PUT  by the url https://superhero.qa-test.csssr.com/superheroes/{ID} (response that object isn’t found);
        b) Send request again;

AR: On the second attempt response with correct data;
ER: Permanent response with correct data;

    8. Field “gender” could be send and saved with value different from “M” and “F”. (Priority - Critical)
        a) Send request PUT by the url  https://superhero.qa-test.csssr.com/superheroes/{ID} with body of JSON according model https://superhero.qa-test.csssr.com/swagger-ui.html#/;

AR: Response with code 200, superhero updated with “gender” as we sent;
ER: Response with code 400 and message about wrong field;

    9. Field «phone» isn’t saved by PUT https://superhero.qa-test.csssr.com/superheroes/{ID} (Priority - Critical)
        a) Send request POST by the url https://superhero.qa-test.csssr.com/superheroes{ID} with body of JSON according model https://superhero.qa-test.csssr.com/swagger-ui.html#/;

AR: Response with code 200, superhero updated without data in field phone;
ER: Response with code 400 and message about wrong field;

    10. Fix errorCode by request without mandatory field POST by the url.  https://superhero.qa-test.csssr.com/superheroes with body of JSON according model https://superhero.qa-test.csssr.com/swagger-ui.html#/; (Priority - Normal)
        a) Send request POST without mandatory field “city”

AR: Error response with code 403;
ER: Error response with code 400;

    11.  Object isn’t deleted by request DELETE https://superhero.qa-test.csssr.com/superheroes/{ID}. (Priority - Critical)
        a) Send request DELETE https://superhero.qa-test.csssr.com/superheroes/{ID}
        b) Check object exist by request GET  by the url https://superhero.qa-test.csssr.com/superheroes/{ID}

AR: Object still exist;
ER: Object deleted;

    12. Request DELETE https://superhero.qa-test.csssr.com/superheroes/{ID} with non-exist ID doesn’t give correct errorCode; (Priority - Normal)
        a) Send  DELETE https://superhero.qa-test.csssr.com/superheroes/{ID} with non-exist ID;

AR: ErrorCode is 400;
ER: ErrorCode is 404;
