package andres.cl.missionshop.data;

/**
 * Created by Andrés on 26-10-2017.
 */

public class EmailProcessor {
    public String sanitazedEmail(String email){
        return email.replace("@", "AT").replace(".", "DOT");
    }
}
