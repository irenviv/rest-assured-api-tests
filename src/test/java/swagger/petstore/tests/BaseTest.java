package swagger.petstore.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import swagger.petstore.helpers.Specifications;

public class BaseTest {

    public static final String BASE_URL = "https://petstore.swagger.io/v2";
    public static final String PET_URL = "/pet";
    public static final String USER_URL = "/user";
    public static final String USER_LOGIN_URL = "/login";
    public static final String USER_LOGOUT_URL = "/logout";
    public static final String STORE_ORDER_URL = "/store/order";
    public static final String STORE_PET_INVENTORY_URL = "store/inventory";

    public void installSpecificationForRequestAndResponse(int statusCode){
        RestAssured.requestSpecification = new RequestSpecBuilder() .log(LogDetail.ALL).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().log(LogDetail.ALL).build();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        Specifications.installSpecification(Specifications.requestSpec(BASE_URL),
                Specifications.responseSpec(statusCode));
    }

}
