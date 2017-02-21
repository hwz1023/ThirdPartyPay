package raindrops.hwz.paylibrary.wxpay;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import raindrops.hwz.paylibrary.AppManager;
import raindrops.hwz.paylibrary.PayActivity;
import raindrops.hwz.paylibrary.ThirdPartyPayUtils;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, ThirdPartyPayUtils.getInstance().wxAppId);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX && ThirdPartyPayUtils.getInstance()
                .getCallback() != null) {
            if (resp.errCode == 0) {
                ThirdPartyPayUtils.getInstance().getCallback().success();
            } else {
                ThirdPartyPayUtils.getInstance().getCallback().error(resp.errCode, resp.errStr);
            }
        } else {
            AppManager.getAppManager().finishActivity(PayActivity.class);
            finish();
        }
    }
}