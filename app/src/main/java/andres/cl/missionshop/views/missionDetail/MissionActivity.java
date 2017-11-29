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
import android.widget.TextView;
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

        TextView textView = (TextView) findViewById(R.id.adressTV);

        textView.setText(mission.getAddress());

        ImageView photoIv = (ImageView) findViewById(R.id.localDetailIv);
        Picasso.with(this).load(mission.getPhotoPlace()).fit().centerCrop().into(photoIv);//pego imagen en la scrolling activity

        if (mission.isNewMission()){
            mission.setNewMission(false);
            Toast.makeText(this, "¡Eres el PRIMERO en abrir esta misión!", Toast.LENGTH_SHORT).show();
            new Nodes().mission(mission.getKey()).child("newMission").setValue(false);
        }
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
