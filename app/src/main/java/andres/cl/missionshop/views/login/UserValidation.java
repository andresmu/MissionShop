package andres.cl.missionshop.views.login;

import andres.cl.missionshop.data.CurrentUser;

/**
 * Created by Andrés on 28-09-2017.
 */

public class UserValidation {

    private ValidationCallback callback;

    public UserValidation(ValidationCallback callback) {
        this.callback = callback;
    }

    public void userValidated(){

        if (new CurrentUser().getCurrentUser() != null){
            callback.logged();
        } else {
            callback.singIn();
        }
    }
}
