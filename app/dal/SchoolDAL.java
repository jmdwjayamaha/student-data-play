/**
 * 
 */
package dal;

import java.util.List;

import models.School;

/**
 * The Interface SchoolDAL.
 *
 * @author vjayad1
 */
public interface SchoolDAL {

    /**
     * Save.
     *
     * @param shool the shool
     * @return the school
     */
    public School save(School shool);

    /**
     * Delete.
     *
     * @param school the school
     * @return the int
     */
    public int delete(School school);

    /**
     * Update.
     *
     * @param schoolId the school id
     * @param school the school
     * @return the school
     */
    public School update(String schoolId, School school);

    /**
     * List.
     *
     * @return the list
     */
    public List<School> list();

    /**
     * Gets the by id.
     *
     * @param id the id
     * @return the by id
     */
    public School getById(String id);
}
