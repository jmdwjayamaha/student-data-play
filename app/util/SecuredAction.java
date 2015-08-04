/**
 * 
 */
package util;

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

    @Override
    public Promise<Result> call(Context ctx) throws Throwable {

        String token = getTokenFromHeader(ctx);
        Promise<Result> promise;

        if (token == null) {
            promise = Promise.pure(Results.unauthorized("unauthorized"));
        } else {
            promise = delegate.call(ctx);
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
