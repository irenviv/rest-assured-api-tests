package swagger.petstore.tests;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import swagger.petstore.objects.ResponseData;
import swagger.petstore.objects.store.PetOrderData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@Tag("pets_store")
public class PetsStorePositiveTests extends BaseTest {

    private Long orderId;
    private PetOrderData petOrder;
    private PetOrderData createdOrder;
    private ResponseData responseData;

    @BeforeEach
    public void beforeTest(){
        installSpecificationForRequestAndResponse(200);
        petOrder = new PetOrderData(0L,
                12345L,
                1,
                "2022-11-30T11:19:14.552Z",
                "available",
                true);
        createdOrder = given()
                .body(petOrder)
                .when()
                .post(STORE_ORDER_URL)
                .then()
                .extract().as(PetOrderData.class);
        orderId = createdOrder.getId();
        System.out.println("Pet purchase order id: " + createdOrder.getPetId());
        System.out.println("Pet order date: " + petOrder.getShipDate());
        System.out.println("Created pet order date: " + createdOrder.getShipDate());
    }

    private void deletePetOrderById(){
        responseData = given()
            .when()
            .delete(STORE_ORDER_URL + "/" + orderId)
            .then()
            .assertThat()
            .statusCode(200)
            .extract().as(ResponseData.class);
    }

    @Test
    public void placeAnOrderForPurchasingPetTest(){
        petOrder.setId(orderId);
        assertNotNull(orderId);
        assertThat(createdOrder, samePropertyValuesAs(petOrder));

        deletePetOrderById();
    }

    @Test
    public void findPurchaseOrderByIdTest(){
        PetOrderData getOrderData = given()
                .when()
                .get(STORE_ORDER_URL + "/" + orderId)
                .then()
                .extract().as(PetOrderData.class);
        assertEquals(createdOrder.getId(), getOrderData.getId());
        assertThat(getOrderData, samePropertyValuesAs(createdOrder));

        deletePetOrderById();
    }

    @Test
    public void deletePurchaseOrderByIdTest(){
        deletePetOrderById();
        assertEquals(200, responseData.getCode());
        assertEquals("unknown", responseData.getType());
        assertEquals(Long.toString(orderId), responseData.getMessage());

        //verify order is deleted
        installSpecificationForRequestAndResponse(404);
        ResponseData getResponseData = given()
                .when()
                .get(STORE_ORDER_URL + "/" + orderId)
                .then()
                .extract().as(ResponseData.class);
        assertEquals(1, getResponseData.getCode());
        assertEquals("error", getResponseData.getType());
        assertEquals("Order not found", getResponseData.getMessage());
    }

    @Test
    public void getPetInventoriesByStatus(){
        String petStatus = petOrder.getStatus();
        JsonPath responseData = given()
                .when()
                .get(STORE_PET_INVENTORY_URL)
                .then()
                .extract().jsonPath();
        int petQuantityWithStatus = responseData.get(petStatus);
        System.out.println("Pets quantity with status " + petStatus + " - " + petQuantityWithStatus);
        assertTrue(petQuantityWithStatus >= 1,
                "There is no pet with status " + petStatus + ", but should be");
        deletePetOrderById();
    }
}
