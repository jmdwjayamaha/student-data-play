import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import java.io.IOException;

import models.Student;

import org.junit.Test;

import play.Logger;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author vjayad1
 *
 */
public class StudentControllerTest {

    private static final long REQUEST_TIMEOUT = 10000;

    private static final int PORT = 3333;

    @Test
    public void listStudentsTest() {

        Logger.debug("Running Test: listStudentsTest");
        running(testServer(PORT), new Runnable() {

            public void run() {
                assertEquals(WS.url("http://localhost:" + PORT + "/students").get().get(REQUEST_TIMEOUT).getStatus(),
                        OK);
            }
        });
    }

    @Test
    public void createStudentTest() {

        Logger.debug("Running Test: createStudentTest");
        running(testServer(PORT), new Runnable() {

            public void run() {
                final Student student = new Student();
                student.setFirstName("testName");
                student.setLastName("testLastName");
                student.setEmail("test@gmail.com");
                student.setGender("MALE");
                student.setDisability(false);

                ObjectMapper mapper = new ObjectMapper();
                final JsonNode studentJson = mapper.valueToTree(student);
                WSResponse response = WS.url("http://localhost:" + PORT + "/students").post(studentJson)
                        .get(REQUEST_TIMEOUT);

                assertEquals(response.getStatus(), OK);

                final JsonNode responseStudent = response.asJson();

                try {
                    final Student createdStudent = mapper.readValue(responseStudent.traverse(), Student.class);
                    assertNotNull(createdStudent.getStudentNumber());
                } catch (JsonProcessingException e) {
                    assertTrue(false);
                } catch (IOException e) {
                    assertTrue(false);
                }
            }
        });
    }

}
