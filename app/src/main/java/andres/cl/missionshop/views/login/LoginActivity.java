package andres.cl.missionshop.views.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ResultCodes;

import java.util.Arrays;

import andres.cl.missionshop.BuildConfig;
import andres.cl.missionshop.R;
import andres.cl.missionshop.views.main.MainActivity;


public class LoginActivity extends AppCompatActivity implements ValidationCallback, ProfileCallback {

    private static final int RC_SIGN_IN = 111;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        new UserValidation(LoginActivity.this).userValidated();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RC_SIGN_IN == requestCode){
            if (resultCode == ResultCodes.OK){
                progressDialog = new ProgressDialog(this);
                progressDialog.show();
                new ProfilePhotoValidation(this).validate(this);
            } else{
                singIn();
            }
        }
    }

    @Override
    public void singIn(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()/*,
                                new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build()*/))
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                        .setTheme(R.style.Login)
                        .setLogo(R.drawable.logo1test)
                        .build(),
                RC_SIGN_IN);
    }
    @Override
    public void logged(){
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void done() {
        logged();
    }
}
