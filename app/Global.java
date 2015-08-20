import java.lang.reflect.Method;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Controller;
import play.mvc.Http.Request;
import play.mvc.Result;
import util.PropertyUtils;

/**
 * @author VJAYAD1
 *
 */
public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        Logger.info("Application started: " + PropertyUtils.readString("appilcation.name"));
    }

    @Override
    public void onStop(Application app) {
        Logger.info("Application stopped...");
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Action onRequest(Request request, Method actionMethod) {

        Logger.info("Request intercepted: " + request.toString());
        Logger.info("Action Method: " + actionMethod.getDeclaringClass().getTypeName() + "." + actionMethod.getName());
        return super.onRequest(request, actionMethod);
    }

    public Promise<Result> onHandlerNotFound() {
        return Promise.pure(Controller.notFound("Action not found!"));
    }
}
