package andres.cl.missionshop.models;

import java.io.Serializable;

/**
 * Created by Andrés on 28-11-2017.
 */

public class UserMission implements Serializable{
    private String name, local, address, logo, status, photoPlace, type, key;
    private boolean newMission;

    public UserMission() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhotoPlace() {
        return photoPlace;
    }

    public void setPhotoPlace(String photoPlace) {
        this.photoPlace = photoPlace;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isNewMission() {
        return newMission;
    }

    public void setNewMission(boolean newMission) {
        this.newMission = newMission;
    }
}
