package andres.cl.missionshop.views.main.drawer;

import android.content.Context;
import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import andres.cl.missionshop.data.CurrentUser;
import andres.cl.missionshop.data.EmailProcessor;
import andres.cl.missionshop.data.LocalPhoto;
import andres.cl.missionshop.models.User;

/**
 * Created by Andrés on 26-10-2017.
 */

public class UpPhoto {

    private Context context;
    private PhotoCallback callback;

    public UpPhoto(Context context, PhotoCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void UpFile(String path){
        String bucket = "gs://missionshop-ded88.appspot.com/";
        String folder = "avatar/" + new EmailProcessor().sanitazedEmail(new CurrentUser().email()) + "/";
        String fileName = "avatar.jpeg";
        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(bucket + folder + fileName);
        reference.putFile(Uri.parse("file://"+path)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests")
                String url = taskSnapshot.getDownloadUrl().toString().split("&token")[0];
                FirebaseDatabase.getInstance().getReference().child("users").child(new EmailProcessor().sanitazedEmail(new CurrentUser().email())).setValue(url);

               // new Nodes().user(email).setValue(user);
                new LocalPhoto(context).save(url);
                callback.setPhoto(url);
            }
        });
    }
}
