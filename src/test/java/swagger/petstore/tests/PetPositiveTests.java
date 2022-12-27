package swagger.petstore.tests;

import org.junit.jupiter.api.*;
import swagger.petstore.objects.ResponseData;
import swagger.petstore.objects.pet.PetCategoryData;
import swagger.petstore.objects.pet.PetData;
import swagger.petstore.objects.pet.PetTag;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("pets_store")
public class PetPositiveTests extends BaseTest {

    private Long petId;
    private PetData createdPet;
    private PetData pet;

    @BeforeEach
    public void beforeTest(){
        installSpecificationForRequestAndResponse(200);
        pet = new PetData(0L,
                new PetCategoryData(0, "dog"),
                "Kelly",
                List.of("some_url1", "some_url2"),
                List.of(new PetTag(0, "pug")),
                "available");
        createdPet = given()
                .body(pet)
                .when()
                .post(PET_URL)
                .then()
                .extract().as(PetData.class);
        petId = createdPet.getId();
        System.out.println("Pet id: " + petId);
    }

    @AfterEach
    public void afterTest(){
         given()
        .when()
        .delete(PET_URL + "/" + petId)
        .then()
        .statusCode(200);
    }

    @Test
    public void createPetTest(){
        pet.setId(petId);
        assertNotNull(petId);
        assertEquals(pet, createdPet);
    }

    @Test
    public void updatePetWithBodyTest(){
        pet.setStatus("pending");
        PetData updatedPet = given()
                .body(pet)
                .when()
                .put(PET_URL)
                .then()
                .extract().as(PetData.class);
        assertEquals("pending", updatedPet.getStatus());
        assertEquals(pet, updatedPet);
    }

    @Test
    public void updatePetWithIdTest(){
        ResponseData responseData = given()
                .param("name", "Ben")
                .param("status", "sold")
                .when()
                .post(PET_URL + "/" + petId)
                .then()
                .extract().as(ResponseData.class);
        assertEquals(200, responseData.getCode());
        assertEquals("unknown", responseData.getType());
        assertEquals(Long.toString(petId), responseData.getMessage());
    }

    @Test
    public void findPetByStatusTest(){
        List<PetData> petsPending = given()
                .param("status", createdPet.getStatus())
                .when()
                .get(PET_URL + "/findByStatus")
                .then()
                .extract().body().jsonPath().getList("", PetData.class);
        assertTrue(petsPending.stream().allMatch(x-> x.getStatus().equals(createdPet.getStatus())));
    }

    @Test
    public void findPetByIdTest(){
        PetData getPetData = given()
                .when()
                .get(PET_URL + "/" + petId)
                .then()
                .extract().as(PetData.class);
        assertEquals(createdPet.getId(), getPetData.getId());
        assertEquals(createdPet, getPetData);
    }
}