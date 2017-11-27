package andres.cl.missionshop.views.missionDetail;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import andres.cl.missionshop.R;
import andres.cl.missionshop.data.CurrentUser;
import andres.cl.missionshop.data.Nodes;
import andres.cl.missionshop.models.Mission;
import andres.cl.missionshop.views.main.MainActivity;

public class MissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//activo boton atras

        final Mission mission = (Mission) getIntent().getSerializableExtra("mission");//tomo la mision
        getSupportActionBar().setTitle(mission.getName());//pego el nombre en el toolbar

        ImageView photoIv = (ImageView) findViewById(R.id.localDetailIv);
        Picasso.with(this).load(mission.getPhotoPlace()).fit().centerCrop().into(photoIv);//pego imagen en la scrolling activity

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.cameraAchievfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        new Nodes().achievement(new CurrentUser().email()).child(mission.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount()>3){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MissionActivity.this);
                    alertDialog.setTitle("¡Misión Cumplida!");
                    alertDialog.setMessage("Tu Mision esta completa, ahora el estado de tu mision es: En revisión");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (mission.isNewMission()){
                                mission.setNewMission(false);
                                Toast.makeText(getBaseContext(), "¡Eres el PRIMERO en abrir esta misión!", Toast.LENGTH_SHORT).show();
                                new Nodes().mission(mission.getKey()).child("newMission").setValue(false);
                            }
                        }
                    });
                    alertDialog.show();
                } else if (dataSnapshot.getChildrenCount()==1 || dataSnapshot.getChildrenCount()==3){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MissionActivity.this);
                    alertDialog.setTitle("¡Aún no terminas!");
                    alertDialog.setMessage("Tu Mision esta a medias, ahora mismo el estado de tu mision es: Sin Completar");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        final Mission mission = (Mission) getIntent().getSerializableExtra("mission");

        new Nodes().achievement(new CurrentUser().email()).child(mission.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount()>3){
                    new Nodes().mission(mission.getKey()).child("status").setValue("En revisión");
                } else {
                    new Nodes().mission(mission.getKey()).child("status").setValue("Sin Completar");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
