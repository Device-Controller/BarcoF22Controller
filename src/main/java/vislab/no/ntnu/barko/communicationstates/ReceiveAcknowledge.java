package vislab.no.ntnu.barko.communicationstates;

import java.io.IOException;

import vislab.no.ntnu.barko.commands.F22Command;
import vislab.no.ntnu.barko.driver.CommunicationContext;

/**
 * State that waits for upto 2000ms for a response.
 */
public class ReceiveAcknowledge implements CommunicationState {
    private long timeout = 2000;
    @Override
    public void execute(final CommunicationContext context) throws IOException {
        F22Command command = context.getCommand();
            if(context.getReader().ready()){
                char c;
                StringBuilder str = new StringBuilder();
                while(context.getReader().ready() && (c = (char) context.getReader().read()) != (char) -1){
                    str.append(c);
                }
                String line = str.toString().trim();
                command.setResponse(line);
                if(command.checkAck()){
                    context.changeState(new AcknowledgeReceived());
                } else if(command.receivedError()){
                    context.changeState(new Unavailable());
                }else {
                    context.changeState(new InvalidAcknowledge());
                }
            } else if(context.hasTimerPassed(timeout)){
                context.checkConnection();
                context.changeState(new NoAcknowledge());
            }
    }
}
