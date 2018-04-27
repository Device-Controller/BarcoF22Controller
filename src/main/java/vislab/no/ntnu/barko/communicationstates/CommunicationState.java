package vislab.no.ntnu.barko.communicationstates;

import java.io.IOException;

import vislab.no.ntnu.barko.driver.CommunicationContext;

/**
 * The state interface for the state pattern. All classes that are states in this state machine must implement this interface.
 */
public interface CommunicationState {
    void execute(final CommunicationContext context) throws IOException;
}
