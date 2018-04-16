package no.ntnu.vislab.barko.communicationstates;

import no.ntnu.vislab.barko.driver.CommunicationContext;

/**
 * State representing that a command was not successfully sent.
 * It may or may not have been received by the projector, but the response was incorrect or never arrived too many times.
 */
public class SendFailed implements CommunicationState {
    @Override
    public void execute(CommunicationContext context) {
        context.getListener().onAcknowledge(context.getAndRemove());
        context.resetSendAttempts();
        context.changeState(new Wait());
    }
}