import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class _04_ExtractingResponse {

    @BeforeClass
    public void setup(){
        baseURI = "https://reqres.in" ; // static rest assured

    }

    // //    https://reqres.in/api/users

    // George alacagiz bir degiskene ataayip assrtion
    // Body icinde ki bir degeri disari cikartiyorum ve bir ataama yapiyorum

    @Test
    public void extractingJsonPath(){
        String extractValue =  given()
                .when()
                .get("/api/users")
                .then()
                .log().body()
                .extract().path("data[0].first_name")
                ;
        System.out.println("Alinan Deger : "+ extractValue);
        Assert.assertEquals(extractValue,"George");
    }


  // Liste olarak alacagiz

    @Test
    public void extractingJsonPathList(){
        List<String> list =  given()
                .when()
                .get("/api/users")

                .then()
                .extract().path("data.first_name")  // [] bir elemanin indexi verilmez datanin altindaki tüm first namleri liste olarak alir

                ;

        System.out.println(list);

        Assert.assertTrue(list.contains("Emma"));
    }
}
