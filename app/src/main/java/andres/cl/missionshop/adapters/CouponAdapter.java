package andres.cl.missionshop.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import andres.cl.missionshop.R;
import andres.cl.missionshop.data.CurrentUser;
import andres.cl.missionshop.data.Nodes;
import andres.cl.missionshop.models.Coupon;

/**
 * Created by Andrés on 01-12-2017.
 */

public class CouponAdapter extends FirebaseRecyclerAdapter<Coupon, CouponAdapter.CouponHolder>{

    private CouponListener listener;


    public CouponAdapter(CouponListener listener, String email) {
        super(Coupon.class, R.layout.list_item_coupon, CouponHolder.class, new Nodes().coupons(email));
        this.listener=listener;
    }

    @Override
    protected void populateViewHolder(final CouponHolder viewHolder, Coupon model, int position) {
        viewHolder.name.setText(model.getName());
        viewHolder.code.setText("Codigo: "+model.getCode());
        viewHolder.description.setText(model.getDescription());

        View notification = viewHolder.view;
        if (!model.isValid()){
            notification.setVisibility(View.VISIBLE);
        }else{
            notification.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Coupon aux = getItem(viewHolder.getAdapterPosition());
                listener.clicked(aux);
            }
        });
    }

    @Override
    protected void onDataChanged() {
        super.onDataChanged();
        listener.ready();
    }

    public static class CouponHolder extends RecyclerView.ViewHolder {

        private TextView code;
        private TextView name;
        private TextView description;
        private View view;

        public CouponHolder(View itemView) {
            super(itemView);
            code = (TextView) itemView.findViewById(R.id.couponCodeTv);
            name = (TextView) itemView.findViewById(R.id.couponNameTv);
            description = (TextView) itemView.findViewById(R.id.couponDescTv);
            view = itemView.findViewById(R.id.usedV);
        }
    }
}
