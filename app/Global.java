import play.GlobalSettings;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author VJAYAD1
 * 
 */
public class Global extends GlobalSettings {

    public void onStart() {
        Logger.info("Student App Started...");
    }
    
	public Promise<Result> onHandlerNotFound() {
		return Promise.pure(Controller.notFound("Action not found!"));
	}
}
