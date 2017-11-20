package andres.cl.missionshop.adapters;

import andres.cl.missionshop.models.Mission;

/**
 * Created by Andrés on 20-11-2017.
 */

public interface MissionsListener {

    void clicked(Mission mission);
    void ready();
}
