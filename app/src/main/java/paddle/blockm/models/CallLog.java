package paddle.blockm.models;

import java.util.Date;

/**
 * Created by Daniel on 9/21/2015.
 */
public class CallLog {
    public enum CallType {
        INCOMING,
        MISSED,
        OUTGOING,
        BLOCKED;
    }

    private String number;
    private String name;
    private Date callDate;
    private CallType callType;
    private String duration;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCallDate() {
        return callDate;
    }

    public void setCallDate(Date callDate) {
        this.callDate = callDate;
    }

    public CallType getCallType() {
        return callType;
    }

    public void setCallType(CallType callType) {
        this.callType = callType;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
