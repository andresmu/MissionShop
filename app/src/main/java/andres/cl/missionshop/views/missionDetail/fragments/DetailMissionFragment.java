package andres.cl.missionshop.views.missionDetail.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import andres.cl.missionshop.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailMissionFragment extends Fragment {


    public DetailMissionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_mission, container, false);
    }

}
