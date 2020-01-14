package model;

public class Messages {
    private Integer id = 0;
    private String author;
    private String messages;
    private String keyUser;

    @Override
    public String toString() {
        return " "+author+" :   "+messages;
    }

    public Messages(){}

    public Messages(Integer id, String author, String messages, String keyUser) {
        this.id = id;
        this.author = author;
        this.messages = messages;
        this.keyUser = keyUser;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyUser() {
        return keyUser;
    }

    public void setKeyUser(String keyUser) {
        this.keyUser = keyUser;
    }

}
