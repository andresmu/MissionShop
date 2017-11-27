package andres.cl.missionshop.views.missionDetail.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.BubbleImageView;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import andres.cl.missionshop.R;
import andres.cl.missionshop.models.Mission;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailMissionFragment extends Fragment {

    CircularImageView circularImageView;

    public DetailMissionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_mission, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        circularImageView = (CircularImageView) view.findViewById(R.id.logoCiv);
        TextView description = (TextView) view.findViewById(R.id.descMissionTv);
        TextView typeInfo = (TextView) view.findViewById(R.id.typeMissionTv2);
        TextView address = (TextView) view.findViewById(R.id.descAdressTv2);

        Mission mission = (Mission) getActivity().getIntent().getSerializableExtra("mission");

        Picasso.with(getContext()).load(mission.getLogo()).fit().centerCrop().into(circularImageView);
        description.setText(mission.getDescription());
        typeInfo.setText("Tipo: " + mission.getType() + "\nEn: " + mission.getLocal());
        address.setText(mission.getAddress());
    }
}
