package andres.cl.missionshop.views.missiondetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

public class MissionActivity extends AppCompatActivity implements TakeMissionCallback{

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

        new TakeMissionValidation(this).TakedMission(mission);
    }

    @Override
    public void taked() {
        Toast.makeText(this, "Mision Tomada", Toast.LENGTH_SHORT).show();
    }
}
