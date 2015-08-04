/**
 * 
 */
package controllers;

import play.mvc.Controller;
import play.mvc.With;
import util.SecuredAction;


/**
 * @author vjayad1
 *
 */
@With(SecuredAction.class)
public class SecuredController extends Controller {

}
