package paddle.blockm.models;

import com.orm.SugarRecord;

/**
 * Created by Daniel on 9/20/2015.
 */
public class BlockedNumber extends SugarRecord {
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
