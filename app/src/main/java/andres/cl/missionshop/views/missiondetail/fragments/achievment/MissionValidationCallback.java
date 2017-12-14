package andres.cl.missionshop.views.missiondetail.fragments.achievment;

import android.content.Context;

/**
 * Created by Andrés on 01-12-2017.
 */

public interface MissionValidationCallback {

    void complete();
    void incomplete();
    void commentOk(String comment);
}
