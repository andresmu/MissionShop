package andres.cl.missionshop.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Andrés on 07-11-2017.
 */

public class Nodes{

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();

    public DatabaseReference user(String email){
        email = new EmailProcessor().sanitazedEmail(email);
        return root.child("users").child(email);
        }

    public DatabaseReference missions(){
        return root.child("missions");
    }
}
