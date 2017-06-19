package d4tekkom.presensiuas;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by doy on 17/06/17.
 */

public final class ApplicationRecyclerClickListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener listener;

    public ApplicationRecyclerClickListener(Fragment fragment, OnItemClickListener onItemClickListener) {
        listener = onItemClickListener;
        detector = new GestureDetector(fragment.getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
//                return super.onSingleTapUp(e);
                return true;
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    GestureDetector detector;

    public ApplicationRecyclerClickListener(Context context, OnItemClickListener onItemClickListener) {
        listener = onItemClickListener;
        detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
//                return super.onSingleTapUp(e);
                return true;
            }
        });
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && listener != null && detector.onTouchEvent(e)) {
            listener.onItemClick(childView, rv.getChildPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}

