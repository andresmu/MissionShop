package andres.cl.missionshop.views.main;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import andres.cl.missionshop.data.CurrentUser;
import andres.cl.missionshop.data.Nodes;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Andrés on 10-12-2017.
 */

public class FirstRunValidation {
    private FirstRunCallback callback;

    public  FirstRunValidation(FirstRunCallback callback){
        this.callback = callback;
    }

    public void checkFirstRun(final Context context) {
        DatabaseReference userPhoto = new Nodes().user(new CurrentUser().email());

        userPhoto.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("¡Bienvenido!");
                    alertDialog.setMessage("Bienvenido a MissionShop, aqui podras conocer locales en distintas partes completando misiones.\n\n¡Te regalamos un Cupon para tu primera mision, SOLO cargando tu foto de perfil!\n¡No te olvides!");
                    alertDialog.setPositiveButton("GRACIAS <3", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
