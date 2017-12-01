package andres.cl.missionshop.views.CouponList;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import andres.cl.missionshop.R;
import andres.cl.missionshop.data.CurrentUser;
import andres.cl.missionshop.data.Nodes;
import andres.cl.missionshop.views.UserMissionsList.UserMissionsActivity;

public class UserCouponsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_coupons);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        new Nodes().coupon(new CurrentUser().email()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long cupones = dataSnapshot.getChildrenCount();
                String totales =  Long.toString(cupones);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserCouponsActivity.this);
                alertDialog.setTitle("¡Tus Cupones!");
                alertDialog.setMessage("Aqui ves los cupones que haz ganado. \nTienes un total de: "+ totales+"\n\n"
                +"*Para usar tus cupones, al momento de realizar una misión o comprar, antes de toca el cupón, y muestrale la pantalla al vendedor.* \n\nLos cupones con punto rojo quedan invalidos.");
                alertDialog.setPositiveButton("¡Gracias!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
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

}
