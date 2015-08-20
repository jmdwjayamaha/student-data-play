/**
 * 
 */
package dal;

import util.CassandraUtils;
import util.Constants;
import util.PropertyUtils;

import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

/**
 * The Class BaseDAL.
 *
 * @author vjayad1
 */
public class BaseDAL {

    /** The session. */
    protected Session session;

    /** The db name. */
    protected String keyspaceName;

    /**
     * Instantiates a new base DAL.
     */
    public BaseDAL() {

        session = CassandraUtils.getSession();
        keyspaceName = PropertyUtils.readString(Constants.KEYSPACE_NAME);
    }

    /**
     * Retrieve table name.
     *
     * @param entityName
     *            the entity name
     * @return the string
     */
    public String retrieveTableName(final String entityName) {

        return PropertyUtils.readString(entityName);

    }

    /**
     * List all in table.
     *
     * @param tableName
     *            the table name
     * @return the result set future
     */
    public ResultSetFuture listAllInTable(final String tableName) {

        Statement listStatement = QueryBuilder.select().all().from(keyspaceName, tableName);

        return session.executeAsync(listStatement);
    }

}
