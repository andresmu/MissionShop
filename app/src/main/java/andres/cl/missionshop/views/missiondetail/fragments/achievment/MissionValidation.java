package andres.cl.missionshop.views.missiondetail.fragments.achievment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import andres.cl.missionshop.R;
import andres.cl.missionshop.data.CurrentUser;
import andres.cl.missionshop.data.Nodes;
import andres.cl.missionshop.views.missiondetail.MissionActivity;

/**
 * Created by Andrés on 01-12-2017.
 */

public class MissionValidation extends MissionActivity {


    private MissionValidationCallback callback;

    public MissionValidation (MissionValidationCallback callback) {
        this.callback=callback;
    }

    public void missionVerification(String missionKey){

        DatabaseReference achievment = new Nodes().achievement(new CurrentUser().email()).child(missionKey);
        achievment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount()>3){
                    callback.complete();
                } else if (dataSnapshot.getChildrenCount()==3){
                    callback.incomplete();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void commentMissionVerification(String mission){


        DatabaseReference achievmentComment = new Nodes().achievement(new CurrentUser().email()).child(mission).child("comentario");

        achievmentComment.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    callback.commentOk(String.valueOf(dataSnapshot.getValue()));
                } else {
                    callback.commentOk("No haz comentado");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
