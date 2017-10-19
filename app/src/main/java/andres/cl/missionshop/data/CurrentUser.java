package andres.cl.missionshop.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Andrés on 28-09-2017.
 */

public class CurrentUser {

    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public FirebaseUser getCurrentUser(){
        return currentUser;
    }

    public String email(){
        return currentUser.getEmail();
    }

    public String uid(){
        return currentUser.getUid();
    }
}
