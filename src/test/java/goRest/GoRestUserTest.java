package goRest;

import goRest.PojoModel.User;
import groovy.util.ConfigObject;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GoRestUserTest {


    int userID;
//(enabled = false)
   @Test
    public void goRestGetUsers(){


       List<User> userList = given()

               .when()
               .get("https://gorest.co.in/public-api/users")

               .then()
             //  .log().body()
               .statusCode(200)
               .contentType(ContentType.JSON)
               .body("code", equalTo(200))
               .body("data", not(empty()))
               .extract().jsonPath().getList("data", User.class)

               ;

       System.out.println(userList);

       for (User n:userList) {
           System.out.println(n.getEmail());
       }


   }

    @Test
    public void createUser(){

    //Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb


      //  String name =  getRandomName();
      //  String email =  getRandomEmail();

        Map<String,String> user= new HashMap<>();
        user.put("name", getRandomName());
        user.put("gender","Male");
        user.put("email", getRandomEmail());
        user.put("status", "active");



     userID =  given()
               .header("Authorization", "Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb")
               .contentType(ContentType.JSON)
             //  .body("{\"name\":\""+getRandomName()+"\", \"gender\":\"male\", \"email\":\""+getRandomEmail()+"\", \"status\":\"active\"}")
               .body(user)
               .when()
               .post("https://gorest.co.in/public-api/users")
               .then()
               .log().body()
               .statusCode(200)
               .contentType(ContentType.JSON)
               .body("code", equalTo(201))
              .body("data.name", equalTo(user.get("name")))
               .extract().jsonPath().getInt("data.id")

               ;

        System.out.println(userID);




    }

    private  String getRandomEmail(){


       String userEmail= RandomStringUtils.randomAlphabetic(8).toLowerCase() + "@gmail.com";
        System.out.println(userEmail);
       return userEmail;
    }


    private  String getRandomName(){

        String name= RandomStringUtils.randomAlphabetic(5).toLowerCase();
        System.out.println(name);
        return name;
    }


    @Test(dependsOnMethods = "createUser")
    public void updateUserByID(){


       given()
               .header("Authorization", "Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb")
               .contentType(ContentType.JSON)
               .pathParam("userID",userID)
               .body("{\"name\":\"ömer\", \"gender\":\"male\", \"email\":\""+getRandomEmail()+"\", \"status\":\"active\"}")
               .log().uri()
               .when()
               .put("https://gorest.co.in/public-api/users/{userID}")
               .then()
               .statusCode(200)
               .contentType(ContentType.JSON)
               .body("code", equalTo(200))
               .body("data.name", equalTo("ömer"))
              // .log().body()

               ;


    }


    @Test(dependsOnMethods = "createUser", priority =  1)
    public void deleteUserByID(){


        given()
                .header("Authorization", "Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb")
                .pathParam("userID",userID)
                .log().uri()
                .when()
                .delete("https://gorest.co.in/public-api/users/{userID}")
                .then()
                .statusCode(200)
                .body("code", equalTo(204))
                .log().body()


        ;


    }

    @Test(dependsOnMethods = "deleteUserByID")
    public void deleteUserByINegativ(){


        given()
                .header("Authorization", "Bearer 6a72f07ad4685b1a298a2615c2a4683c5513b67a62991ac4f3e56fa1ebd113cb")
                .pathParam("userID",userID)
                .log().uri()
                .when()
                .delete("https://gorest.co.in/public-api/users/{userID}")
                .then()
                .statusCode(200)
                .body("code", equalTo(404))
                .body("data.message", equalTo("Resource not found"))
                .log().body()

        ;


    }



}
