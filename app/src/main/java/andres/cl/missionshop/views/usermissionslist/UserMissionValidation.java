package andres.cl.missionshop.views.usermissionslist;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import andres.cl.missionshop.data.CurrentUser;
import andres.cl.missionshop.data.Nodes;

/**
 * Created by Andrés on 10-12-2017.
 */

public class UserMissionValidation {

    UserMissionCallback callback;

    public UserMissionValidation(UserMissionCallback callback){
        this.callback = callback;
    }

    public void userMissions(){

        DatabaseReference userMissions = new Nodes().userMission(new CurrentUser().email());

        userMissions.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long misiones = dataSnapshot.getChildrenCount();
                String totales =  Long.toString(misiones);
                callback.complete(totales);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
