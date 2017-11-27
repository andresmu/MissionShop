package andres.cl.missionshop.views.missionDetail;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import andres.cl.missionshop.R;
import andres.cl.missionshop.data.Nodes;
import andres.cl.missionshop.models.Mission;

public class MissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//activo boton atras

        Mission mission = (Mission) getIntent().getSerializableExtra("mission");//tomo la mision
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
}
