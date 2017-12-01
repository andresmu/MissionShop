package andres.cl.missionshop.views.CouponList;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import andres.cl.missionshop.R;
import andres.cl.missionshop.adapters.CouponAdapter;
import andres.cl.missionshop.adapters.CouponListener;
import andres.cl.missionshop.data.CurrentUser;
import andres.cl.missionshop.data.Nodes;
import andres.cl.missionshop.models.Coupon;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserCouponsFragment extends Fragment implements CouponListener{

    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private CouponAdapter adapter;

    public UserCouponsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_coupons, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Cargando cupones...");
        progressDialog.show();

        recyclerView = (RecyclerView) view;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));


        String email = new CurrentUser().email();

        adapter = new CouponAdapter(this, email);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void clicked(final Coupon coupon) {
        if (coupon.isValid()){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Cupon: "+coupon.getName());
        alertDialog.setMessage("Este cupon tiene un estado de: Valido"+"\n\nMuestra a tu vendedor este mensaje por favor:"
        +"\n\nCodigo: "+coupon.getCode()
        +"\n\nDescripción: "+coupon.getDescription()
        +"\n\n\n ¿Este cupon será usado?");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                new Nodes().coupon(new CurrentUser().email()).child(coupon.getCode()).child("valid").setValue(false);
                dialog.dismiss();
                Toast.makeText(getContext(), "TU CUPON HA SIDO UTILIZADO", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }
    }

    @Override
    public void ready() {
        progressDialog.dismiss();
    }
}
