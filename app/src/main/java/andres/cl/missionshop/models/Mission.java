package andres.cl.missionshop.models;

import java.io.Serializable;

/**
 * Created by Andrés on 03-10-2017.
 */

public class Mission implements Serializable{
    private String name, description, local, address, status, logo, photoPlace, type, key;
    private boolean newMission;
    private int position;

    public Mission() {
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getPhotoPlace() {
        return photoPlace;
    }

    public void setPhotoPlace(String photoPlace) {
        this.photoPlace = photoPlace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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
