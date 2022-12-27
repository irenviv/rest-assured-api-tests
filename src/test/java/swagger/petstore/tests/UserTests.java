package swagger.petstore.tests;

import org.junit.jupiter.api.*;
import swagger.petstore.objects.ResponseData;
import swagger.petstore.objects.user.UserData;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@Tag("pets_store")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTests extends BaseTest {

    private final UserData user = new UserData(0L,"user_user","user_firstname","user_lastname","user_user@gmail.com", "12345","123-123-123",0);
    private final String userName = user.getUsername();
    private final String updatedUsername = "updated_user";

    @BeforeEach
    public void beforeTest(){
        installSpecificationForRequestAndResponse(200);
    }

    @Test
    @Order(1)
    public void createUserTest() {
        ResponseData userResponse = given()
                    .body(user)
                .when()
                    .post(USER_URL)
                .then()
                    .extract().as(ResponseData.class);
        assertEquals(200, userResponse.getCode());
        assertEquals("unknown", userResponse.getType());
        assertNotNull(userResponse.getMessage());

        //verify user is created
        UserData createdUser = given()
                .when()
                .get(USER_URL + "/" + userName)
                .then()
                .extract().as(UserData.class);
        user.setId(Long.valueOf(userResponse.getMessage()));
        assertThat(createdUser, samePropertyValuesAs(user));
    }

    @Test
    @Order(2)
    public void updateUserTest(){
        user.setUsername(updatedUsername);
        ResponseData userResponse = given()
                .body(user)
                .when()
                .put(USER_URL + "/" + userName)
                .then()
                .extract().as(ResponseData.class);
        assertEquals(200, userResponse.getCode());
        assertEquals("unknown", userResponse.getType());
        assertNotNull(userResponse.getMessage());

        //verify user is updated
        UserData updatedUser = given()
                .when()
                .get(USER_URL + "/" + user.getUsername())
                .then()
                .extract().as(UserData.class);
        user.setId(Long.valueOf(userResponse.getMessage()));
        assertThat(updatedUser, samePropertyValuesAs(user));
    }

    @Test
    @Order(3)
    public void deleteUserTest() {
        String userName = user.getUsername();
        ResponseData userResponse = given()
                .when()
                .delete(USER_URL + "/" + userName)
                .then()
                .extract().as(ResponseData.class);
        assertEquals(200, userResponse.getCode());
        assertEquals("unknown", userResponse.getType());
        assertNotNull(userName);

        //verify user deleted with get request
        ResponseData deletedUser  = given()
                .when()
                .get(USER_URL + "/" + userName)
                .then()
                .assertThat()
                .statusCode(404)
                .extract().as(ResponseData.class);
        assertEquals(1, deletedUser.getCode());
        assertEquals("error", deletedUser.getType());
        assertEquals("User not found", deletedUser.getMessage());
    }

    @Test
    @Order(3)
    public void deleteUpdatedUserTest() {
        ResponseData userResponse = given()
                .when()
                .delete(USER_URL + "/" + updatedUsername)
                .then()
                .extract().as(ResponseData.class);
        assertEquals(200, userResponse.getCode());
        assertEquals("unknown", userResponse.getType());
        assertNotNull(updatedUsername);
    }

    @Test
    @Order(4)
    public void userLoginTest(){
        ResponseData userResponseData = given()
                    .param("username", "1user1")
                    .param("password", "123456")
                .when()
                    .get(USER_URL + USER_LOGIN_URL)
                .then()
                    .extract().as(ResponseData.class);
        assertEquals(200, userResponseData.getCode());
        assertEquals("unknown", userResponseData.getType());
        assertTrue(userResponseData.getMessage().contains("logged in user session:"));
    }

    @Test
    @Order(5)
    public void userLogoutTest(){
        ResponseData userResponseData = given()
                .when()
                    .get(USER_URL + USER_LOGOUT_URL)
                .then()
                    .extract().as(ResponseData.class);
        assertEquals(200, userResponseData.getCode());
        assertEquals("unknown", userResponseData.getType());
        assertEquals("ok", userResponseData.getMessage());
    }
}
