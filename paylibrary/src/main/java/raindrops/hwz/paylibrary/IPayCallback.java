package raindrops.hwz.paylibrary;

/**
 * Created by huangweizhou on 2017/2/21.
 */

public interface IPayCallback {

    void success();

    void error(String code, String msg);

}
