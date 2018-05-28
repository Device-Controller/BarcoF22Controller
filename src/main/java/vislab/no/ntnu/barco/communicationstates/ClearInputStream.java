package vislab.no.ntnu.barco.communicationstates;

import java.io.IOException;

import vislab.no.ntnu.barco.driver.CommunicationContext;

public class ClearInputStream implements CommunicationState {
    @Override
    public void execute(CommunicationContext context) throws IOException {
        try {
            while (context.getReader().ready()) {
                context.getReader().read(); //Clear the inputstream, when an invalid response is recieved it could mean that an older command was sent twice, but the response was delayed causing data to be left in the stream.

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        context.changeState(new Idle());
    }
}
