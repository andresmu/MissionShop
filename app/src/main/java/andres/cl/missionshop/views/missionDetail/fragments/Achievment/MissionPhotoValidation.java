package andres.cl.missionshop.views.missionDetail.fragments.Achievment;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import andres.cl.missionshop.data.CurrentUser;
import andres.cl.missionshop.data.LocalPhoto;
import andres.cl.missionshop.data.Nodes;

/**
 * Created by Andrés on 27-11-2017.
 */

public class MissionPhotoValidation {

    private MissionPhotoCallback callback;

    public MissionPhotoValidation(MissionPhotoCallback callback) {
        this.callback = callback;
    }

    public void validate(final Context context, String key) {
        new Nodes().achievement(new CurrentUser().email()).child(key).child("foto").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    new LocalPhoto(context).save(dataSnapshot.getValue(String.class));
                }
                callback.done();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
