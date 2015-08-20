package controllers;

import javax.inject.Inject;

import models.School;
import play.libs.F.Promise;
import play.libs.Json;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;

import dal.SchoolDALImpl;

/**
 * The Class SchoolController.
 */
public class SchoolController extends SecuredController {

    @Inject
    private SchoolDALImpl schoolDAL;

    /**
     * List.
     *
     * @param page
     *            the page
     * @return the promise
     */
    public Promise<Result> list(final int page) {

        return Promise.promise(() -> {
            JsonNode schoolListJson = Json.toJson(schoolDAL.list());

            return ok(schoolListJson);
        });
    }

    /**
     * Gets the by id.
     *
     * @param id
     *            the id
     * @return the by id
     */
    public Result getById(final String id) {

        return ok("Found one: " + id);
    }

    /**
     * Save.
     *
     * @return the promise
     */
    public Promise<Result> save() {

        JsonNode schoolJson = request().body().asJson();

        School school = Json.fromJson(schoolJson, School.class);

        return Promise.promise(() -> {
            return ok(Json.toJson(schoolDAL.save(school)));
        });
    }
}
