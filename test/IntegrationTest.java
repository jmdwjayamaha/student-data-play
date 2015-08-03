import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.REQUEST_TIMEOUT;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import java.io.IOException;

import models.Student;

import org.junit.Test;

import play.Logger;
import play.libs.F.Callback;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.test.TestBrowser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class IntegrationTest {
    
    private static final int PORT = 3333;

    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */
    @Test
    public void test() {
        running(testServer(PORT, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");
                assertTrue(browser.pageSource().contains("Your new application is ready."));
            }
        });
    }
    
    /**
     * Test for the creation and list of an student
     */
    @Test
    public void testStudentController() {
        
        Logger.debug("Running Integration Test");
        running(testServer(PORT) , new Runnable() {
            public void run() {
                
                // Student information 
                String firstName = "testFirst";
                String lastName = "testLast";
                String email = "test@gmail.com";
                
                // Create a student
                final Student student = new Student();
                student.setFirstName(firstName);
                student.setLastName(lastName);
                student.setEmail(email);
                student.setGender("MALE");
                student.setDisability(false);

                ObjectMapper mapper = new ObjectMapper();
                final JsonNode studentJson = mapper.valueToTree(student);
                WSResponse response = WS.url("http://localhost:" + PORT + "/students").post(studentJson).get(REQUEST_TIMEOUT);

                assertEquals(response.getStatus(), OK);

                final JsonNode responseStudent = response.asJson();

                try {
                    final Student createdStudent = mapper.readValue(responseStudent.traverse(), Student.class);
                    assertNotNull(createdStudent.getStudentNumber());
                } catch (IOException e) {
                    assertTrue(false);
                }
                
                // List the students
                WSResponse listResponse = WS.url("http://localhost:" + PORT + "/students").get().get(REQUEST_TIMEOUT);
                
                assertEquals(listResponse.getStatus(), OK);
                
                final JsonNode listResponseJSON = listResponse.asJson();
                try {
                    final Student[] students = mapper.readValue(listResponseJSON.traverse(), Student[].class);
                    assertEquals(students[0].getEmail(), email);
                } catch (IOException e) {
                    assertTrue(false);
                }
            }
        });
    }
}
