package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server extends Thread {

    private ArrayList<ServerWorker> allOnlineUsers = new ArrayList<ServerWorker>();
    private HashMap<String, Boolean> allSavedUsersLoggedIn = new HashMap<>();
    private HashMap<String, String> allSavedUsers = new HashMap<>();

    public static void main(String[] args) {
        Server s = new Server();
        s.start();
    }

    @Override
    public void run() {
        allSavedUsers.put("test", "pw");
        allSavedUsersLoggedIn.put("test", false);
        allSavedUsers.put("da", "pw");
        allSavedUsersLoggedIn.put("da", false);
        try {
            ServerSocket ss = new ServerSocket(8818);
            while (true) {
                System.out.println("About to accept connection.");
                Socket clientSocket = ss.accept();
                System.out.println("Accepted connection from client");
                ServerWorker sw = new ServerWorker(clientSocket, this);
                allOnlineUsers.add(sw);
                sw.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeUser(ServerWorker serverWorker) {
        allOnlineUsers.remove(serverWorker);
        allSavedUsersLoggedIn.remove(serverWorker.getLoggedInUser());
        allSavedUsersLoggedIn.put(serverWorker.getLoggedInUser(), false);
    }

    public void notifyAllClients(Command command, ServerWorker excptionInstance, String[] tokens) throws IOException {
        if (command == Command.LOGIN) {
            for (ServerWorker sw : allOnlineUsers) {
                if (sw != null && sw != excptionInstance) {
                    sw.sendMessage("notify " + Command.LOGIN.getCommandID() + " " + excptionInstance.getLoggedInUser() + " \n");
                }
            }
        } else if (command == Command.LOGOUT) {
            for (ServerWorker sw : allOnlineUsers) {
                if (sw != null && sw != excptionInstance) {
                    sw.sendMessage("notify " + Command.LOGOUT.getCommandID() + " " + excptionInstance.getLoggedInUser() + " \n");
                }
            }
        }
    }

    public boolean checkUserNameAndPassword(String[] tokens) {
        if (tokens[1] != null && tokens[2] != null) {
            try {
                if (allSavedUsers.get(tokens[1]).equals(tokens[2])) {
                    return true;
                } else {
                    return false;
                }
            }catch (NullPointerException e){
                return false;
            }
        }else {
            return false;
        }
    }

    public boolean checkIfUserIsAllreadyLoggedIn(String[] tokens) {
        if (allSavedUsersLoggedIn.get(tokens[1]) == false) {
            return true;
        } else {
            return false;
        }
    }

    public void notifyOfLogin(String[] tokens) {
        allSavedUsersLoggedIn.remove(tokens[1]);
        allSavedUsersLoggedIn.put(tokens[1], true);
    }
}
