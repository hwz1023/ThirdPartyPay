package raindrops.hwz.paylibrary;

import android.content.Context;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by huangweizhou on 2017/2/21.
 */

public class ThirdPartyPayUtils {

    public String wxAppId;

    private PayCallback callback;


    public static ThirdPartyPayUtils getInstance() {
        return SingleInstance.instance;
    }

    private static class SingleInstance {
        private static ThirdPartyPayUtils instance = new ThirdPartyPayUtils();
    }

    public ThirdPartyPayUtils initWx(String wxAppId) {
        this.wxAppId = wxAppId;
        return this;
    }

    public static void doWechatPay(Context context, String partnerId, String prepayId, String
            packageValue
            , String nonceStr, String timeStamp, String sign) {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
        msgApi.registerApp(ThirdPartyPayUtils.getInstance().wxAppId);
        PayReq request = new PayReq();
        request.appId = ThirdPartyPayUtils.getInstance().wxAppId;
        request.partnerId = partnerId;
        request.prepayId = prepayId;
        request.packageValue = packageValue;
        request.nonceStr = nonceStr;
        request.timeStamp = timeStamp;
        request.sign = sign;
        msgApi.sendReq(request);
    }

    public ThirdPartyPayUtils bindCallback(PayCallback callback) {
        this.callback = callback;
        return this;
    }

    public PayCallback getCallback() {
        return callback;
    }
}
