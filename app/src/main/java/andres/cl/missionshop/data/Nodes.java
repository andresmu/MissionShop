package andres.cl.missionshop.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by Andrés on 07-11-2017.
 */

public class Nodes{

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();

    public DatabaseReference user(String email){
        email = new EmailProcessor().sanitazedEmail(email);
        return root.child("photoUsers").child(email);
        }

    public Query missions(){
        return root.child("missions").orderByChild("position").startAt(0);
    }

    public DatabaseReference mission(String missionKey){
       return root.child("missions").child(missionKey);
    }

    public DatabaseReference achievement(String email){
        email = new EmailProcessor().sanitazedEmail(email);
        return root.child("achievements").child(email);
    }

    public DatabaseReference userMission(String email){
         email = new EmailProcessor().sanitazedEmail(email);
        return root.child("userMissions").child(email);
    }

    public DatabaseReference coupon(String email){
        email = new EmailProcessor().sanitazedEmail(email);
        return root.child("coupon").child(email);
    }

    public Query coupons(String email){
        email = new EmailProcessor().sanitazedEmail(email);
        return root.child("coupon").child(email).orderByChild("position").startAt(0);
    }

}
