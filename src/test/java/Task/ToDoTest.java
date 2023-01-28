package Task;

import Task.PojoClass.PojoToDo;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ToDoTest {


    @Test
    public void testTodo(){





        PojoToDo[] toDoArray = given()

                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().as(PojoToDo[].class)
                        ;


        System.out.println(Arrays.toString(toDoArray));


    }



    @Test
    public void testTodoLIst(){

        List<PojoToDo> toDoList = given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos")

                .then()
                .log().body()
                .statusCode(200)
                .extract().jsonPath().get()
                ;

        System.out.println(toDoList);

    }
      @Test
    public void testTodoList2(){

        List<PojoToDo> pojoToDoList = Arrays.asList( given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos")

                .then()
                .extract().as(PojoToDo[].class)
        )
                ;

          System.out.println(pojoToDoList);

      }




}
