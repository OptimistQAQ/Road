package cn.nodemedia.pusher.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import cn.nodemedia.NodeCameraView;
import cn.nodemedia.pusher.R;
import cn.nodemedia.pusher.ShareBean;
import cn.nodemedia.pusher.contract.PushContract;
import cn.nodemedia.pusher.widget.ScreenAboveLollRecordManager;
import xyz.tanwb.airship.view.BaseActivity;

public class PushActivity extends BaseActivity<PushContract.Presenter> implements PushContract.View, View.OnClickListener {

    private NodeCameraView pushSurface;
    private ImageView pushBack,pushSwitch,pushFlash;
    private TextView pushButton;
    private ScreenAboveLollRecordManager screenAboveLollRecordManager;
//    private static final int VIDEO_WITH_CAMERA = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_push;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
//        StatusBarUtils.setColorToTransparent(this);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        int videoOrientation = Integer.parseInt(sp.getString("video_orientation", "0"));
        if(videoOrientation == 1) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if(videoOrientation == 2) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        assignViews();

        screenAboveLollRecordManager = new ScreenAboveLollRecordManager(PushActivity.this, 600, 600);
        screenAboveLollRecordManager.startScreenRecorde(PushActivity.this);

//        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
//        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
//        startActivityForResult (intent, VIDEO_WITH_CAMERA);
    }


    /**
     * 实例化视图控件
     */
    private void assignViews() {
        pushSurface = getView(R.id.push_surface);
        pushBack = getView(R.id.ca_push_back);
        pushBack.setOnClickListener(this);
        pushSwitch = getView(R.id.push_switch);
        pushSwitch.setOnClickListener(this);
        pushFlash = getView(R.id.push_flash);
        pushFlash.setOnClickListener(this);
        pushButton = getView(R.id.push_button);
        pushButton.setOnClickListener(this);
    }

    @Override
    public void initPresenter() {
        mPresenter.initPresenter(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.ca_push_back) {
            exit();

        } else if (i == R.id.push_switch) {
            mPresenter.switchCamera();
            FlipAnimatorXViewShow(pushSwitch, pushSwitch, 300);

        } else if (i == R.id.push_flash) {
            mPresenter.switchFlash();

        } else if (i == R.id.push_button) {
            mPresenter.pushChange();
            pushButton.setText(R.string.push_wait);
            screenAboveLollRecordManager.destroy();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void FlipAnimatorXViewShow(final View oldView, final View newView, final long time) {

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(oldView, "rotationY", 0, 90);
        final ObjectAnimator animator2 = ObjectAnimator.ofFloat(newView, "rotationY", -90, 0);
        animator2.setInterpolator(new OvershootInterpolator(2.0f));

        animator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                oldView.setVisibility(View.GONE);
                animator2.setDuration(time).start();
                newView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator1.setDuration(time).start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void exit() {
        finish();
    }

    @Override
    public NodeCameraView getNodeCameraView() {
        return pushSurface;
    }

    @Override
    public void buttonAvailable(boolean isStarting) {
//        pushButton.setClickable(true);
        pushButton.setBackgroundColor(isStarting ? 0x3FF9493B : 0x3F000000);
        pushButton.setText(isStarting ? R.string.push_stop : R.string.push_start);
    }

    @Override
    public void buttonUnavailability() {
//        pushButton.setClickable(false);
//        pushButton.setTextColor(mContext.getResources().getColor(R.color.colorGray));

    }

    @Override
    public void flashChange(boolean onOrOff) {
        if(onOrOff) {
            pushFlash.setImageResource(R.drawable.ic_flash_on);
        } else {
            pushFlash.setImageResource(R.drawable.ic_flash_off);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        screenAboveLollRecordManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

//        try{
//            if (resultCode == Activity.RESULT_OK && requestCode == VIDEO_WITH_CAMERA) {
//                Uri uri = data.getData();
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
