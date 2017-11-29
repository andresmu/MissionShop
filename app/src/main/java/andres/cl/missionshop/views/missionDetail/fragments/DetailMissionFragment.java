package andres.cl.missionshop.views.missionDetail.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.BubbleImageView;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import andres.cl.missionshop.R;
import andres.cl.missionshop.models.Mission;
import andres.cl.missionshop.models.UserMission;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailMissionFragment extends Fragment {

   ImageView ImageView;
    //final AlertDialog alert=null;

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
        ImageView = (ImageView) view.findViewById(R.id.logoCiv);
        TextView description = (TextView) view.findViewById(R.id.descMissionTv);
        Button addresBtn = (Button) view.findViewById(R.id.adressBtn);
/*        TextView typeInfo = (TextView) view.findViewById(R.id.typeMissionTv2);
        TextView address = (TextView) view.findViewById(R.id.descAdressTv2);*/

        final Mission mission = (Mission) getActivity().getIntent().getSerializableExtra("mission");

        addresBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    AlertNoGps();
                }else {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("google.navigation:q="+ mission.getAddress())); //https://cl.linkedin.com/in/andrés-murillo-acosta-5baa3ba7/
                    startActivity(intent);
                }
            }
        });

        Picasso.with(getContext()).load(mission.getLogo()).fit().centerCrop().into(ImageView);
        description.setText(mission.getDescription());
/*        typeInfo.setText("Tipo: " + mission.getType() + "\nEn: " + mission.getLocal());
        address.setText(mission.getAddress());*/
    }

    private void AlertNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
