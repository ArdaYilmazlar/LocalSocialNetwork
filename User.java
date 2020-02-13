import java.util.ArrayList;
import java.util.Calendar;

public class User {
    private String name;
    private String email;
    private Calendar birthday;
    private String placeOfResidence;

    //All of those are made from ArrayLists to let them expand as much as needed

    private ArrayList<Message> notificationBox = new ArrayList<>();
    private ArrayList<Post> postList = new ArrayList<>();
    private ArrayList<Post> timeLine = new ArrayList<>();
    private ArrayList<Post> favoritePosts = new ArrayList<>();
    private ArrayList<User> followedUsers = new ArrayList<>();
    private ArrayList<User> followedBy = new ArrayList<>(); //To be able to check if both users follow each other easily
    private ArrayList<Message> inbox = new ArrayList<>();
    private ArrayList<Message> outbox = new ArrayList<>();

    //Getter and Setters

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<User> getFollowedUsers() {
        return followedUsers;
    }

    public ArrayList<Message> getInbox() {
        return inbox;
    }

    public ArrayList<Message> getOutbox() {
        return outbox;
    }

    public ArrayList<Message> getNotificationBox(){
        return notificationBox;
    }

    public ArrayList<Post> getPostList(){
        return postList;
    }

    public ArrayList<Post> getFavoritePosts(){
        return favoritePosts;
    }

    public ArrayList<Post> getTimeLine(){
        return timeLine;
    }

    public ArrayList<User> getFollowedBy(){
        return  followedBy;
    }

    public Calendar getBirthday(){
        return birthday;
    }

    public void setBirthday(Calendar birthday){
        this.birthday = birthday;

    }

    public String getPlaceOfResidence() {
        return placeOfResidence;
    }

    public void setPlaceOfResidence(String placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
    }
}
