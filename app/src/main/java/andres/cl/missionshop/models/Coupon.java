package andres.cl.missionshop.models;

/**
 * Created by Andrés on 03-10-2017.
 */

public class Coupon {
    private String name, description, code;

    public Coupon() {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
