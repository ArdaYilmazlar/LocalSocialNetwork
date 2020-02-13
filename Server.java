import java.util.ArrayList;
import java.util.Calendar;

public class Server {

    private ArrayList<User> users = new ArrayList<>(); //Holds every user that's using this Social Network
    private User mainUser; //Current user

    public Server(){
        Administrator admin = new Administrator(users); //An administrator that sends birthday messages
    }

    public void createUser(String name, String email, Calendar birthday, String placeOfResidence){ //Creates and adds a user to the list
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setBirthday(birthday);
        newUser.setPlaceOfResidence(placeOfResidence);

        users.add(newUser);
    }
    //mainUser follows a user, said users followedBy list is updated and sent a notification.
    public void followUser(User userToFollow){
        mainUser.getFollowedUsers().add(userToFollow);
        userToFollow.getFollowedBy().add(mainUser);
        sendNotification("System", userToFollow, String.format("%s just followed you!", mainUser.getName()));
    }

    //Creates a list that consists of users that's not been followed by the mainUser
    protected ArrayList<User> createFollowableUserList(){
        ArrayList<User> followableUserList = new ArrayList<>(users); //Copies users list
        followableUserList.remove(mainUser); //Can't follow yourself

        for(int i = 0; i < mainUser.getFollowedUsers().size(); i++){
            for(int j = 0; j < users.size(); j++){
                if(mainUser.getFollowedUsers().get(i) == users.get(j))
                    followableUserList.remove(i);
            }
        }

        return followableUserList;
    }

    //Adds a post to mainUser's followers' timeline, updates post list of mainUser
    public void sharePost(Post post){
        mainUser.getPostList().add(post);

        for(User usr : mainUser.getFollowedBy()){
            usr.getTimeLine().add(post);
        }
    }


    //Updates inbox of receiver, outbox of mainUser
    public void sendMessage(User receiver, Message message){
        receiver.getInbox().add(message);
        mainUser.getOutbox().add(message);
    }

    //Creates a list of people mainUser follows who follow him back
    protected ArrayList<User> createMessageableUserList(){
        ArrayList<User> messageableUserList = new ArrayList<>();
        for(User followedUser : mainUser.getFollowedUsers()){
            for(User followedUsersFollowedUser : followedUser.getFollowedUsers()){
                if(followedUsersFollowedUser == mainUser)
                    messageableUserList.add(followedUser);
            }
        }
        return messageableUserList;
    }

    //Creates a message and adds it to notificationBox of receiver.
    private void sendNotification(String sender, User receiver, String content){
        Message notification = new Message();
        notification.content = content;
        notification.sender = sender;
        notification.receiver = receiver.getName();
        notification.date = Calendar.getInstance();
        receiver.getNotificationBox().add(notification);

    }

    //Setter and getters

    public User getMainUser(){
        return mainUser;
    }

    public void setMainUser(int userIndex){
        mainUser = users.get(userIndex);
    }

    public ArrayList<User> getUsers(){
        return users;
    }

}
