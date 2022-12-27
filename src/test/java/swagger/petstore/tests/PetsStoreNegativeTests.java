package swagger.petstore.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import swagger.petstore.objects.ResponseData;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("pets_store")
public class PetsStoreNegativeTests extends BaseTest {

    //failed, inconsistent with swagger doc (wrong response message)
    @Test
    public void placeInvalidPetOrderIdTest(){
        installSpecificationForRequestAndResponse(400);
        String petOrder = """
                {
                  "id": 0z,
                  "petId": 12434,
                  "quantity": 1,
                  "shipDate": "2022-11-30T11:19:14.552Z",
                  "status": "available",
                  "complete": true
                }
                """;
        ResponseData error = given()
                .body(petOrder)
                .when()
                .post(STORE_ORDER_URL)
                .then()
                .extract().as(ResponseData.class);
        assertEquals(400, error.getCode());
        assertEquals("unknown", error.getType());
        assertEquals("Invalid Order", error.getMessage());
    }

    @Test
    public void deletePurchaseOrderByInvalidIdTest(){
        installSpecificationForRequestAndResponse(404);
        ResponseData error = given()
                .when()
                .delete(STORE_ORDER_URL + "/1")
                .then()
                .extract().as(ResponseData.class);
        assertEquals(404, error.getCode());
        assertEquals("unknown", error.getType());
        assertEquals("Order Not Found", error.getMessage());
    }
}
