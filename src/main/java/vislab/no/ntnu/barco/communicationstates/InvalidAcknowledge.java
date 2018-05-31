package vislab.no.ntnu.barco.communicationstates;


import vislab.no.ntnu.barco.driver.CommunicationContext;

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