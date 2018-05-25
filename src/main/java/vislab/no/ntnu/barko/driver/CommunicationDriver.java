package vislab.no.ntnu.barko.driver;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vislab.no.ntnu.barko.AbstractRunnable;
import vislab.no.ntnu.barko.commands.F22Command;
import vislab.no.ntnu.providers.Command;


public class CommunicationDriver extends AbstractRunnable {
    private Socket host;
    private ArrayList<F22Command> idleCommands;
    private ArrayList<F22Command> outgoingBuffer;
    private final CommunicationContext communicator;

    public interface OnCommandReady{
        void onCommandReady(F22Command command);
    }

    public interface OnConnectionIssue{
        void onIssue();
    }
    private OnConnectionIssue issueCallback;
    private OnCommandReady listener;

    public CommunicationDriver(Socket host, List<F22Command> idleCommands) throws IOException {
        this.host = host;
        this.idleCommands = new ArrayList<>(idleCommands);
        this.outgoingBuffer = new ArrayList<>();
        this.communicator = new CommunicationContext(host.getOutputStream(), host.getInputStream(), outgoingBuffer, idleCommands);
        this.communicator.setOnAcknowledgeReceivedListener(this::handleCommand);
    }

    private void handleCommand(F22Command command) {
        if(listener != null){
            listener.onCommandReady(command);
        }
    }

    public CommunicationDriver(Socket host, F22Command... commands) throws IOException {
        this(host, Arrays.asList(commands));
    }

    public void setOnCommandReady(OnCommandReady listener) {
        this.listener = listener;
    }
    public void setOnIssueCallback(OnConnectionIssue callback){
        this.issueCallback = callback;
    }

    @Override
    public void run() {
        try {
            while (getRunning()) {
                try {
                    communicator.execute();
                } catch (IOException e) {
                    stopThread();

                }
            }
            stopThread();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public boolean queueCommand(F22Command command) {
        if(getRunning()) {
            outgoingBuffer.add(command);
        }
        return getRunning();
    }

    @Override
    public boolean stopThread(){
        if(issueCallback != null){
            issueCallback.onIssue();
        }
        return super.stopThread();
    }
}
