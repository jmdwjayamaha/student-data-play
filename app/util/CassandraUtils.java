/**
 * 
 */
package util;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

/**
 * @author vjayad1
 *
 */
public class CassandraUtils {

    /** The cluster. */
    private static Cluster cluster;

    /** The session. */
    private static Session session;

    /**
     * Generate cluster.
     */
    private static void generateCluster() {

        cluster = Cluster.builder().addContactPoint(PropertyUtils.readString("cassandra.host")).build();
    }

    /**
     * Gets the session.
     *
     * @return the session
     */
    public static Session getSession() {

        if (session == null) {
            generateCluster();
            session = cluster.connect();
        }

        return session;
    }
}
