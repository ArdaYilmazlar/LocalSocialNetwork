import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Administrator extends User {
    ArrayList<User> userList; //Administrator requires a list of users to check their birthdays

    protected Administrator(ArrayList<User> userList){
        this.userList = userList;
        celebrateBirthday();
    }

    private void celebrateBirthday(){
        TimerTask checkForBirthdays = new TimerTask() { //A task is set to check user birthdays and
            // if it's their birthday, send a message to them
            @Override
            public void run() {
                Calendar currentDate = Calendar.getInstance();
                for(User usr : userList){ //Loops through the user list
                    if(currentDate.get(Calendar.MONTH) == usr.getBirthday().get(Calendar.MONTH)
                    && currentDate.get(Calendar.DAY_OF_MONTH) == usr.getBirthday().get(Calendar.DAY_OF_MONTH)){

                        usr.getNotificationBox().add(new Message("Administrator", usr.getName(),
                                String.format("Happy birthday %s!", usr.getName()), Calendar.getInstance())); //If birthday, sends a notification.
                    }
                }
            }
        };
        Timer timer = new Timer("Timer");

        timer.scheduleAtFixedRate(checkForBirthdays, 500, 1000 * 60 * 60 * 24); //Fires up checkForBirthDays, everyday.
        //500 ms delay added to let program create the default birthdays.
    }

}
