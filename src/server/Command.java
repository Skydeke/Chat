package server;

public enum Command {
    LOGIN(0),
    LOGOUT(1),
    NACK(2),
    ACK(3);

    private final int id;

    Command(int id) {
        this.id = id;

    }

    public int getCommandID() {
        return id;
    }
}
