package andres.cl.missionshop.views.missionDetail.fragments.Achievment;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import andres.cl.missionshop.data.CurrentUser;
import andres.cl.missionshop.data.Nodes;
import andres.cl.missionshop.models.Mission;
import andres.cl.missionshop.views.missionDetail.MissionActivity;

/**
 * Created by Andrés on 01-12-2017.
 */

public class MissionValidation extends MissionActivity {

    private MissionValidationCallback callback;

    public MissionValidation (MissionValidationCallback callback) {
        this.callback=callback;
    }

    public void MissionValidation(String missionKey, final Context context){


        new Nodes().achievement(new CurrentUser().email()).child(missionKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount()>3){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("¡Misión Cumplida!");
                    alertDialog.setMessage("Tu Mision esta completa, ahora el estado de tu mision es: En revisión \n¡Te enviaremos un correo cuando tengas tu cupon ganado!");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                } else if (dataSnapshot.getChildrenCount()==3){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("¡Tenemos tus comentarios!");
                    alertDialog.setMessage("Tu Mision aun no termina, te falta tomar la foto, ahora mismo el estado de tu mision es: Sin Completar");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }

                callback.complete();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
