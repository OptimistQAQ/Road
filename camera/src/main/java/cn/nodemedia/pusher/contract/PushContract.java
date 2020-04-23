package cn.nodemedia.pusher.contract;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;

import cn.nodemedia.NodeCameraView;
import cn.nodemedia.NodePublisher;
import cn.nodemedia.NodePublisherDelegate;
import cn.nodemedia.pusher.R;
import xyz.tanwb.airship.utils.Log;
import xyz.tanwb.airship.utils.ToastUtils;
import xyz.tanwb.airship.view.BasePresenter;
import xyz.tanwb.airship.view.BaseView;

public interface PushContract {

    interface View extends BaseView {

        NodeCameraView getNodeCameraView();

        void buttonAvailable(boolean isStarting);

        void buttonUnavailability();

        void flashChange(boolean onOrOff);
    }

    class Presenter extends BasePresenter<View> implements NodePublisherDelegate {

        private SharedPreferences sp;
        private NodePublisher nodePublisher;

        private boolean isStarting;

        private boolean isFlashEnable = false;

        @Override
        public void onStart() {

            // 得到我们的存储Preferences值的对象，然后对其进行相应操作
            sp = PreferenceManager.getDefaultSharedPreferences(mContext);

            String streamURL = sp.getString("push_stream_url", null);
            int cameraPostion = getPreferenceValue("camera_postion", "0");
            boolean camreaFrontMirror = getPreferenceValue("camera_front_mirror", true);
            boolean videoFrontMirror = getPreferenceValue("video_front_mirror", false);
            int videoResolution = getPreferenceValue("video_resolution", "1");
            int videoProfile = getPreferenceValue("video_profile", "0");
            int videoKeyframeInterval = getPreferenceValue("video_keyframe_interval", "1");
            int videoBitrate = getPreferenceValue("video_bitrate", "500000");
            int videoFps = getPreferenceValue("video_fps", "15");
            int audioProfile = getPreferenceValue("audio_profile", "1");
            int audioBitrate = getPreferenceValue("audio_bitrate", "32000");
            int audioSamplerate = getPreferenceValue("audio_samplerate", "44100");
            boolean audioDenoise = getPreferenceValue("audio_denoise", true);
            boolean autoHardwareAcceleration = getPreferenceValue("auto_hardware_acceleration", true);
            int smoothSkinLevel = getPreferenceValue("smooth_skin_level", "0");

            nodePublisher = new NodePublisher(mContext,"c0KzkWKg5LoyRg+hR+2wtrnf/k61cQuoAibf2T8ghqFObNhHVuBiWqn28RhSSyAmLhcxuLVOXVLUf0Blk/axig==");
            nodePublisher.setNodePublisherDelegate(this);
            nodePublisher.setOutputUrl(streamURL);
            nodePublisher.setCameraPreview(mView.getNodeCameraView(), cameraPostion, camreaFrontMirror);
            nodePublisher.setVideoParam(videoResolution, videoFps, videoBitrate, videoProfile, videoFrontMirror);
            nodePublisher.setKeyFrameInterval(videoKeyframeInterval);
            nodePublisher.setAudioParam(audioBitrate, audioProfile, audioSamplerate);
            nodePublisher.setDenoiseEnable(audioDenoise);
            nodePublisher.setHwEnable(autoHardwareAcceleration);
            nodePublisher.setBeautyLevel(smoothSkinLevel);
            nodePublisher.setAutoReconnectWaitTimeout(-1);
            nodePublisher.startPreview();
        }

        private int getPreferenceValue(String key, String defValue) {
            String value = sp.getString(key, defValue);
            return Integer.parseInt(value);
        }

        private boolean getPreferenceValue(String key, boolean defValue) {
            return sp.getBoolean(key, defValue);
        }

        public void pushChange() {
            if (isStarting) {
                nodePublisher.stop();
            } else {
                nodePublisher.start();
            }
        }

        public int switchCamera() {
            int ret = nodePublisher.switchCamera();
            if(ret > 0) {
                mView.flashChange(false);
            }
            return ret;
        }

        public int switchFlash() {
            boolean flashEnable = !this.isFlashEnable;
            int ret = nodePublisher.setFlashEnable(flashEnable);
            this.isFlashEnable = ret == 1;
            mView.flashChange(this.isFlashEnable);
            return ret;
        }

        @Override
        public void onDestroy() {
            nodePublisher.stopPreview();
            nodePublisher.stop();
            nodePublisher.release();
            super.onDestroy();
        }

        @Override
        public void onEventCallback(NodePublisher nodePublisher, int event, String msg) {
            Log.d("EventCallback:" + event + " msg:" + msg);
            handler.sendEmptyMessage(event);
        }

        private Handler handler = new Handler() {
            // 回调处理
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 2000:
                        ToastUtils.show(mContext, mContext.getString(R.string.toast_2000));
                        break;
                    case 2001:
                       ToastUtils.show(mContext, mContext.getString(R.string.toast_2001));
                        isStarting = true;
                        if (mView != null) {
                            mView.buttonAvailable(isStarting);
                        }
                        break;
                    case 2002:
                        ToastUtils.show(mContext, mContext.getString(R.string.toast_2002));
                        break;
                    case 2004:
                       ToastUtils.show(mContext, mContext.getString(R.string.toast_2004));
                        isStarting = false;
                        if (mView != null) {
                            mView.buttonAvailable(isStarting);
                        }
                        break;
                    case 2005:
                        ToastUtils.show(mContext, mContext.getString(R.string.toast_2005));
                        break;
                }
            }
        };
    }
}
