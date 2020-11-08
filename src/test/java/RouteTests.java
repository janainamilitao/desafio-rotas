import br.radixeng.Application;
import br.radixeng.request.EdgeRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

/**
 * @author Janaina Militão
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestPropertySource(
        locations = "classpath:application.properties")

public class RouteTests {

    @LocalServerPort
    private int port;

    @Before
    public void before(){
       RestAssured.baseURI="http://localhost";
       RestAssured.port = port;
    }

    @After
    public void after(){
        RestAssured.reset();
    }



    @Test
    public void test01_must_create_graph(){
        List<EdgeRequest> edges = new ArrayList<>();
        edges.add(new EdgeRequest("A", "B", 5));
        edges.add(new EdgeRequest("B", "C", 4));
        edges.add(new EdgeRequest("C", "D", 8));
        edges.add(new EdgeRequest("D", "C", 8));
        edges.add(new EdgeRequest("D", "E", 6));
        edges.add(new EdgeRequest("A", "D", 5));
        edges.add(new EdgeRequest("C", "E", 2));
        edges.add(new EdgeRequest("E", "B", 3));
        edges.add(new EdgeRequest("A", "E", 7));

       int graphId =  given().
                      contentType("application/json").
                      body(edges).
                      when().post("/graph").
                      then().
                      assertThat().
                      statusCode(HttpStatus.CREATED.value()).
                      extract().
                      path("id");

        assertEquals(graphId, 1);
    }

    @Test
    public void test02_must_calculate_routes_from_C_to_C(){

        Response response = given().contentType("application/json").pathParams("graphId", 1, "town1", "C", "town2", "C").and().param("maxStops", 3).
                when().
                get("/routes/{graphId}/from/{town1}/to/{town2}").
                then().
                statusCode(HttpStatus.OK.value()).
                extract().
                response();

        List<Integer> stops  = response.jsonPath().getList("stops");
        List<String> routes  = response.jsonPath().getList("route");

        assertEquals(routes.get(0), "C");
        assertEquals(stops.get(0), new Integer(0));

    }

    @Test
    public void test03_must_calculate_routes_from_A_to_C(){

        Response response = given().contentType("application/json").pathParams("graphId", 1, "town1", "A", "town2", "C").and().param("maxStops", 4).
                when().
                get("/routes/{graphId}/from/{town1}/to/{town2}").
                then().
                statusCode(HttpStatus.OK.value()).
                extract().
                response();

        List<Integer> stops  = response.jsonPath().getList("stops");
        List<String> routes  = response.jsonPath().getList("route");

        assertEquals(routes.get(0), "ABC");
        assertEquals(stops.get(0), new Integer(2));

        assertEquals(routes.get(1), "ADC");
        assertEquals(stops.get(1), new Integer(2));

        assertEquals(routes.get(2), "ADEBC");
        assertEquals(stops.get(2), new Integer(4));

        assertEquals(routes.get(3), "AEBC");
        assertEquals(stops.get(3), new Integer(3));


    }

    @Test
    public void test04_must_calculate_distance_minimal_when_route_B_from_B(){

        Response response = given().
                basePath("/distance").
                when().
                get("/{graphId}/from/{town1}/to/{town2}", 1, "B", "B").
                then().
                statusCode(HttpStatus.OK.value()).
                extract().
                response();

        assertEquals(response.jsonPath().getList("path"), Arrays.asList("B"));
        assertEquals(response.jsonPath().getInt("distance"), 0);
    }

    @Test
    public void test05_must_calculate_distance_minimal_when_route_A_from_C(){

        Response response = given().
                 basePath("/distance").
                 when().
                 get("/{graphId}/from/{town1}/to/{town2}", 1, "A", "C").
                 then().
                 statusCode(HttpStatus.OK.value()).
                 extract().
                 response();

        assertEquals(response.jsonPath().getList("path"), Arrays.asList("A", "B", "C"));
        assertEquals(response.jsonPath().getInt("distance"), 9);
    }

}
