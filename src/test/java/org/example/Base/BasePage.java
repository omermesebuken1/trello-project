package org.example.Base;
import java.util.List;
import java.util.Random;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class BasePage
{
    String key = "147e06210af6b6483bff35491be20c81";
    String token = "ATTAe29b9bc98faa7a0f23b432d8b38f05241b7063999a0efb66386b0eefb3fce8efE14A86B3";

    public void init(){
        RestAssured.baseURI = "https://api.trello.com/1";
    }

    public Response createBoard(String name, String path){

        return given().header("Accept-Encoding", "gzip,deflate")
                .contentType("application/json")
                .queryParam("name", name)
                .queryParam("key",key)
                .queryParam("token",token)
                .log().all()
                .when()
                .post(path);
    }
    public Response createItem(String name,String path,String paramName,String paramValue){
        return given().header("Accept-Encoding", "gzip,deflate")
                .contentType("application/json")
                .queryParam("name", name)
                .queryParam(paramName, paramValue)
                .queryParam("key",key)
                .queryParam("token",token)
                .log().all()
                .when()
                .post(path);
    }
    public void updateItem(String path,String pathParam, String pathParamValue, String updateParam, String newValue){
        given().header("Accept-Encoding", "gzip,deflate")
                .contentType("application/json")
                .pathParam(pathParam, pathParamValue)
                .queryParam(updateParam, newValue)
                .queryParam("key",key)
                .queryParam("token",token)
                .log().all()
                .when()
                .put(path)
                .then()
                .statusCode(200);
    }
    public void deleteItem (String path,String pathParam,String pathParamValue){
        given().header("Accept-Encoding", "gzip,deflate")
                .pathParam(pathParam, pathParamValue)
                .queryParam("key",key)
                .queryParam("token",token)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .delete(path)
                .then()
                .statusCode(200);
    }
    public Response getItem(String path,String pathParam, String pathParamValue){
        return given().header("Accept-Encoding", "gzip,deflate")
                .contentType("application/json")
                .pathParam(pathParam, pathParamValue)
                .queryParam("key",key)
                .queryParam("token",token)
                .log().all()
                .when()
                .get(path);
    }
    public void assertName(Response response,String name){
        response.then()
                .statusCode(200)
                .body("name", equalTo(name));
    }
    public String extractParameter(Response response,String parameter){

        List<String> itemList = response.then()
                .extract().jsonPath().get(parameter);


        return itemList.get(0);
    }
    public String getRandomCard(Response response,String parameter){

        List<String> itemList = response.then()
                .extract().jsonPath().get(parameter);

        Random rand = new Random();
        int randomValue = rand.nextInt() % 2;
        return itemList.get(randomValue);
    }





}
