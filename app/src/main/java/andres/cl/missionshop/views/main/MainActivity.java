package andres.cl.missionshop.views.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import andres.cl.missionshop.R;


public class MainActivity extends AppCompatActivity implements FirstRunCallback{

    //private boolean welcome = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        new FirstRunValidation(this).checkFirstRun();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } /*else {
            super.onBackPressed();
        }*/
    }

    @Override
    public void first() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("¡Bienvenido!");
        alertDialog.setMessage("Bienvenido a MissionShop, aqui podras conocer locales en distintas partes completando misiones.\n\n¡Te regalamos un Cupon para tu primera mision, SOLO cargando tu foto de perfil!\n¡No te olvides!");
        alertDialog.setPositiveButton("GRACIAS <3", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
        Toast.makeText(this, "Validado", Toast.LENGTH_SHORT).show();
    }
}