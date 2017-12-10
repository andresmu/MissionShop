package andres.cl.missionshop.views.missiondetail;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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

    public void TakedMission(final Mission mission){
        new Nodes().achievement(new CurrentUser().email()).child(mission.getKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount()>3){
                    new Nodes().userMission(new CurrentUser().email()).child(mission.getKey()).child("status").setValue("En revisión");
                } else {
                    new Nodes().userMission(new CurrentUser().email()).child(mission.getKey()).child("status").setValue("Sin Completar");
                    new Nodes().userMission(new CurrentUser().email()).child(mission.getKey()).child("photoPlace").setValue(mission.getPhotoPlace());
                    new Nodes().userMission(new CurrentUser().email()).child(mission.getKey()).child("name").setValue(mission.getName());
                    callback.taked();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
