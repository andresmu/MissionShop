package andres.cl.missionshop.adapters;

import andres.cl.missionshop.models.Mission;
import andres.cl.missionshop.models.UserMission;

/**
 * Created by Andrés on 20-11-2017.
 */

public interface MissionsListener {

    void clicked(Mission mission);
    void ready();
    void cliked2(UserMission userMission);
}
