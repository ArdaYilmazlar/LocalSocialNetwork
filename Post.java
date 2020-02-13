import java.util.ArrayList;
import java.util.Calendar;

public class Post {
    ArrayList<User> likedBy = new ArrayList<>();
    String creator;
    Calendar shareTime;
    String content;

    //Two constructors to let programmer create posts whichever style they prefer.
    public Post(){

    }

    public Post(String creator, Calendar shareTime, String content){
        this.creator = creator;
        this.shareTime = shareTime;
        this.content = content;
    }

    //Returns a string of those who liked this post.
    public String likedByToString(){
        StringBuilder builder = new StringBuilder();
        for(User usr : likedBy){
            builder.append(String.format("| %s |", usr.getName()));
        }
        return builder.toString();
    }
}
