package cn.nodemedia.pusher.widget;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.nodemedia.pusher.R;

/**
 * Created by aliang on 2017/12/4.
 */

public class ButtonPreference extends Preference{
    public ButtonPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        View view = super.onCreateView(parent);

        RelativeLayout layout = (RelativeLayout) ((LinearLayout) view).getChildAt(1);
        layout.setGravity(Gravity.CENTER);
        TextView title = (TextView)layout.getChildAt(0);
        title.setTextColor(view.getResources().getColor(R.color.colorAccent));
        return view;
    }
}
