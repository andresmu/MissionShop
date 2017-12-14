package andres.cl.missionshop.views.couponlist;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import andres.cl.missionshop.data.CurrentUser;
import andres.cl.missionshop.data.Nodes;

/**
 * Created by Andrés on 10-12-2017.
 */

public class CouponListValidation {

    private CouponListCallback callback;

    public CouponListValidation(CouponListCallback callback){
        this.callback = callback;
    }

    public void couponListValidation(){

        DatabaseReference cupon = new Nodes().coupon(new CurrentUser().email());

        cupon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long cupones = dataSnapshot.getChildrenCount();
                String totales =  Long.toString(cupones);

                callback.complete(totales);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
