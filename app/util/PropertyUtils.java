/**
 * 
 */
package util;

import play.Configuration;
import play.Play;

/**
 * @author vjayad1
 *
 */
public class PropertyUtils {

    private static Configuration configuration = Play.application().configuration();

    /**
     * Read string.
     *
     * @param key the key
     * @return the string property value
     */
    public static String readString(final String key) {

        return configuration.getString(key);
    }
}
