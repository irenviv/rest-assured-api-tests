package swagger.petstore.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import swagger.petstore.objects.ResponseData;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("pets_store")
public class PetNegativeTests extends BaseTest {

    @Test
    public void createPetWithoutBodyTest(){
        installSpecificationForRequestAndResponse(405);
        ResponseData error = given()
                .when()
                .post(PET_URL)
                .then()
                .extract().as(ResponseData.class);
        assertEquals(405, error.getCode());
        assertEquals("unknown", error.getType());
        assertNotNull(error.getMessage());
    }

    //test fail, incorrect response
    @Test
    public void updatePetWithWrongIdTest(){
        installSpecificationForRequestAndResponse(400);
        var pet = """
                {
                  "id": 999J,
                  "category": {
                    "id": 0,
                    "name": "doggie"
                  },
                  "name": "Ben",
                  "photoUrls": [
                    "string"
                  ],
                  "tags": [
                    {
                      "id": 0,
                      "name": "pug"
                    }
                  ],
                  "status": "available"
                }
                """;
        ResponseData error = given()
                .body(pet)
                .when()
                .put(PET_URL)
                .then()
                .extract().as(ResponseData.class);
        assertEquals(400, error.getCode());
        assertEquals("unknown", error.getType());
        assertEquals("Invalid ID supplied", error.getMessage());
    }

    @Test
    public void petNotFoundWhenSearchByIdTest(){
        installSpecificationForRequestAndResponse(404);
        ResponseData error = given()
                .when()
                .get(PET_URL + "/0")
                .then()
                .extract().as(ResponseData.class);
        assertEquals(1, error.getCode());
        assertEquals("error", error.getType());
        assertEquals("Pet not found", error.getMessage());
    }

    @Test
    public void deletePetWithWrongId(){
        installSpecificationForRequestAndResponse(404);
        given()
                .when()
                .delete(PET_URL + "/0")
                .then()
                .assertThat()
                .statusCode(404);
    }
}