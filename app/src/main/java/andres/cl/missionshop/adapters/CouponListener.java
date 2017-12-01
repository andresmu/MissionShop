package andres.cl.missionshop.adapters;

import andres.cl.missionshop.models.Coupon;

/**
 * Created by Andrés on 01-12-2017.
 */

public interface CouponListener {
    void clicked(Coupon coupon);
    void ready();
}
