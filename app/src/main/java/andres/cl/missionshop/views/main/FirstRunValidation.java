package andres.cl.missionshop.views.main;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import andres.cl.missionshop.data.CurrentUser;
import andres.cl.missionshop.data.Nodes;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Andrés on 10-12-2017.
 */

public class FirstRunValidation {

    private FirstRunCallback callback;

    public  FirstRunValidation(FirstRunCallback callback){
        this.callback = callback;
    }

    public void checkFirstRun() {
        DatabaseReference userPhoto = new Nodes().user(new CurrentUser().email());

        userPhoto.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    callback.first();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
