package andres.cl.missionshop.views.usermissionslist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import andres.cl.missionshop.R;
import andres.cl.missionshop.data.CurrentUser;
import andres.cl.missionshop.data.Nodes;

public class UserMissionsActivity extends AppCompatActivity implements UserMissionCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_missions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new UserMissionValidation(this).userMissions();

    }

    @Override
    public void complete(String totales) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserMissionsActivity.this);
        alertDialog.setTitle("¡Tus Misiones!");
        alertDialog.setMessage("Aqui ves las misiones que haz hecho o intentado \nTienes un total de: "+ totales);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();

        Toast.makeText(this, "Tus Misiones", Toast.LENGTH_SHORT).show();
    }
}
