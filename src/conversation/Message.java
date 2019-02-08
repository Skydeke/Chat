package conversation;

public class Message {

    private boolean sendByMe = false;
    private String message;
    private long id;

    public Message(boolean sendByMe, String message, long id) {
        this.sendByMe = sendByMe;
        this.message = message;
        this.id = id;
    }

    public boolean isSendByMe() {
        return sendByMe;
    }

    public void setSendByMe(boolean sendByMe) {
        this.sendByMe = sendByMe;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
