package vislab.no.ntnu.barko.communicationstates;

import vislab.no.ntnu.barko.driver.CommunicationContext;
import vislab.no.ntnu.barko.commands.Power;
import vislab.no.ntnu.providers.Command;

/**
 * State for sending the command currently first in queue.
 * This state is followed by a wait state that varies in length depending on how many commands have been sent and if its the powerON command.
 */
public class Send implements CommunicationState {

    @Override
    public void execute(final CommunicationContext context) {
        Command command = context.getCommand();
        context.getPrintWriter().write(command.toString() + (char)0x0D);
        context.getPrintWriter().flush();
        context.incrementSentCounter();
        context.incrementSendAttempts();
        int waitTime = 0;
        if (context.getSentCount() % 20 == 0) {
            waitTime += 5000;
        }
        if (command instanceof Power && ((Power) command).getPowerSetting() == 1) {
            waitTime += 30000;
        } else {
            waitTime += 500;
        }
        context.setWaitTime(waitTime);
        context.changeState(new ReceiveAcknowledge());
        context.resetTimer();
    }
}
