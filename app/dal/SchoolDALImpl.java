/**
 *
 */
package dal;

import java.util.ArrayList;
import java.util.List;

import models.School;
import util.Constants;

import play.Logger;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Row;

/**
 * @author vjayad1
 *
 */
public class SchoolDALImpl extends BaseDAL implements SchoolDAL {

    /*
     * (non-Javadoc)
     *
     * @see dal.SchoolDAL#save(models.School)
     */
    @Override
    public School save(School school) {

        final PreparedStatement statement = session
                .prepare("INSERT INTO student_info.school (school_id,name,address,email,gender_type) "
                        + "VALUES (?,?,?,?,?)");
        final BoundStatement boundStatement = statement.bind(school.getSchoolId(), school.getName(),
                school.getAddress(), school.getEmail(), school.getGenderType());

        final ResultSetFuture savedSchoolFuture = session.executeAsync(boundStatement);

        // final Row response = savedSchoolFuture.getUninterruptibly().one();

        return school;
    }

    /*
     * (non-Javadoc)
     *
     * @see dal.SchoolDAL#delete(models.School)
     */
    @Override
    public int delete(School school) {

        final PreparedStatement statement = session.prepare("DELETE FROM school WHERE school_id = ?");
        final BoundStatement boundStatement = statement.bind(school.getSchoolId());

        try {
            session.executeAsync(boundStatement);
        } catch (Exception e) {
            return 0;
        }

        return 1;
    }

    /*
     * (non-Javadoc)
     *
     * @see dal.SchoolDAL#update(java.lang.String, models.School)
     */
    @Override
    public School update(String schoolId, School school) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see dal.SchoolDAL#list()
     */
    @Override
    public List<School> list() {

        final ResultSetFuture schoolListFuture = listAllInTable(School.class.getSimpleName().toLowerCase());
        final List<Row> schoolRowList = schoolListFuture.getUninterruptibly().all();

        final List<School> schoolList = new ArrayList<>();

        School school;
        for (Row row : schoolRowList) {
            school = createSchoolForRow(row);
            schoolList.add(school);
        }

        return schoolList;
    }

    /*
     * (non-Javadoc)
     *
     * @see dal.SchoolDAL#getById(java.lang.String)
     */
    @Override
    public School getById(String id) {

        final PreparedStatement statement = session.prepare("SELECT * FROM school WHERE school_id = ?");
        final BoundStatement boundedStatement = statement.bind(id);

        final ResultSetFuture schoolFuture = session.executeAsync(boundedStatement);
        final Row schoolRow = schoolFuture.getUninterruptibly().one();

        School school;
        if (schoolRow != null) {
            school = createSchoolForRow(schoolRow);
        } else {
            school = null;
        }

        return school;
    }

    /**
     * Creates the school for row.
     *
     * @param row
     *            the row
     * @return the school
     */
    private School createSchoolForRow(Row row) {

        School school = new School();

        school.setSchoolId(row.getString(Constants.SCHOOL_ID));
        school.setName(row.getString(Constants.NAME));
        school.setAddress(row.getString(Constants.ADDRESS));
        school.setGenderType(row.getString(Constants.GENDER_TYPE));
        school.setEmail(row.getString(Constants.EMAIL));

        return school;
    }

}
