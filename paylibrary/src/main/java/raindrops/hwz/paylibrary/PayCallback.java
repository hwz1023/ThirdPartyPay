package raindrops.hwz.paylibrary;

import raindrops.hwz.paylibrary.wxpay.WXPayEntryActivity;

/**
 * Created by huangweizhou on 2017/2/21.
 */

public abstract class PayCallback implements IPayCallback {

    @Override
    public void success() {
        finish();
        paySuccess();
    }

    @Override
    public void error(int code, String msg) {
        finish();
        payError(code, msg);
    }

    private void finish() {
        AppManager.getAppManager().finishActivity(PayActivity.class);
        AppManager.getAppManager().finishActivity(WXPayEntryActivity.class);
    }

    abstract void paySuccess();

    abstract void payError(int code, String msg);

}
