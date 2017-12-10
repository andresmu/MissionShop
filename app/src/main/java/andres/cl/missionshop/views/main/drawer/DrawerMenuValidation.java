package andres.cl.missionshop.views.main.drawer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import andres.cl.missionshop.data.CurrentUser;
import andres.cl.missionshop.data.Nodes;
import andres.cl.missionshop.models.Coupon;
import andres.cl.missionshop.views.couponlist.UserCouponsActivity;
import andres.cl.missionshop.views.usermissionslist.UserMissionsActivity;

import static java.security.AccessController.getContext;

/**
 * Created by Andrés on 10-12-2017.
 */

public class DrawerMenuValidation {

    private DrawerMenuCallback callback;

    DatabaseReference userMissions;

    DatabaseReference coupons;

    public DrawerMenuValidation(DrawerMenuCallback callback){
        this.callback=callback;
    }

    public void profileListValidation(final Context context){

        userMissions = new Nodes().userMission(new CurrentUser().email());

        userMissions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long misiones = dataSnapshot.getChildrenCount();
                String totales =  Long.toString(misiones);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle(new CurrentUser().userName());
                alertDialog.setMessage("Haz tomado un total de: "+totales+" Misiones");
                alertDialog.setPositiveButton("Siguiente", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        coupons = new Nodes().coupon(new CurrentUser().email());

                        coupons.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Long cupones = dataSnapshot.getChildrenCount();
                                String totalescu = Long.toString(cupones);

                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                                alertDialog.setTitle(new CurrentUser().userName());
                                alertDialog.setMessage("Haz ganado un total de: " + totalescu+" Cupones");
                                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
                });
                alertDialog.show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void couponListValidation(final Context context){
        coupons = new Nodes().coupon(new CurrentUser().email());

        coupons.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Intent intent = new Intent(context, UserCouponsActivity.class);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "No has ganado cupones de descuento", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void missionListValidation(final Context context){

       userMissions = new Nodes().userMission(new CurrentUser().email());

        userMissions.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Intent intent = new Intent(context, UserMissionsActivity.class);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "No has realizado misiones", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void CuponGiftValidation(final Context context){

        coupons = new Nodes().coupon(new CurrentUser().email()).child("firstMission");

        coupons.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("¡Felicidades!");
                    alertDialog.setMessage("Tu foto fue guardada en tu galeria como avatar, veras tu foto la proxima vez que entres a la aplicación \nademás te regalamos un cupon por subir tu foto de perfil :D");
                    alertDialog.setPositiveButton("Gracias", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            new Intent(Intent.ACTION_MAIN);
                        }
                    });
                    alertDialog.show();

                    Coupon cupon = new Coupon();

                    cupon.setName("Tu foto, Tu primer Cupon");
                    cupon.setDescription("Tienes un 5% de descuento para tus siguiente misiones");
                    cupon.setCode("firstMission");
                    cupon.setValid(true);
                    cupon.setPosition(1);

                    DatabaseReference firstCoupon = new Nodes().coupon(new CurrentUser().email()).child("firstMission");

                    firstCoupon.setValue(cupon);
                }else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("Foto de perfil Actualizada");
                    alertDialog.setMessage("Tu foto fue guardada en tu galeria como avatar, veras tu foto la proxima vez que entres a la aplicación.");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            new Intent(Intent.ACTION_MAIN);
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
