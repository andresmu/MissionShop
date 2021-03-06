package andres.cl.missionshop.views.missiondetail.fragments.achievment;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import andres.cl.missionshop.R;
import andres.cl.missionshop.data.CurrentUser;
import andres.cl.missionshop.data.LocalPhoto;
import andres.cl.missionshop.data.Nodes;
import andres.cl.missionshop.models.Mission;
import andres.cl.missionshop.models.UserMission;
import andres.cl.missionshop.views.main.drawer.PhotoCallback;
import andres.cl.missionshop.views.main.drawer.UpPhoto;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AchievementFragment extends Fragment implements PhotoCallback, MissionValidationCallback{

    private static final int RESIZE_PHOTO_PIXELS_PERCENTAGE = 30;

    private MagicalPermissions magicalPermissions;
    private MagicalCamera magicalCamera;
    RoundedImageView photoMission;
    DatabaseReference achievment;

    public AchievementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_achievement, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Mission mission = (Mission) getActivity().getIntent().getSerializableExtra("mission");
        final EditText comment = (EditText) view.findViewById(R.id.commentEt);
        ImageButton commentBtn = (ImageButton) view.findViewById(R.id.commentBtn);
        final TextView post = (TextView) view.findViewById(R.id.postTv);

        photoMission = (RoundedImageView) view.findViewById(R.id.photoRiv);

        new MissionPhotoValidation(this).validate(getContext(), mission.getKey());


        new MissionValidation(this).commentMissionVerification(mission.getKey());


        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String desc = comment.getText().toString();
                if (desc.trim().length() > 0){
                    post.setText(desc);
                    // ACTUALIZADO DRY
                    achievment = new Nodes().achievement(new CurrentUser().email()).child(mission.getKey());

                    achievment.child("comentario").setValue(desc);
                    achievment.child("missionKey").setValue(mission.getKey());
                    achievment.child("missionNombre").setValue(mission.getName());

                    UserMission userMission = new UserMission();
                    userMission.setName(mission.getName());
                    userMission.setLocal(mission.getLocal());
                    userMission.setAddress(mission.getAddress());
                    userMission.setLogo(mission.getLogo());
                    userMission.setPhotoPlace(mission.getPhotoPlace());
                    userMission.setType(mission.getType());
                    userMission.setKey(mission.getKey());
                    userMission.setStatus(mission.getStatus());

                    DatabaseReference userMissionRef = new Nodes().userMission(new CurrentUser().email()).child(mission.getKey());

                    userMissionRef.setValue(userMission);

                    comment.setText("");

                    new MissionValidation(AchievementFragment.this).missionVerification(mission.getKey());

                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("¡Completa tu misión!");
                    alertDialog.setMessage("Debes escribir algo, ¡queremos saber si te gusto el servicio o no!");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton cameraFab = (FloatingActionButton) getActivity().findViewById(R.id.cameraAchievfab);

        String[] permissions = new String[] {
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        magicalPermissions = new MagicalPermissions(this, permissions);
        magicalCamera = new MagicalCamera(getActivity(), RESIZE_PHOTO_PIXELS_PERCENTAGE, magicalPermissions);

        cameraFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magicalCamera.takeFragmentPhoto(AchievementFragment.this);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        magicalPermissions.permissionResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Mission mission = (Mission) getActivity().getIntent().getSerializableExtra("mission");
        TextView post = (TextView) getActivity().findViewById(R.id.postTv);

        magicalCamera.resultPhoto(requestCode, resultCode, data);

        String path = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(), "PhotoMission", "MissionShop", MagicalCamera.JPEG, false);

        if (RESULT_OK == resultCode) {
            Toast.makeText(getContext(), "Tu foto fue guardada en tu galeria: " + path, Toast.LENGTH_SHORT).show();
            new UpPhoto(getContext(), this).upFileMission(path, mission.getKey());
            if (post.getText().toString().equals("No haz comentado")){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("¡Foto Subida por: "+new CurrentUser().userName()+"!");
                alertDialog.setMessage("¡Ya tenemos la foto de esta misión! Comenta que te ha parecido el servicio cuando gustes si 'No haz comentado', recuerda que aun tienes la mision sin completar!");
                alertDialog.setPositiveButton("¡Lo hare!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }

        }else{
            Toast.makeText(getContext(), "Lo sentimos, tu foto no fue guardada contacta con fabian7593@gmail", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void setPhoto(String url) {
        Picasso.with(getContext())
                .load(url)
                .fit()
                .centerCrop()
                .into(photoMission);
    }

    @Override
    public void complete() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("¡Misión Cumplida!");
        alertDialog.setMessage("Tu Mision esta completa, ahora el estado de tu mision es: En revisión \n¡Te enviaremos un correo cuando tengas tu cupon ganado!");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
        Toast.makeText(getContext(), "¡FELICIDADES!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void incomplete() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("¡Tenemos tus comentarios!");
        alertDialog.setMessage("Tu Mision aun no termina, te falta tomar la foto, ahora mismo el estado de tu mision es: Sin Completar");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public void commentOk(String comment) {
        TextView post = (TextView) getActivity().findViewById(R.id.postTv);
        post.setText(comment);
    }

}
