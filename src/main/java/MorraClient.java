import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class MorraClient extends Thread{


    Socket socketClient;
    //MorraInfo morraInfo = new MorraInfo();
    MorraInfo clientInfo = new MorraInfo();
    ObjectOutputStream out;
    ObjectInputStream in;

    private Consumer<Serializable> callback;

    MorraClient(Consumer<Serializable> call){
        callback = call;
    }

    public void run() {

        try {
            socketClient= new Socket("127.0.0.1",5555);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
        }
        catch(Exception e){}

        while(true) {
            //receives the MorraInfo class from the server
            try {
                clientInfo = (MorraInfo) in.readObject();
                String message = in.readObject().toString();
                callback.accept(message);
            }
            catch(Exception e) {}
        }

    }

    //sends the morraInfo class to the Server
    public void send(String data) {
        try {
            if(clientInfo.getpNum() == 1){
                clientInfo.setP1Plays(data);
            }
            else if(clientInfo.getpNum() == 2){
                clientInfo.setP2Plays(data);
            }
            out.reset();
            out.flush();
            out.writeObject(clientInfo);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
