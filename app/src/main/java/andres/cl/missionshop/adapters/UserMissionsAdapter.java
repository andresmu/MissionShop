package andres.cl.missionshop.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import andres.cl.missionshop.R;
import andres.cl.missionshop.data.Nodes;
import andres.cl.missionshop.models.Mission;
import andres.cl.missionshop.models.UserMission;

/**
 * Created by Andrés on 28-11-2017.
 */

public class UserMissionsAdapter extends FirebaseRecyclerAdapter<UserMission, UserMissionsAdapter.UserMissionHolder> {

    private MissionsListener listener;

    public UserMissionsAdapter(MissionsListener listener, String email) {
        super(UserMission.class, R.layout.list_item_mission_card, UserMissionHolder.class, new Nodes().userMission(email));
        this.listener=listener;
    }


    @Override
    protected void populateViewHolder(final UserMissionHolder viewHolder, UserMission model, int position) {
        viewHolder.textView3.setText(model.getName());
        viewHolder.textView.setText("Tipo: " + model.getType() + "\nEn: " + model.getLocal());
        viewHolder.textView2.setText(model.getAddress());
        Picasso.with(viewHolder.itemView.getContext()).load(model.getPhotoPlace()).centerCrop().fit().into(viewHolder.imageView2);
        Picasso.with(viewHolder.itemView.getContext()).load(model.getLogo()).fit().centerCrop().into(viewHolder.imageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserMission aux = getItem(viewHolder.getAdapterPosition());
                listener.cliked2(aux);
            }
        });
    }

    @Override
    protected void onDataChanged() {
        super.onDataChanged();
        listener.ready();
    }

    public static class UserMissionHolder extends RecyclerView.ViewHolder {

        private CircularImageView imageView;
        private ImageView imageView2;
        private TextView textView;
        private TextView textView2;
        private TextView textView3;

        public UserMissionHolder(View itemView) {
            super(itemView);

            imageView = (CircularImageView) itemView.findViewById(R.id.photoCiv);
            imageView2 = (ImageView) itemView.findViewById(R.id.localIv);
            textView = (TextView) itemView.findViewById(R.id.typeMissionTv);
            textView2 = (TextView) itemView.findViewById(R.id.descAdressTv);
            textView3 = (TextView) itemView.findViewById(R.id.nameMissionTv);

        }
    }
}
