/**
 * 
 */
package util;

import java.util.Base64;

import play.Logger;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Results;

/**
 * @author vjayad1
 *
 */
public class SecuredAction extends Action.Simple {

    /** The Constant HEADER_AUTH_PARAM. */
    public static final String HEADER_AUTH_PARAM = "Authorization";

    /** The Constant BASIC_AUTH. */
    public static final String BASIC_AUTH = "Basic ";

    /*
     * (non-Javadoc)
     * 
     * @see play.mvc.Action#call(play.mvc.Http.Context)
     */
    @Override
    public Promise<Result> call(Context ctx) throws Throwable {

        String tokenString = getTokenFromHeader(ctx);
        Promise<Result> promise = Promise.pure(Results.unauthorized("unauthorized"));

        if (tokenString != null) {

            if (tokenString.startsWith(BASIC_AUTH)) {
                String token = tokenString.substring(tokenString.indexOf(" ") + 1);

                try {

                    String userString = new String(Base64.getDecoder().decode(token), "UTF-8");

                    int seperatorIndex = userString.indexOf(':');
                    String username = userString.substring(0, seperatorIndex);
                    String password = userString.substring(seperatorIndex + 1);

                    if (username.equals(password)) {
                        promise = delegate.call(ctx);
                    }

                } catch (IllegalArgumentException exc) {
                    Logger.info("BASIC AUTH: Invalid Base64 code - " + token);
                }

            }
        }

        return promise;
    }

    /**
     * Gets the token from header.
     *
     * @param ctx
     *            the http context
     * @return the token from header
     */
    private String getTokenFromHeader(Http.Context ctx) {
        String[] authTokenHeaderValues = ctx.request().headers().get(HEADER_AUTH_PARAM);

        String authToken;
        if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1)
                && (authTokenHeaderValues[0] != null)) {
            authToken = authTokenHeaderValues[0];
        } else {
            authToken = null;
        }
        return authToken;
    }
}
