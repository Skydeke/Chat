package conversation;

public class User {

    private String name;
    private long id;
    private String status;

    public User(String n, String s, long id) {
        name = n;
        this.id = id;
        status = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
