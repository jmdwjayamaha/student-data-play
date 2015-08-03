package util;

import akka.actor.ActorSystem;

/**
 * The Class ActorUtils.
 */
public class ActorUtils {

    private static ActorSystem applicationActorSystem;

    /**
     * Gets the actor system instance. Include only one actor system per logical application.
     *
     * @return the actor system instance
     */
    public static ActorSystem getActorSystemInstance() {

        if (applicationActorSystem == null) {
            applicationActorSystem = ActorSystem.create("play");
        }

        return applicationActorSystem;
    }
}
