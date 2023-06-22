package Events;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadyListener implements EventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadyListener.class);
    @Override
    public  void onEvent(GenericEvent evenet)
    {

        if (evenet instanceof ReadyEvent)
        {
            LOGGER.info("TEST");

            System.out.println("API is ready!");
        }
    }




}
