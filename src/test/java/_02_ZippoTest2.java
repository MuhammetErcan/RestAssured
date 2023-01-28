import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class _02_ZippoTest2 {

    // http://api.zippopotam.us/us/90210
    //  http://api.zippopotam.us
    //  /us/90210  path parameter

    @Test
    public void pathParamTest(){
          String country = "us";
          String zipCode = "90210";

        given()
              //  .pathParam("country", "us")
                .pathParam("country", country)
             //   .pathParam("zipCode", "90210")
                .pathParam("zipCode", zipCode)
                .log().uri()   // request URI yazdirir
                .when()
                .get("http://api.zippopotam.us"+ "/{country}/{zipCode}")

                .then()
                .statusCode(200)
                .body("places", hasSize(1))
                .log().body()
        ;
    }

    //   https://gorest.co.in/public-api/users?page=3
    // ?page=3  query paramater

    @Test
    public void queryParamaterTest(){
         int page = 4;
           given()
                   .param("page", page)
                   .log().uri()
                   .when()
                   .get("https://gorest.co.in/public-api/users")
                 //.get("https://gorest.co.in/public-api/users?page=1")     bu sekilde yazsak ta parametreden gelen page aldi
                   .then()
                   .log().body()
                   .statusCode(200)
                   .body("data[0].name", equalTo("Rev. Shrishti Pandey") )
                   .body("meta.pagination.page", equalTo(page) )
                   ;
    }



    @Test
    public void queryParamaterTestMulti(){
        HashMap<String,Object> params=new HashMap<>();
        params.put("page",5);
        params.put("id", "34343" );

        for (int page = 1; page <=10; page++) {
            given()
                  //  .param("page", page)
                  //  .queryParam("page", page)
                  //  .queryParam("id", "123")
                   .queryParams("page", page, "id", "12")
                    //.queryParams(params)
                    .log().uri()
                    .when()
                    .get("https://gorest.co.in/public-api/users")

                    .then()
                    .statusCode(200)
                    .body("meta.pagination.page", equalTo(page) )
            ;
        }
    }




}
