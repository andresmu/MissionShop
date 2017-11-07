package andres.cl.missionshop.models;

import android.content.Context;

import andres.cl.missionshop.data.LocalPhoto;

/**
 * Created by Andrés on 03-10-2017.
 */

public class Achievement {
    private String  comment, photo, missionKey, missionName;

    public Achievement() {
    }

    public Achievement(String comment, Context context, String missionKey, String missionName) {
        this.comment = comment;
        String photo = new LocalPhoto(context).get();
        this.photo = (photo != null) ? photo : "";
        this.missionKey = missionKey;
        this.missionName = missionName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMissionKey() {
        return missionKey;
    }

    public void setMissionKey(String missionKey) {
        this.missionKey = missionKey;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }
}
