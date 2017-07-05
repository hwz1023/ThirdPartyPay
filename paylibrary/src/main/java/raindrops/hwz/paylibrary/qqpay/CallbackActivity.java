package raindrops.hwz.paylibrary.qqpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mobileqq.openpay.api.IOpenApi;
import com.tencent.mobileqq.openpay.api.IOpenApiListener;
import com.tencent.mobileqq.openpay.api.OpenApiFactory;
import com.tencent.mobileqq.openpay.data.base.BaseResponse;
import com.tencent.mobileqq.openpay.data.pay.PayResponse;

import raindrops.hwz.paylibrary.ThirdPartyPayUtils;

public class CallbackActivity extends Activity implements IOpenApiListener {

    IOpenApi openApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openApi = OpenApiFactory.getInstance(this, ThirdPartyPayUtils.getInstance().qqAppId);
        openApi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        openApi.handleIntent(intent, this);
    }

    @Override
    public void onOpenResponse(BaseResponse response) {
        String title = "Callback from mqq";
        String message;
        if (response == null) {
            message = "response is null.";
            return;
        } else {
            if (response instanceof PayResponse) {
                PayResponse payResponse = (PayResponse) response;

                message = " apiName:" + payResponse.apiName
                        + " serialnumber:" + payResponse.serialNumber
                        + " isSucess:" + payResponse.isSuccess()
                        + " retCode:" + payResponse.retCode
                        + " retMsg:" + payResponse.retMsg;
                if (payResponse.isSuccess()) {
                    if (!payResponse.isPayByWeChat()) {
                        ThirdPartyPayUtils.getInstance().getCallback().success();
                        message += " transactionId:" + payResponse.transactionId
                                + " payTime:" + payResponse.payTime
                                + " callbackUrl:" + payResponse.callbackUrl
                                + " totalFee:" + payResponse.totalFee
                                + " spData:" + payResponse.spData;
                    }
                } else {
                    ThirdPartyPayUtils.getInstance().getCallback().error(String.valueOf(payResponse.retCode), payResponse.retMsg);
                }
            } else {
                message = "response is not PayResponse.";
            }
        }
        Log.e("message", message);
        finish();
    }

}
