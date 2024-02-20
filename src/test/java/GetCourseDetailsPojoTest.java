import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.API;
import pojo.GetCourses;
import pojo.WebAutomation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GetCourseDetailsPojoTest {

    public static void main(String[] args) throws InterruptedException {

// TODO Auto-generated method stub

        String response =
                given()
                        .formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")

                        .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")

                        .formParams("grant_type", "client_credentials")

                        .formParams("scope", "trust")

                        .when().log().all()

                        .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asPrettyString();

        System.out.println(response);

        JsonPath jsonPath = new JsonPath(response);

        String accessToken = jsonPath.getString("access_token");

        System.out.println(accessToken);

        String r2 = given()

                .queryParams("access_token", accessToken)

                .when()

                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")

                .asPrettyString();

        System.out.println(r2);


        //Fetch Instructor & Linkedin Text Details Using Deserialization Pojo Class

        GetCourses gc = given()

                .queryParams("access_token", accessToken)

                //.expect().defaultParser(Parser.JSON)

                .when()

                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")

                .as(GetCourses.class);

        System.out.println("Fetch Linkedin Text: " + gc.getLinkedIn());
        System.out.println("Fetch Instructor Text: " + gc.getInstructor());

        //Fetch  SoapUI course price - way 1
        System.out.println("Fetch Api Size: " + gc.getCourses().getApi().size());

        String expectedCourseTitle = "SoapUI Webservices testing";
        int count = gc.getCourses().getApi().size();
        for (int i = 0; i < count; i++) {
            if (gc.getCourses().getApi().get(i).getCourseTitle().equals(expectedCourseTitle)) {
                System.out.println("Fetch " + expectedCourseTitle + " price: " + gc.getCourses().getApi().get(i).getPrice());
            }
        }

        //Fetch  SoapUI course price - way 2
        System.out.println("Fetch Api Size: " + gc.getCourses().getApi().size());

        String expectedCourseTitle1 = "SoapUI Webservices testing";
        List<API> apiCour = gc.getCourses().getApi();
        for (int i = 0; i < apiCour.size(); i++) {
            if (apiCour.get(i).getCourseTitle().equals(expectedCourseTitle)) {
                System.out.println("Fetch " + expectedCourseTitle + " price: " + apiCour.get(i).getPrice());
            }
        }

        //Print All The CourseTitles Of WebAutomation
        List<WebAutomation> webAuto = gc.getCourses().getWebAutomation();
        for (int j = 0; j < webAuto.size(); j++) {
            System.out.println("Fetched All The WebAutomation Course Titles: " + webAuto.get(j).getCourseTitle());
        }

    //Validate expect and actual coursetitles of webautomation

    //Define an expected array of string for webautomation coursetitles
    String[] expectedCourseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};

    //Create an empty arraylist
    ArrayList<String> al = new ArrayList<>();

    //fetch all the list of coursetitles and add it to the empty arraylist
        List<WebAutomation> webAuto1 = gc.getCourses().getWebAutomation();
        for (int k = 0; k < webAuto1.size(); k++) {

            al.add(webAuto1.get(k).getCourseTitle());
        }

        //Convert the expect array into arraylist
        List<String> exCourseTitles= Arrays.asList(expectedCourseTitles);

        //validate it using assertions
        assertTrue(al.equals(exCourseTitles));

    }

}