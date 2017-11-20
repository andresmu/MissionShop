package andres.cl.missionshop.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.siyamed.shapeimageview.BubbleImageView;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import andres.cl.missionshop.R;
import andres.cl.missionshop.data.Nodes;
import andres.cl.missionshop.models.Mission;

/**
 * Created by Andrés on 07-11-2017.
 */

public class MissionAdapter extends FirebaseRecyclerAdapter<Mission, MissionAdapter.MissionHolder> {

    public MissionAdapter() {
        super(Mission.class, R.layout.list_item_mission, MissionHolder.class, new Nodes().missions());
    }

    @Override
    protected void populateViewHolder(final MissionHolder viewHolder, Mission model, int position) {
        viewHolder.textView.setText(model.getName() + "\nTipo: " + model.getType() + "\nEn: " + model.getLocal());
        viewHolder.textView2.setText(model.getAddress());
        Picasso.with(viewHolder.itemView.getContext()).load(model.getLogo()).fit().centerCrop().into(viewHolder.imageView);

        View notification = viewHolder.view;
        if (model.isNewMission()){
            notification.setVisibility(View.VISIBLE);
        }else{
            notification.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mission aux = getItem(viewHolder.getAdapterPosition());

            }
        });
    }

    public static class MissionHolder extends RecyclerView.ViewHolder {

        private CircularImageView imageView;
        private TextView textView;
        private TextView textView2;
        private View view;

        public MissionHolder(View itemView) {
            super(itemView);

            imageView = (CircularImageView) itemView.findViewById(R.id.photoBiv);
            textView = (TextView) itemView.findViewById(R.id.nameMissionTv);
            textView2 = (TextView) itemView.findViewById(R.id.descAdressTv);
            view = itemView.findViewById(R.id.notificationV);
        }
    }

}
