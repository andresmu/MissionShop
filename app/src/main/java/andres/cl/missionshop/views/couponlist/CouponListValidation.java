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

    public void CouponListValidation(final Context context){

        DatabaseReference cupon = new Nodes().coupon(new CurrentUser().email());

        cupon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long cupones = dataSnapshot.getChildrenCount();
                String totales =  Long.toString(cupones);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
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
}
