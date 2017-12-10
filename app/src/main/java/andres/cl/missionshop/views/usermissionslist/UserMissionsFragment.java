package andres.cl.missionshop.views.usermissionslist;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import andres.cl.missionshop.R;
import andres.cl.missionshop.adapters.MissionsListener;
import andres.cl.missionshop.adapters.UserMissionsAdapter;
import andres.cl.missionshop.data.CurrentUser;
import andres.cl.missionshop.models.Mission;
import andres.cl.missionshop.models.UserMission;
import andres.cl.missionshop.views.missiondetail.MissionActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserMissionsFragment extends Fragment implements MissionsListener{


    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private UserMissionsAdapter adapter;

    public UserMissionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_missions, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // Achievement achievement = (Achievement) getActivity().getIntent().getSerializableExtra("achievement");

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerView = (RecyclerView) view;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        String email = new CurrentUser().email();

        adapter = new UserMissionsAdapter(this, email);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void clicked(Mission mission) {
        Intent intent = new Intent(getActivity(), MissionActivity.class);
        intent.putExtra("mission", mission);
        startActivity(intent);
    }

    @Override
    public void ready() {
        progressDialog.dismiss();
    }

    @Override
    public void cliked2(UserMission userMission) {
        if (userMission.getStatus().equals("Sin Completar")){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("¡Misión Incompleta!");
        alertDialog.setMessage("Tu Mision esta incompleta, recuerda que debes tomar una foto y subir tu comentario");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();

        } else if (userMission.getStatus().equals("En revisión")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setTitle("¡Misión Cumplida!");
            alertDialog.setMessage("Tu Mision esta hecha, ahora el estado de tu mision es: '" + userMission.getStatus() +"'\nNuestro personal esta validando tu misión" +
                    "\n\n¡Te mandaremos un correo cuando ganes tu cupon!");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setTitle("¡Misión Completada!");
            alertDialog.setMessage("Tu Mision esta completa, ¡Felicidades! ¡Ganaste un cupon!");
            alertDialog.setPositiveButton("¡Gracias!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }
}
