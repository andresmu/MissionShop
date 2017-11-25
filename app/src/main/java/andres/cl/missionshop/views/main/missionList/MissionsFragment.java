package andres.cl.missionshop.views.main.missionList;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import andres.cl.missionshop.R;
import andres.cl.missionshop.adapters.MissionAdapter;
import andres.cl.missionshop.adapters.MissionsListener;
import andres.cl.missionshop.models.Mission;
import andres.cl.missionshop.views.missionDetail.MissionActivity;


public class MissionsFragment extends Fragment implements MissionsListener{

    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private MissionAdapter adapter;

    public MissionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_missions, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerView = (RecyclerView) view;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        adapter = new MissionAdapter(this);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void clicked(Mission mission) {
        Intent intent = new Intent(getActivity(), MissionActivity.class);
        intent.putExtra("mission", mission);
        startActivity(intent);
        //new Nodes().missions()mission.setNewMission(false);
    }

    @Override
    public void ready() {
        progressDialog.dismiss();
    }
}
