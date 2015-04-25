import java.util.ArrayList;
import org.joda.time.Instant;
/**
 * A static database of all clients
 * 
 * @author ToFran 
 */
public class DB{
    private static ArrayList<Client> clients = new ArrayList<Client>();
    
    /**
     * Adds a client to the database / list
     * 
     * @param clientToAdd the client to add
     */
    public static void add(Client clientToAdd){
        clients.add(clientToAdd);
    }
    
    /**
     * Connects a client
     * 
     * @param who the id of the client
     * @param when the time of the connection
     */
    public static void connect(int who, Instant when, String nickname){
        clients.get(getPos(who)).joined(when, nickname);
    }
    
    /**
     * Disconnects a client
     * 
     * @param id the id of the client
     * @param when the theme that of the client DC
     * @param didClientTimedOut true/false, if the client diconnected by timming out
     */
    public static void disconnect(int id, String nickname, Instant when, boolean didClientTimedOut){
        int pos = getPos(id);
        if(pos!=-1){
            clients.get(pos).disconnected(when, nickname);
            if(didClientTimedOut){
                clients.get(pos).timedOut();
            }
        }
        else{
             System.out.println("Client id:" + id + " cant disconnect: CLIENT NOT FOUND");
        }
    }

    /**
     * Finds the position in the list/datablase of the specified client
     * 
     * @param id the id of the client
     * @return the position in the list
     */
    public static int getPos(int id){
        int i = 0;
        while(i<clients.size() && clients.get(i).getId() != id){
            i++;
        }
        if(i>=clients.size()){
            i = -1;
        }
        return i;
    }
    
    /**
     * @return all the info abaout the clients on the DB
     */
    public static String toStringAll(){
        String st = "Client ID\tNickname\tCumulative connection time (min)\tLongest connection time (min)" +
                        "\tConnection counter\tTimeOut counter\n";
        for(Client each : clients){
            st += each.toString() + "\n";
        }
        return st;
    }
    
    /**
     * Prints every client
     */
    public static void printAllFormatted(){
        System.out.println("ID  | Nickname                      |SumTime(min)|Longest|Count");
        for(Client each : clients){
            each.print();
        }
    }
    
    /**
     * Disconnects all clients
     */
    public static void disconnectAll(Instant when){
        for(Client each : clients){
            if(each.isConnected()){
                disconnect(each.getId(), null, when, false);
            }
        }
    }
    
    /**
     * This method will delete ALL info in the db
     */
    public static void clear(){
        clients.clear();
    }
}
