package andres.cl.missionshop.views.main.drawer;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import andres.cl.missionshop.R;
import andres.cl.missionshop.data.CurrentUser;
import andres.cl.missionshop.data.Nodes;
import andres.cl.missionshop.views.login.LoginActivity;
import andres.cl.missionshop.views.main.MainActivity;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends Fragment implements PhotoCallback{

    private static final int RESIZE_PHOTO_PIXELS_PERCENTAGE = 30;

    private MagicalPermissions magicalPermissions;
    private MagicalCamera magicalCamera;
    private CircularImageView imageView;

    public DrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drawer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView nick = (TextView) view.findViewById(R.id.nickTv);
        nick.setText(new CurrentUser().userName());

        FloatingActionButton cameraFab = (FloatingActionButton) view.findViewById(R.id.camerafab);

        //Intents menu(navigation View)
        TextView missionList = (TextView) view.findViewById(R.id.missionList);
        TextView couponList = (TextView) view.findViewById(R.id.couponList);
        TextView profileList = (TextView) view.findViewById(R.id.profileList);
        TextView aboutList = (TextView) view.findViewById(R.id.aboutList);



        imageView = (CircularImageView) view.findViewById(R.id.avatarCiv);
        new PhotoValidation(getContext(), this).init();

        new Nodes().user(new CurrentUser().email()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Picasso.with(getContext())
                            .load(dataSnapshot.getValue().toString())
                            .fit()
                            .centerCrop()
                            .into(imageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        new PhotoValidation(getContext(), this).init();

        TextView logout = (TextView) view.findViewById(R.id.logoutTv);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(getActivity())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().finish();
                            }
                        });
            }
        });

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
                //magicalCamera.takeFragmentPhoto(DrawerFragment.this); //sacar foto
                magicalCamera.selectFragmentPicture(DrawerFragment.this, "Selecciona una foto para tu perfil");
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
        magicalCamera.resultPhoto(requestCode, resultCode, data);

        String path = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(), "avatar", "MissionShop", MagicalCamera.JPEG, false);

        if (RESULT_OK == resultCode) {
            //Toast.makeText(getContext(), "Tu foto fue guardada en tu galeria: " + path, Toast.LENGTH_SHORT).show();
            new UpPhoto(getContext(), this).UpFile(path);

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
            alertDialog.setTitle("¡Perfil Completo!");
            alertDialog.setMessage("Tu foto fue guardada en tu galeria como avatar, Veras tu foto la proxima vez que entres a la aplicación ");
            alertDialog.setPositiveButton("Gracias", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    new Intent(Intent.ACTION_MAIN);
                    getActivity().finish();
                }
            });
            alertDialog.show();

        }else{
            Toast.makeText(getContext(), "Lo sentimos, tu foto no fue guardada contacta con fabian7593@gmail", Toast.LENGTH_SHORT).show();
            //noPhoto();
        }
    }

    @Override
    public void setPhoto(String url) {
        Picasso.with(getContext())
                .load(url)
                .fit()
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void noPhoto() {
      /*  Picasso.with(getContext())
                .load()
                .fit()
                .centerCrop()
                .into(imageView);*/
    }
}
