package vislab.no.ntnu.barko.communicationstates;

import java.io.IOException;

import vislab.no.ntnu.barko.commands.F22Command;
import vislab.no.ntnu.barko.driver.CommunicationContext;

public class Unavailable implements CommunicationState {
    @Override
    public void execute(CommunicationContext context) throws IOException {
        F22Command cmd = context.getAndRemove();
        context.resetSendAttempts();
        context.getListener().onAcknowledge(cmd);
        context.changeState(new Wait());
    }
}
