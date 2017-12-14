package andres.cl.missionshop.views.missiondetail;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import andres.cl.missionshop.data.CurrentUser;
import andres.cl.missionshop.data.Nodes;
import andres.cl.missionshop.models.Mission;

/**
 * Created by Andrés on 10-12-2017.
 */

public class TakeMissionValidation {
    private TakeMissionCallback callback;

    public TakeMissionValidation(TakeMissionCallback callback){
        this.callback = callback;
    }

    public void takedMission(final Mission mission){

        DatabaseReference achievment = new Nodes().achievement(new CurrentUser().email()).child(mission.getKey());

        achievment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference userMissionUp = new Nodes().userMission(new CurrentUser().email()).child(mission.getKey());
                if (dataSnapshot.getChildrenCount()>3){
                    userMissionUp.child("status").setValue("En revisión");
                } else {
                    userMissionUp.child("status").setValue("Sin Completar");
                    userMissionUp.child("photoPlace").setValue(mission.getPhotoPlace());
                    userMissionUp.child("name").setValue(mission.getName());
                    callback.taked();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
