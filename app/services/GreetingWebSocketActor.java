/**
 * 
 */
package services;

import util.ActorUtils;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * @author vjayad1
 *
 */
public class GreetingWebSocketActor extends UntypedActor {

    private final ActorRef out;

    public GreetingWebSocketActor(ActorRef out) {
        this.out = out;
    }

    public static Props props(ActorRef out) {
        Props props = Props.create(GreetingWebSocketActor.class, out);
        ActorUtils.getActorSystemInstance().actorOf(props);

        return props;
    }

    /*
     * (non-Javadoc)
     * 
     * @see akka.actor.UntypedActor#onReceive(java.lang.Object)
     */
    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            getSender().tell("Greeting " + (String) message, out);
        }
    }

}
