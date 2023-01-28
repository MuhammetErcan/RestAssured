import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

public class _03_ZippoTest3 {


         private ResponseSpecification responseSpecification ;

         private RequestSpecification requestSpecification;



        @BeforeClass
        public void setup(){

            baseURI = "http://api.zippopotam.us" ; // static rest assured

           responseSpecification = new ResponseSpecBuilder()
                   .expectStatusCode(200)
                   .expectContentType(ContentType.JSON)
                   .log(LogDetail.BODY)
                   .build();


            requestSpecification = new RequestSpecBuilder()
                    .log(LogDetail.BODY)
                    .setAccept(ContentType.JSON)
                    .build();


        }

   // baseURI = "http://api.zippopotam.us"
    // Environment variable
    @Test
    public void checkPlacesArrayBaseUri(){

        given()
                .log().uri()
                .when()
                .get("/us/90210")  // http ile baslamadigini gördugunde base uri kullanyior

                .then()
                .body("places", hasSize(1))
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
        ;
    }


    //yaptigimzi testlet ortak ise rsponse spesificcation

    @Test
    public void checkPlacesArraySIzeResponseSpesification(){

        given()
                .log().uri()
                .when()
                .get("/us/90210")

                .then()
                .body("places", hasSize(1))
                .spec(responseSpecification)
        ;
    }


    @Test
    public void checkPlacesArraySIzeRequestSpesifocation(){

        given()
                .spec(requestSpecification)
                .when()
                .get("/us/90210")

                .then()
                .body("places", hasSize(1))
                .spec(responseSpecification)
        ;

    }
}
