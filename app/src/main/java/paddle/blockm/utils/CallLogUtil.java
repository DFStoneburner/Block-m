package paddle.blockm.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import paddle.blockm.models.CallLog;

/**
 * Created by Daniel on 11/11/2015.
 */
public class CallLogUtil {
    public static List<CallLog> getCallsLog(ContentResolver contentResolver) {
        List<CallLog> logs = new ArrayList<>();
        String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
        Uri callUri = Uri.parse("content://call_log/calls");
        Cursor curLog = contentResolver.query(callUri, null, null, null, strOrder);
        while (curLog.moveToNext()) {
            CallLog callLog = new CallLog();
            String callNumber = curLog.getString(curLog
                    .getColumnIndex(android.provider.CallLog.Calls.NUMBER));
            callLog.setNumber(callNumber);

            String callName = curLog
                    .getString(curLog
                            .getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME));
            callLog.setName(callName);

            String callDate = curLog.getString(curLog
                    .getColumnIndex(android.provider.CallLog.Calls.DATE));
            callLog.setCallDate(new Date(Long.parseLong(callDate)));

            String callType = curLog.getString(curLog
                    .getColumnIndex(android.provider.CallLog.Calls.TYPE));
            if (callType.equals("1")) {
                callLog.setCallType(CallLog.CallType.INCOMING);
            } else {
                callLog.setCallType(CallLog.CallType.OUTGOING);
            }

            String duration = curLog.getString(curLog
                    .getColumnIndex(android.provider.CallLog.Calls.DURATION));
            callLog.setDuration(duration);

            logs.add(callLog);
        }
        return logs;
    }
}
