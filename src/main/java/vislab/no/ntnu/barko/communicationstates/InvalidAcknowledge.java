package vislab.no.ntnu.barko.communicationstates;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import vislab.no.ntnu.barko.driver.CommunicationContext;

/**
 * State for when the acknowledge was incorrect.
 * This state will clear the incoming buffer before either trying to resend the same command again, or go into a SendFailed state.
 */
public class InvalidAcknowledge implements CommunicationState {
    @Override
    public void execute(CommunicationContext context) {
        if (context.getSendAttempts() < 3) {
            context.changeState(new Wait());
        } else {
            context.changeState(new SendFailed());
        }
    }
}