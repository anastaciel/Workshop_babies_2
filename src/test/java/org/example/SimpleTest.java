package org.example;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.example.api.StudentRequests;
import org.example.api.models.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.text.html.HTML;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;

public class SimpleTest {
    @BeforeAll
    public static  void setupTests(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI = "https://crudcrud.com/api/548d1951c84c4b56950f01b9616f6b5c";

    }
    @Test
    public void userShouldBeAbleCreateStudent() {
        //given - when - then BDD

        //Серилизация из JSON в объект и наоборот
        Student student = new Student("Саша Осипов", 2, null);

        StudentRequests.createStudent(student);
    }
    @Test
    public void userShouldBeAbleDeleteExistingStudent() {
        //Принципы разработки API тестов:
        //1. Атомарность
        //2. Тест сам себе готовит данные

        //Шаг 1: Создание студента
        Student student = new Student("Саша Осипов", 2, null);
        Student createdStudent = StudentRequests.createStudent(student);

        //Шаг 2: удаление
        StudentRequests.deleteStudent(createdStudent.getId());
        //Шаг 3 Проверить, что студент больше не существует
        given()
                .get("/student/" + createdStudent.getId())
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);

    }
}

