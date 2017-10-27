package andres.cl.missionshop.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Andrés on 26-10-2017.
 */

public class LocalPhoto {

    private static final String GROUP_KEY = "GROUP_KEY";
    private static final String PREFERENCE_KEY = "PREFERENCE_KEY";
    private Context context;

    public LocalPhoto(Context context) {
        this.context = context;
    }

    public void save(String url) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GROUP_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(PREFERENCE_KEY, url);
        prefEditor.apply();
    }

    public String get() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GROUP_KEY, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PREFERENCE_KEY, null);
    }
}
