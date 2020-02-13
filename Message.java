import java.util.Calendar;

public class Message {
    String sender;
    String receiver;
    String content;
    Calendar date;

    public Message(){

    }

    public Message(String sender, String receiver, String content, Calendar date){
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.date = date;
    }
}
