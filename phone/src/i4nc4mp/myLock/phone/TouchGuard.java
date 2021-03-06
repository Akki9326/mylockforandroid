package i4nc4mp.myLock.phone;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class TouchGuard extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.callguard);
		
		//Todo, register pref listener so we can also call exit if callActive goes off  (call ends)
	}
	
	//ensure the normal behavior of finishing is ignored on back button
	//this ensures it can only be dismissed by camera key.
	@Override
	public void onBackPressed() {
		return;
	}
	
	//Some devices don't have a cam button.
	//Big ones now are nexus and incredible
	/**  For motion  based nav hardware -----
     * The optical nav handles as a trackball also (Incredible/ADR6300)
     * the motion is locked by this override, to stop conversion to dpad directional events
     * we allow the click to pass through, it comes to key event dispatch as dpad center
     */
    @Override public boolean dispatchTrackballEvent(MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_MOVE) return true;
            
            return super.dispatchTrackballEvent(event);
    }
	
	//camera key is used to clear the touchscreen guard
	@Override
    public boolean dispatchKeyEvent(KeyEvent event) {
		switch (event.getKeyCode()) {
		case KeyEvent.KEYCODE_FOCUS:
			return true;
			//consume since we want to get the 2nd stage press
		case KeyEvent.KEYCODE_CAMERA:
		case KeyEvent.KEYCODE_DPAD_CENTER:
		case KeyEvent.KEYCODE_DEL://just for emulator testing
			moveTaskToBack(true);
			finish();
			return true;
		default:
			break;
		}
		return super.dispatchKeyEvent(event);
	}
}