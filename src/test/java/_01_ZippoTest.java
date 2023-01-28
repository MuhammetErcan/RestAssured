import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class _01_ZippoTest {



    @Test
    public void test1() {

        given().when().then();

//                given(). //setup baslangis ayarlari
//
//                        when() // send aksiyon metod get put post delete
//
//
//
//                        .then()  // test
//
//                ;

    }

    //"http://api.zippopotam.us/us/90210"

    @Test
    public void statusCodeTest() {
        given()
                .when().get("http://api.zippopotam.us/us/90210")

                .then()
                .log().all()
                .statusCode(200)
        ;
    }

    @Test
    public void contentTypeTest(){
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .contentType("application/json")
                ;
    }

    @Test
    public void logTest(){
        given()
                .log().all()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().all()
                //log().body
                ;
    }

    @Test
    public void checkCountry(){
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .statusCode(200)
                .body("country",equalTo("United States"))
                .log().body()
                ;
    }



    @Test
    public void checkPlacesState(){
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .statusCode(200)
                .body("places[0].state", equalTo("California"))
                .log().body()
        ;
    }

    @Test
    public void checkPlaceName(){
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .statusCode(200)
                .body("places[0].'place name'", equalTo("Beverly Hills"))  // arasinda bosluk olan keylerde '' kullaniyoruz
                .log().body()
        ;
    }


    // places arrayinin size ni test edelim

    @Test
    public void checkPlacesArraySize(){
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .statusCode(200)
                .body("places", hasSize(1))
                .log().body()
        ;
    }

    @Test
    public void cokluBodyTest(){
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .body("places", hasSize(1))
                .body("places[0].'place name'", equalTo("Beverly Hills"))
                .body("places[0].state", equalTo("California"))
                .statusCode(200)
                ;
    }
}
