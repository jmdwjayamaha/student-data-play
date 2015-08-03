package controllers;

import static akka.pattern.Patterns.ask;

import util.ActorUtils;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import models.Student;
import play.Logger;
import play.Logger.ALogger;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;
import services.ListStudentsActor;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StudentController extends Controller {

    private static final ALogger logger = Logger.of(StudentController.class);

    static ActorSystem actorSystem;

    static {
        actorSystem = ActorUtils.getActorSystemInstance();
        actorSystem.actorOf(Props.create(ListStudentsActor.class), "ListStudentsActor");
    }

    @Inject
    private ObjectMapper objMapper;

    /**
     * Handle the GET user by ID.
     * 
     * @param id
     *            the ID of the student
     * @return response of the operation status and the student JSON
     */
    public Result getById(final String id) {

        Student student = Ebean.find(Student.class, id);

        Result result;
        if (student == null) {
            result = notFound();
        } else {

            JsonNode resultStudent = objMapper.valueToTree(student);
            result = ok(resultStudent);
        }

        return result;
    }

    /**
     * Delete a student by the ID.
     * 
     * @param id
     *            the ID of the student
     * @return The deletion status
     */
    public Result delete(String id) {

        int deleted = Ebean.delete(Student.class, id);

        Result result;
        if (deleted == 1) {
            result = ok();
        } else {
            result = notFound();
        }

        return result;
    }

    /**
     * Retrieve the entire list of students.
     * 
     * @return A Promise of a Future to all the students in JSON format
     */
    @SuppressWarnings("unchecked")
    public Promise<Result> list() {

        ActorSelection listStudentsActor = actorSystem.actorSelection("user/ListStudentsActor");

        return Promise.wrap(ask(listStudentsActor, "", 30000)).map(r -> {
            final JsonNode resultNode = objMapper.valueToTree((List<Student>) r);
            return ok(resultNode);
        });
    }

    /**
     * Update an existing student.
     * 
     * @param id
     *            The ID of the student
     * @return The status of the update operation and the updated student JSON
     */
    public Result update(String id) {

        JsonNode studentJson = request().body().asJson();

        Student student = Ebean.find(Student.class, id);

        Result result;
        if (student == null) {
            result = notFound();
        } else {
            if (studentJson == null) {
                result = badRequest("Require JSON");
            } else {

                Student updatedValueStudent;
                try {

                    updatedValueStudent = objMapper.readValue(studentJson.traverse(), Student.class);
                    Ebean.update(updatedValueStudent);
                    result = ok(studentJson);

                } catch (JsonParseException | JsonMappingException e) {
                    result = badRequest("Invalid JSON");
                } catch (IOException e) {
                    result = internalServerError("Cannot read from the request");
                }
            }
        }

        return result;
    }

    /**
     * Save a new student to the database.
     * 
     * @return the status of the operation and the created student JSON
     */
    public Result save() {

        JsonNode jsonBody = request().body().asJson();

        Result result;
        if (jsonBody == null) {
            result = badRequest("Require JSON");
        } else {

            try {

                Student student = objMapper.readValue(jsonBody.traverse(), Student.class);
                Ebean.save(student);
                logger.debug("Student Saved: " + student.getStudentNumber());
                jsonBody = objMapper.valueToTree(student);
                result = ok(jsonBody);

            } catch (JsonParseException | JsonMappingException e) {
                result = badRequest("Invalid JSON");
            } catch (IOException e) {
                result = badRequest("Cannot read from the request");
            }
        }

        return result;
    }

    /**
     * @return the objMapper
     */
    public ObjectMapper getObjMapper() {
        return objMapper;
    }

    /**
     * @param objMapperValue
     *            the ObjectMapper instance to set
     */
    public void setObjMapper(final ObjectMapper objMapperValue) {
        this.objMapper = objMapperValue;
    }
}
