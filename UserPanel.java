
//Test Class


import java.util.Calendar;

public class UserPanel {
    public static void main(String[] args) {
        Server sv = new Server();

        //Filling up the server with users

        Calendar birthday = Calendar.getInstance(); //Birthday is set as today to showcase the administrator birthday celebrating feature!
        sv.createUser("Emine", "Emine@gmail.com", birthday, "Celal bayar University");

        birthday = Calendar.getInstance();
        birthday.set(1971, Calendar.JUNE, 28);
        sv.createUser("Burak", "Burak@gmail.com", birthday, "Celal bayar University");

        birthday = Calendar.getInstance();
        birthday.set(1955, Calendar.OCTOBER, 24);
        sv.createUser("Bill gates", "Bill@microsoft.com", birthday, "Washington");

        birthday = Calendar.getInstance();
        birthday.set(1955, Calendar.FEBRUARY, 24);
        sv.createUser("Steve Jobs", "Steve@Apple.com", birthday, "California");

        sv.setMainUser(1); //Burak is the current user
        sv.followUser(sv.getUsers().get(0)); //Burak follows emine

        sv.setMainUser(0); //Emine is the current user
        sv.followUser(sv.getUsers().get(1)); //Emine follows elon back

        sv.setMainUser(2); //Bill is the current user
        sv.followUser(sv.getUsers().get(0)); //Bill follows Emine

        sv.setMainUser(3); //Steve is the current user
        sv.followUser(sv.getUsers().get(1)); //Steve follows elon

        sv.setMainUser(1); //Burak is the current user
        sv.sharePost(new Post(sv.getMainUser().getName(), Calendar.getInstance(), "Hello!\n Burak here!.")); //Burak shares a post

        sv.sendMessage(sv.getUsers().get(0), new Message(sv.getMainUser().getName(), sv.getUsers().get(0).getName(),
                "Just testing out the messaging feature.", Calendar.getInstance())); //Burak sends a message to emine

        sv.setMainUser(0); //Emine is set as the final user and UI is fired up.
        UserInterface UI = new UserInterface(sv);

    }
}
