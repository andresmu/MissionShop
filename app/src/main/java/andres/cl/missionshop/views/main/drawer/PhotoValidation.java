package andres.cl.missionshop.views.main.drawer;

import android.content.Context;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import andres.cl.missionshop.data.CurrentUser;
import andres.cl.missionshop.data.LocalPhoto;
import andres.cl.missionshop.data.Nodes;

/**
 * Created by Andrés on 26-10-2017.
 */

public class PhotoValidation {

    private Context context;
    private PhotoCallback callback;
    private DatabaseReference photoUser;

    public PhotoValidation(Context context, PhotoCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void init(final CircularImageView imageView){
        String url = new LocalPhoto(context).get();
        if (url != null){
            callback.setPhoto(url);

            //DRY

            photoUser = new Nodes().user(new CurrentUser().email());

            photoUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        Picasso.with(context)
                                .load(dataSnapshot.getValue().toString())
                                .fit()
                                .centerCrop()
                                .into(imageView);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
