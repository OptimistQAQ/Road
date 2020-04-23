package cn.nodemedia.pusher.contract;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;

import cn.nodemedia.NodePlayer;
import cn.nodemedia.NodePlayerDelegate;
import cn.nodemedia.NodePlayerView;
import xyz.tanwb.airship.utils.Log;
import xyz.tanwb.airship.view.BasePresenter;
import xyz.tanwb.airship.view.BaseView;

public interface PlayContract {

    interface View extends BaseView {

        NodePlayerView getNodePlayerView();

    }

    class Presenter extends BasePresenter<View> implements NodePlayerDelegate {

        private SharedPreferences sp;
        private NodePlayer nodePlayer;

        private boolean isStarting;

        @Override
        public void onStart() {
            // 得到我们的存储Preferences值的对象，然后对其进行相应操作
            sp = PreferenceManager.getDefaultSharedPreferences(mContext);

            String streamURL = sp.getString("play_stream_url", null);
            int bufferTime = getPreferenceValue("buffertime", "300");
            int maxBufferTime = getPreferenceValue("maxbuffertime", "1000");
            int videoScaleMode = getPreferenceValue("video_scale_mode", "1");
            boolean autoHA = getPreferenceValue("decode_auto_hardware_acceleration",true);
            String rtspTransport =  sp.getString("rtsp_transport","udp");

            NodePlayerView playSurface = mView.getNodePlayerView();
            //设置播放视图的渲染器模式,可以使用SurfaceView或TextureView. 默认SurfaceView
            playSurface.setRenderType(NodePlayerView.RenderType.SURFACEVIEW);
            //设置视图的内容缩放模式

            NodePlayerView.UIViewContentMode mode = null;
            switch (videoScaleMode) {
                case 0:
                    mode = NodePlayerView.UIViewContentMode.ScaleToFill;
                    break;
                case 1:
                    mode = NodePlayerView.UIViewContentMode.ScaleAspectFit;
                    break;
                case 2:
                    mode = NodePlayerView.UIViewContentMode.ScaleAspectFill;
                    break;
            }
            playSurface.setUIViewContentMode(mode);

            nodePlayer = new NodePlayer(mContext,"c0KzkWKg5LoyRg+hR+2wtrnf/k61cQuoAibf2T8ghqFObNhHVuBiWqn28RhSSyAmLhcxuLVOXVLUf0Blk/axig==");
            nodePlayer.setNodePlayerDelegate(this);
            nodePlayer.setPlayerView(playSurface);

            /**
             * 设置播放直播视频url
             */
            nodePlayer.setInputUrl(streamURL);

            /**
             * 设置启动缓冲区时长,单位毫秒.此参数关系视频流连接成功开始获取数据后缓冲区存在多少毫秒后开始播放
             */
            nodePlayer.setBufferTime(bufferTime);

            /**
             * 设置最大缓冲区时长,单位毫秒.此参数关系视频最大缓冲时长.
             * RTMP基于TCP协议不丢包,网络抖动且缓冲区播完,之后仍然会接受到抖动期的过期数据包.
             * 设置改参数,sdk内部会自动清理超出部分的数据包以保证不会存在累计延迟,始终与直播时间线保持最大maxBufferTime的延迟
             */
            nodePlayer.setMaxBufferTime(maxBufferTime);

            /**
             *
             开启硬件解码,支持4.3以上系统,初始化失败自动切为软件解码,默认开启.
             */
            nodePlayer.setHWEnable(autoHA);

            /**
             * 设置连接超时时长,单位毫秒.默认为0 一直等待.
             * 连接部分RTMP服务器,握手并连接成功后,当播放一个不存在的流地址时,会一直等待下去.
             * 如需超时,设置该值.超时后返回1006状态码.
             */
            // np.setConnectWaitTimeout(10*1000);

            /**
             * @brief rtmpdump 风格的connect参数
             * Append arbitrary AMF data to the Connect message. The type must be B for Boolean, N for number, S for string, O for object, or Z for null.
             * For Booleans the data must be either 0 or 1 for FALSE or TRUE, respectively. Likewise for Objects the data must be 0 or 1 to end or begin an object, respectively.
             * Data items in subobjects may be named, by prefixing the type with 'N' and specifying the name before the value, e.g. NB:myFlag:1.
             * This option may be used multiple times to construct arbitrary AMF sequences. E.g.
             */
            // np.setConnArgs("S:info O:1 NS:uid:10012 NB:vip:1 NN:num:209.12 O:0");

            /**
             * 设置RTSP使用TCP传输模式
             * 支持的模式有:
             * NodePlayer.RTSP_TRANSPORT_UDP
             * NodePlayer.RTSP_TRANSPORT_TCP
             * NodePlayer.RTSP_TRANSPORT_UDP_MULTICAST
             * NodePlayer.RTSP_TRANSPORT_HTTP
             */
            nodePlayer.setRtspTransport(rtspTransport);

            /**
             * 在本地开起一个RTMP服务,并进行监听播放,局域网内其他手机或串流器能推流到手机上直接进行播放,无需中心服务器支持
             * 播放的ip可以是本机IP,也可以是0.0.0.0,但不能用127.0.0.1
             * app/stream 可加可不加,只要双方匹配就行
             */
            // np.setLocalRTMP(true);
            nodePlayer.start();
        }

        private int getPreferenceValue(String key, String defValue) {
            String value = sp.getString(key, defValue);
            return Integer.parseInt(value);
        }

        private boolean getPreferenceValue(String key, boolean defValue) {
            return sp.getBoolean(key, defValue);
        }


        @Override
        public void onDestroy() {
            /**
             * 停止播放
             */
            nodePlayer.stop();

            /**
             * 释放资源
             */
            nodePlayer.release();
            super.onDestroy();
        }

        /**
         * 事件回调
         *
         * @param nodePlayer 对象
         * @param event      事件状态码
         * @param msg        事件描述
         */
        @Override
        public void onEventCallback(NodePlayer nodePlayer, int event, String msg) {
            Log.e("onEventCallback:" + event + " msg:" + msg);
            handler.sendEmptyMessage(event);
        }

        private Handler handler = new Handler() {
            // 回调处理
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1000:
                        // 正在连接视频
//                        ToastUtils.show(mContext, mContext.getString(R.string.toast_1000));
                        break;
                    case 1001:
                        // 视频连接成功
//                        ToastUtils.show(mContext, mContext.getString(R.string.toast_1001));
                        isStarting = true;
                        break;
                    case 1002:
                        // 视频连接失败 流地址不存在，或者本地网络无法和服务端通信，回调这里。5秒后重连， 可停止
//                      ToastUtils.show(mContext, mContext.getString(R.string.toast_1002));
                        break;
                    case 1003:
                        // 视频开始重连,自动重连总开关
//                       ToastUtils.show(mContext, mContext.getString(R.string.toast_1003));
                        break;
                    case 1004:
                        // 视频播放结束
//                       ToastUtils.show(mContext, mContext.getString(R.string.toast_1004));
                        isStarting = false;
                        break;
                    case 1005:
                        // 网络异常,播放中断,播放中途网络异常，回调这里。1秒后重连，如不需要，可停止
//                        ToastUtils.show(mContext, mContext.getString(R.string.toast_1005));
                        break;

                }
            }
        };
    }
}
