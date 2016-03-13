package paddle.blockm.models;


import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Daniel on 9/20/2015.
 */
public class BlockLog extends SugarRecord {
    private Date date;
    private String blockedNumber;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBlockedNumber() {
        return blockedNumber;
    }

    public void setBlockedNumber(String blockedNumber) {
        this.blockedNumber = blockedNumber;
    }
}
