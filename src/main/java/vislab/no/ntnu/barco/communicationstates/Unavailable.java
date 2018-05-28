package vislab.no.ntnu.barco.communicationstates;

import java.io.IOException;

import vislab.no.ntnu.barco.commands.F22Command;
import vislab.no.ntnu.barco.driver.CommunicationContext;

public class Unavailable implements CommunicationState {
    @Override
    public void execute(CommunicationContext context) throws IOException {
        F22Command cmd = context.getAndRemove();
        context.resetSendAttempts();
        context.getListener().onAcknowledge(cmd);
        context.changeState(new Wait());
    }
}
