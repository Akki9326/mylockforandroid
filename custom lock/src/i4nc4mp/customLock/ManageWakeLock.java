package i4nc4mp.customLock;
//Starting point from AdamK smspopup

import android.content.Context;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

//introduces our own settings for how long the timeout should be and how bright it would be

public class ManageWakeLock {
  private static PowerManager.WakeLock myWakeLock = null;
  private static PowerManager.WakeLock myPartialWakeLock = null;

  // private static PowerManager myPM = null;

  // public static synchronized void setPM(Context context) {
  // if (myPM == null) {
  // myPM = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
  // }
  // }

  public static synchronized void acquireFull(Context context) {
    // setPM(context);
    PowerManager myPM = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

    if (myWakeLock != null) {
      // myWakeLock.release();
      Log.v("acquire","**Wakelock already held");
      return;
    }

    SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(context);

    int flags;

    flags = PowerManager.SCREEN_DIM_WAKE_LOCK;
    
    

    //flags |= PowerManager.ACQUIRE_CAUSES_WAKEUP;
    // PowerManager.ON_AFTER_RELEASE;

    myWakeLock = myPM.newWakeLock(flags, "acquire");
    Log.v("acquire","**Wakelock acquired");
    myWakeLock.setReferenceCounted(false);
    myWakeLock.acquire();

    // Fetch wakelock/screen timeout from preferences
    /*
    int timeout =
      Integer.valueOf(myPrefs.getString(context.getString(R.string.pref_timeout_key), context
          .getString(R.string.pref_timeout_default)));
*/
    //Set a receiver to remove all locks in "timeout" seconds
    //ClearAllReceiver.setCancel(context, timeout);
    //Moved this to a method we can choose so we can time it out again immediately
    
  }

  public static synchronized void DoCancel(Context context,int timeout) {
	  //what it does is passes the int, *100 milis. So one second is 10
	  //ClearAllReceiver.setCancel(context, timeout);
  }
  
  public static synchronized void acquirePartial(Context context) {
    // setPM(context);
    PowerManager myPM = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

    if (myPartialWakeLock != null) {
      return;
    }

    
//a partial is just a "cpu is awake"
    //not sure what it helps accomplish
myPartialWakeLock = myPM.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "customLock");
myPartialWakeLock.acquire();
  }

  public static synchronized void releaseFull() {
    if (myWakeLock != null) {
      Log.v("release","**Wakelock released");
      myWakeLock.release();
      myWakeLock = null;
    }
  }

  public static synchronized void releasePartial() {
    if (myPartialWakeLock != null) {
      Log.v("relpart","**Wakelock (partial) released");
      myPartialWakeLock.release();
      myPartialWakeLock = null;
    }
  }

  public static synchronized void releaseAll() {
    releaseFull();
    releasePartial();
  }

  // This is not supported by the API at this time :(
  /*
  public static synchronized void goToSleep(Context context) {
	  PowerManager myPM = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
   myPM.goToSleep(SystemClock.uptimeMillis() + 100);
  
   }
   */
}