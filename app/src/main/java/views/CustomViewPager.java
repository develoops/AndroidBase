package views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * Created by THANH on 3/14/15.
 */
public class CustomViewPager extends ViewPager {
    private boolean isParentsSwipe = false;
    private int TabProgramId = 0;
    private int MaxChildProgramId;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        isParentsSwipe = false;
    }
    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        //Log.e("THANH", "canScroll: " + checkV +" " + dx+ " " + x+ " " + y);
        if(v != this && v instanceof ViewPager) {
            Log.e("THANH", "Check canScroll");
            if (isParentsSwipe)
                //Return FALSE, mean only child swipe.
                return false;
            else
                //Return TRUE, mean only parents swipe.
                return true;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
    public void setMaxChildProgramId(int max){
        MaxChildProgramId = max;
    }
    //This function for checking child viewpager_position, if move to margin position, then
    //  disable swipe in child
    public void f5SwipeStateFromChild(int position){
        Log.e("THANH", "f5SwipeStateFromChild " + position + " maxId= " + MaxChildProgramId);
        if ( position == MaxChildProgramId) {
            isParentsSwipe = true;
            Log.e("THANH", "Became " + isParentsSwipe);
        }
    }
    //This function for checking parrent viewpager_position, if move to TabProgramId, then
    //  allow swipe in child viewpager.
    public void f5SwipeStateFromParents(){
        Log.e("THANH", "f5SwipeStateFromParents " + this.getCurrentItem());
        if (this.getCurrentItem() == TabProgramId) {
            isParentsSwipe = false;
            Log.e("THANH", "Became " + isParentsSwipe);
        }
    }

}