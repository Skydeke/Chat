package main;

import server.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client extends Thread {

    private Controller guiController;
    private Socket socket;
    private String username;
    private String password;
    private String errorMsg;

    public Client(String ipAdress, String port, Controller c) throws IOException {
        guiController = c;
        socket = new Socket(ipAdress, Integer.parseInt(port));
    }

    public boolean login(String username, String password) throws IOException {
        this.username = username;
        this.password = password;
        socket.getOutputStream().write(("login " + username + " " + password + " \n").getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while (socket.isClosed() == false && (line = br.readLine()) != null) {
            String[] tokens = line.split(" ");
            if (tokens[0].equals("ack")) {
                return true;
            } else {
                errorMsg = tokens[1];
                return false;
            }
        }
        return false;
    }

    @Override
    public void run() {
        super.run();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while (socket.isClosed() == false && (line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                if (tokens[0].equals("notify")) {
                    handleNotify(tokens);
                } else {
                    System.out.println("Recceived:" + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopThread() {
        try {
            socket.getOutputStream().write(("logout \n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleNotify(String[] tokens) {
        System.out.println("I got Notified: " + String.join(" ", tokens));
        if (Integer.parseInt(tokens[1]) == Command.LOGOUT.getCommandID()) {
            System.out.println(tokens[2] + " logged out.");
        }
    }

    public String getErrorMsg() {
        return errorMsg.replaceAll("°", " ");
    }
}
