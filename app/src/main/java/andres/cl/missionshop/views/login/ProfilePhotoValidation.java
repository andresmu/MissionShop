package andres.cl.missionshop.views.login;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import andres.cl.missionshop.data.CurrentUser;
import andres.cl.missionshop.data.LocalPhoto;
import andres.cl.missionshop.data.Nodes;

/**
 * Created by Andrés on 07-11-2017.
 */

class ProfilePhotoValidation {

    private ProfileCallback callback;

    public ProfilePhotoValidation(ProfileCallback callback) {
        this.callback = callback;
    }

    public void validate(final Context context) {

        DatabaseReference photoUser = new Nodes().user(new CurrentUser().email());

        photoUser.addListenerForSingleValueEvent(new ValueEventListener() {
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
