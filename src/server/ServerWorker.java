package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerWorker extends Thread {

    private Socket clientSocket;
    private Server server;
    private String loggedInUser;

    public ServerWorker(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line;
            while (clientSocket.isClosed() == false && (line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                if (tokens[0].equalsIgnoreCase("logout")) {
                    handleLogoff(tokens);
                } else if (tokens[0].equalsIgnoreCase("login")) {
                    handleLogin(tokens);
                } else {
                    System.out.println("Unknown command: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handleLogoff(String[] tokens) throws IOException {
        server.notifyAllClients(Command.LOGOUT, this, tokens);
        server.removeUser(this);
        System.out.println(loggedInUser + " logged off.");
        clientSocket.close();
    }

    private void handleLogin(String[] tokens) throws IOException {
        if (server.checkUserNameAndPassword(tokens)) {
            if (server.checkIfUserIsAllreadyLoggedIn(tokens)) {
                System.out.println("Found user, which is not logged in, and password is correct. ");
                loggedInUser = tokens[1];
                server.notifyOfLogin(tokens);
                clientSocket.getOutputStream().write(("ack \n").getBytes());
                server.notifyAllClients(Command.LOGIN, this, tokens);
            } else {
                String msg = "You tried to login even though that user is already online.";
                System.out.println(msg);
                clientSocket.getOutputStream().write(("nack " + msg.replace(" ", "°") + "\n").getBytes());
            }
        } else {
            System.out.println("Error, wrong password.");
            clientSocket.getOutputStream().write(("nack Error,°wrong°password. \n").getBytes());
        }
    }

    public void sendMessage(String message) throws IOException {
        clientSocket.getOutputStream().write(message.getBytes());
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }
}
