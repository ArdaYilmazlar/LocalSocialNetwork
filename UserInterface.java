import java.text.SimpleDateFormat;
import java.util.*;

public class UserInterface {
    private Scanner scn = new Scanner(System.in);
    private Server sv; //UI needs a server to operate on

    protected UserInterface(Server sv){
        this.sv = sv;
        mainMenu();
    }

    private void mainMenu(){ //UI always loops back to here

       while(true) {
           clearScreen();
           System.out.printf("Welcome %s, please choose the operation you want : \n",sv.getMainUser().getName());
           System.out.print("1. Show Inbox\n2. Show Outbox\n3. Send Message\n4. Share Post\n5. Show Timeline\n" +
                   "6. Show contact list\n7. Show notifications\n8. Follow People\n9. Show your own posts\nYour choice : ");
           try{
               selectOperation(); //Gets input
           }catch (InputMismatchException ex){ //To prevent crashing when user inputs something like "asd" when an integer is expected.
               scn.nextLine(); //Flushes input buffer
           }
       }
    }

    private void selectOperation(){
        int selection = scn.nextInt();
        scn.nextLine(); //To clear \n left over from pressing enter
        clearScreen(); //Clears mainMenu
        switch (selection){
            case 1:
                printInbox();
                System.out.print("\nPress enter to go back to main menu");
                scn.nextLine();
                break;
            case 2:
                printOutbox();
                System.out.print("\nPress enter to go back to main menu");
                scn.nextLine();
                break;
            case 3:
                sendMessage();
                break;
            case 4:
                sharePost();
                break;
            case 5:
                printTimeline();
                System.out.print("\nPress enter to go back to main menu");
                scn.nextLine();
                break;
            case 6:
                printUserList(sv.getMainUser().getFollowedUsers());
                System.out.print("\nPress enter to go back to main menu");
                scn.nextLine();
                break;
            case 7:
                printNotificationBox();
                System.out.print("\nPress enter to go back to main menu");
                scn.nextLine();
                break;
            case 8:
                followUser();
                break;
            case 9:
                printPosts();
                System.out.print("\nPress enter to go back to main menu");
                scn.nextLine();
                break;

                default:
                    break;

        }
    }
    private void printTimeline(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); //How date will be printed.
        ArrayList<Post> timeline = sv.getMainUser().getTimeLine();

        if(timeline.isEmpty()){ //Just a blank screen doesn't look nice, so added this
            System.out.print("Looks like your timeline is empty!");
            return;
        }

        int i = 0;
        for(Post post : timeline){
            System.out.printf("%d.\n", i);
            System.out.printf("Shared by : %s\n", post.creator);
            System.out.printf("Date : %s\n", formatter.format(post.shareTime.getTime()));
            System.out.printf("Content :\n%s\n\n", post.content);
            System.out.printf("Likes (%d) : %s\n", post.likedBy.size(), post.likedByToString());
            i++;
        }

        //After every post is printed, user is prompted to like a post if they want.
        System.out.print("\nNumber of the post you want to like (-1 to go back to main menu) : ");
        likePost(scn.nextInt());
    }


    private void likePost(int postNumber){ //Simply adds a like to the post, has input control to avoid nullPointerException
        ArrayList<Post> timeline = sv.getMainUser().getTimeLine();

        if(postNumber > timeline.size() || postNumber < 0)
            return;
        else if(timeline.get(postNumber).likedBy.contains(sv.getMainUser())) //If user already liked the post, skip.
            return;

        timeline.get(postNumber).likedBy.add(sv.getMainUser());

    }

    private void printPosts(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        ArrayList<Post> postList = sv.getMainUser().getPostList();
        if(postList.isEmpty()){ //In case didn't post anything
            System.out.print("Looks like you didn't post anything yet!");
            return;
        }

        //Prints every post you have shared
        int i = 0;
        for(Post post : postList){
            System.out.printf("%d.\n", i);
            System.out.printf("Shared by : %s\n", post.creator);
            System.out.printf("Date : %s\n", formatter.format(post.shareTime.getTime()));
            System.out.printf("Content :\n%s\n\n", post.content);
            System.out.printf("Likes (%d) : %s\n", post.likedBy.size(), post.likedByToString());
            System.out.printf("\n");
            i++;
        }
    }


    //UI to follow users
    private void followUser(){
        int selectedUser;
        ArrayList<User> followableUsers = sv.createFollowableUserList(); //Gets a list of non followed users
        System.out.println("Please choose an user to follow;");
        printUserList(followableUsers);

        do{
            selectedUser = scn.nextInt();
            scn.nextLine(); //flush input stream
        }while(selectedUser < 0 || selectedUser >= followableUsers.size()); //To avoid NullPointerException

        sv.followUser(followableUsers.get(selectedUser));
    }

    //Creates a post and sends to server to share
    private void sharePost(){
        Post post = new Post();

        System.out.print("Your post: ");
        post.content = scn.nextLine();

        post.shareTime = Calendar.getInstance();
        post.creator = sv.getMainUser().getName();
        sv.sharePost(post);
    }

    //Creates and sends a message
    private void sendMessage(){
        int selection;
        ArrayList<User> messageableUsers = sv.createMessageableUserList();
        Message message = new Message();

        if(messageableUsers.isEmpty()){ //To see if any user is messeageable
            System.out.print("Looks like you can't message anyone yet! \nDon't forget that you need both users to follow each other " +
                    "to be able to message each other.");
            System.out.print("\nPress enter to go back to main menu");
            scn.nextLine();
            return;
        }


        System.out.println("Please choose an user to message;");
        printUserList(messageableUsers);
        do{
            selection = scn.nextInt();
            scn.nextLine(); //flush \n
        }while(selection < 0 || selection >= messageableUsers.size()); //To avoid NullPointerException

        //Creates and sends a message
        System.out.print("Your message : ");

        message.sender = sv.getMainUser().getName();
        message.receiver = messageableUsers.get(selection).getName();
        message.content = scn.nextLine();
        message.date = Calendar.getInstance();

        sv.sendMessage(messageableUsers.get(selection), message);
    }

    //Prints messages in inbox
    private void printInbox(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        ArrayList<Message> inbox = sv.getMainUser().getInbox();

        if(inbox.isEmpty()){
            System.out.print("Looks like your inbox is empty!");
            return;
        }

        for(Message message : inbox){
            System.out.printf("Sender : %s\n", message.sender);
            System.out.printf("Receiver : %s\n", message.receiver);
            System.out.printf("Date : %s\n", formatter.format(message.date.getTime()));
            System.out.printf("Content:\n%s\n\n", message.content);
        }
    }
    //Prints messages in outbox
    private void printOutbox(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        ArrayList<Message> outbox = sv.getMainUser().getOutbox();
        if(outbox.isEmpty()){
            System.out.print("Looks like your outbox is empty!");
            return;
        }

        for(Message message : outbox){
            System.out.printf("Sender : %s\n", message.sender);
            System.out.printf("Receiver : %s\n", message.receiver);
            System.out.printf("Date : %s\n", formatter.format(message.date.getTime()));
            System.out.printf("Content:\n%s\n\n", message.content);
        }
    }
    //Prints messages in notificationBox
    private void printNotificationBox(){
        ArrayList<Message> notificationBox = sv.getMainUser().getNotificationBox();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        for(Message message : notificationBox){
            System.out.printf("Sender : %s\n", message.sender);
            System.out.printf("Receiver : %s\n", message.receiver);
            System.out.printf("Date : %s\n", formatter.format(message.date.getTime()));
            System.out.printf("Content:\n%s\n\n", message.content);
        }
    }

    private void printUserList(ArrayList<User> userList){
        if(userList.isEmpty()) {
            System.out.print("No users to show.");
            return;
        }

        for(int i = 0; i < userList.size(); i++){
            System.out.printf("%d. %s\n", i, userList.get(i).getName());
        }
    }

    //Prints 50 \n's to clear the screen, preferred this way since other methods weren't consistent on every OS
    private void clearScreen(){

        for(int i = 0; i < 50; i++){
            System.out.println();
        }
    }

}
