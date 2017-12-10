package andres.cl.missionshop.views.missiondetail.fragments.achievment;

import android.content.Context;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

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

    public void validate(final Context context, String key, final RoundedImageView photoMission) {

        DatabaseReference achievmentFoto = new Nodes().achievement(new CurrentUser().email()).child(key).child("foto");

        achievmentFoto.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    new LocalPhoto(context).save(dataSnapshot.getValue(String.class));
                    Picasso.with(context)
                            .load(dataSnapshot.getValue().toString())
                            .fit()
                            .centerCrop()
                            .into(photoMission);
                    callback.done();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
