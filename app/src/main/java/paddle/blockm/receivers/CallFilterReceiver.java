package paddle.blockm.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.orm.SugarContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import paddle.blockm.models.BlockLog;
import paddle.blockm.models.BlockedNumber;

public class CallFilterReceiver extends BroadcastReceiver {
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        SugarContext.init(context);

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        if (number != null) {
            List<BlockedNumber> numbers = BlockedNumber.find(BlockedNumber.class, "number = ?", number);
            if (numbers.size() > 0) {
                endCall();
                BlockedNumber blockedNumber = numbers.get(0);
                BlockLog log = new BlockLog();
                log.setDate(new Date());
                log.setBlockedNumber(blockedNumber.getNumber());
                BlockLog.save(log);
            }
        }
    }

    private void endCall() {
        Object iTelephony = getITelephony();
        try {
            Method endCallMethod = iTelephony.getClass().getDeclaredMethod("endCall");
            endCallMethod.invoke(iTelephony);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Object getITelephony() {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        Method m1 = null;
        try {
            m1 = tm.getClass().getDeclaredMethod("getITelephony");
            m1.setAccessible(true);
            return m1.invoke(tm);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
