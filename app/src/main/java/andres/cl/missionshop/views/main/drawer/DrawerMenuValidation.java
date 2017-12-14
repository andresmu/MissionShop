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

    private DrawerMenuCallback profileCallback;

    private Context context;

    DatabaseReference userMissions;

    DatabaseReference coupons;

    public DrawerMenuValidation(Context context, DrawerMenuCallback callback){
        this.context=context;
        this.profileCallback=callback;
    }

    public void profileListValidation(){

        userMissions = new Nodes().userMission(new CurrentUser().email());

        userMissions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long misiones = dataSnapshot.getChildrenCount();
                final String totales =  Long.toString(misiones);

                coupons = new Nodes().coupon(new CurrentUser().email());
                coupons.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Long cupones = dataSnapshot.getChildrenCount();
                        String totalescu = Long.toString(cupones);

                        profileCallback.done(totales, totalescu);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void couponListValidation(){
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

    public void missionListValidation(){

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

    public void couponGiftValidation(){

        coupons = new Nodes().coupon(new CurrentUser().email()).child("firstMission");

        coupons.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    profileCallback.gift();
                }else {
                    profileCallback.newPhoto();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
