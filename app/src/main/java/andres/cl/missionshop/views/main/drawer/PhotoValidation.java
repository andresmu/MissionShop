package andres.cl.missionshop.views.main.drawer;

import android.content.Context;

import andres.cl.missionshop.data.LocalPhoto;

/**
 * Created by Andrés on 26-10-2017.
 */

public class PhotoValidation {

    private Context context;
    private PhotoCallback callback;

    public PhotoValidation(Context context, PhotoCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void init(){
        String url = new LocalPhoto(context).get();
        if (url != null){
            callback.setPhoto(url);
        }else {
            callback.noPhoto();
        }
    }
}
