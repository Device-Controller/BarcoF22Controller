package vislab.no.ntnu.barko.communicationstates;

import vislab.no.ntnu.barko.driver.CommunicationContext;

/**
 * A wait state. The state machine will wait here for the given amount of time before proceeding.
 */
public class Wait implements CommunicationState {

    @Override
    public void execute(final CommunicationContext context) {
        if(context.hasTimerPassed(context.getWaitTime())){
            context.changeState(new Idle());
            context.resetTimer();
        }
    }
}
