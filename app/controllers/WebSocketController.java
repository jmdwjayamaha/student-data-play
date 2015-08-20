/**
 * 
 */
package controllers;

import play.mvc.Controller;
import play.mvc.WebSocket;
import services.GreetingWebSocketActor;


/**
 * @author vjayad1
 *
 */
public class WebSocketController extends Controller {

    public WebSocket<String> socket() {
        return WebSocket.withActor(GreetingWebSocketActor::props);
    }
}
