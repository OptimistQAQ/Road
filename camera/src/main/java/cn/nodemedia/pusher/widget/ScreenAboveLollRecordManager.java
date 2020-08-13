package cn.nodemedia.pusher.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.Surface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

/**
 * 5.0屏幕录制
 */
@SuppressWarnings("ALL")
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ScreenAboveLollRecordManager {

    private static final int REQUEST_CODE = 12580;
    private MediaProjectionManager mMediaProjectionManager;
    private ScreenRecorderThread mScreenRecorderThread;
    private int mScreenWidth;
    private int mScreenHeight;
    private MediaProjection mMediaProjection;
    private MediaCodec mMediaCodec;
    private final MediaCodec.BufferInfo mBufferInfo = new MediaCodec.BufferInfo();
    private VirtualDisplay mVirtualDisplay;
    private RandomAccessFile mH264DataFile = null;
    private String path = "/video_";
    private Integer a = 0;


    public ScreenAboveLollRecordManager(Activity activity, int width, int height) {
        if (activity != null) {
            mScreenWidth = width;
            mScreenHeight = height;
            mMediaProjectionManager = (MediaProjectionManager) activity
                    .getSystemService(Context.MEDIA_PROJECTION_SERVICE);
            File file = new File(Environment.getExternalStorageDirectory().getPath()
                    + path + a.toString() + ".264");
            a++;
            Log.e("file_path", file.toString());
            try {
                mH264DataFile = new RandomAccessFile(file, "rw");
            } catch (FileNotFoundException e) {
            }
        }
    }

    //退出线程，停止录制
    public void destroy() {
        mIsRun = false;
        mMediaProjectionManager = null;
    }

    //开始屏幕录制
    public void startScreenRecorde(Activity activity ) {
        if (mMediaProjectionManager != null ) {
            if (activity != null) {
                Intent intent = mMediaProjectionManager.createScreenCaptureIntent();
                activity.startActivityForResult(intent, REQUEST_CODE);
            }
        }
    }

    /*
     * 在Acitvity的onActivityResult调用
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_CODE) {
            return;
        }

        if (mScreenRecorderThread != null || mIsRun) {
            // 5.0的编码已经开启了
            // activity被销毁了
            return;
        }


        if (resultCode == Activity.RESULT_OK) {
            mMediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
            if (mMediaProjection == null) {
// TODO 通知启动失败
                return;
            }
            mIsRun = true;
            mScreenRecorderThread = new ScreenRecorderThread();
            mScreenRecorderThread.start();
// TODO 通知开始屏幕录制
        } else {
// TODO 通知启动失败
        }
    }

    //录制视频线程
    private class ScreenRecorderThread extends Thread {
        @Override
        public void run() {
            super.run();
            createMediaCodec(mMediaProjection);
            recordVirtualDisplay();
            release();
        }
    }

    //创建编码器
    private void createMediaCodec(MediaProjection mediaProjection) {
        if (mediaProjection != null) {
            MediaCodecInfo codecInfo = selectCodec();
            if (codecInfo == null) {
                return;
            }


            MediaCodecInfo.CodecCapabilities capabilities = codecInfo
                    .getCapabilitiesForType(MediaFormat.MIMETYPE_VIDEO_AVC);
            MediaCodecInfo.CodecProfileLevel[] profileLevels = capabilities.profileLevels;


            MediaFormat format = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC,
                    mScreenWidth, mScreenHeight);
// 设置颜色格式
            format.setInteger(MediaFormat.KEY_COLOR_FORMAT,
                    MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
// TODO 可根据不同分辨率设置比特率
            format.setInteger(MediaFormat.KEY_BIT_RATE, 4000000);
// 设置帧率
            format.setInteger(MediaFormat.KEY_FRAME_RATE, 10);
// 设置关键帧间隔时间，单位s
            format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1);

            format.setInteger(MediaFormat.KEY_PROFILE,
                    MediaCodecInfo.CodecProfileLevel.AVCProfileBaseline);
            int maxCodecProfileLevel = MediaCodecInfo.CodecProfileLevel.AVCLevel1;
            for (MediaCodecInfo.CodecProfileLevel codecProfileLevel : profileLevels) {
                if (codecProfileLevel.profile != MediaCodecInfo.CodecProfileLevel.AVCProfileBaseline) {
                    continue;
                }
                if (codecProfileLevel.level > maxCodecProfileLevel) {
                    maxCodecProfileLevel = codecProfileLevel.level;
                }
            }
            format.setInteger("level", maxCodecProfileLevel);
            try {
// 实例化一个支持给定MIME类型的数据输出的编码器
                mMediaCodec = MediaCodec.createByCodecName(codecInfo.getName());
// 配置好格式参数
                mMediaCodec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
// 请求一个surface用于编码器的输入
                Surface encoderSurface = mMediaCodec.createInputSurface();
                mVirtualDisplay = mediaProjection.createVirtualDisplay("display", mScreenWidth,
                        mScreenHeight, 1, DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC,
                        encoderSurface, null, null);
                mMediaCodec.start();
            } catch (Exception e) {
            }
        }
    }

    //选择编码器级别
    @SuppressWarnings("deprecation")
    private static MediaCodecInfo selectCodec() {
        int numCodecs = MediaCodecList.getCodecCount();
        for (int i = 0; i < numCodecs; i++) {
            MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
            if (!codecInfo.isEncoder()) {
                continue;
            }
            String[] types = codecInfo.getSupportedTypes();
            for (String type : types) {
                if (MediaFormat.MIMETYPE_VIDEO_AVC.equalsIgnoreCase(type)) {
                    return codecInfo;
                }
            }
        }
        return null;
    }


    private boolean mIsRun = false;
    private boolean mIsOnStart = true;

    //录制虚拟视频
    @SuppressWarnings("deprecation")
    private void recordVirtualDisplay() {
        while (mIsRun) {
            try {
                if (mMediaCodec == null) {
                    return;
                }
                ByteBuffer[] encoderOutputBuffers = mMediaCodec.getOutputBuffers();
                while (mIsRun && mIsOnStart) {
                    int encoderStatus = mMediaCodec.dequeueOutputBuffer(mBufferInfo, 0);
                    if (encoderStatus == MediaCodec.INFO_TRY_AGAIN_LATER) {
                        break;
                    } else if (encoderStatus == MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED) {
                        encoderOutputBuffers = mMediaCodec.getOutputBuffers();
                    } else if (encoderStatus == MediaCodec.INFO_OUTPUT_FORMAT_CHANGED) {
                    } else if (encoderStatus < 0) {
                    } else {
                        if (mBufferInfo.size != 0) {
                            byte[] dataToWrite = new byte[mBufferInfo.size];
                            ByteBuffer encodedData = encoderOutputBuffers[encoderStatus];
                            encodedData.position(mBufferInfo.offset);
                            encodedData.limit(mBufferInfo.offset + mBufferInfo.size);
                            encodedData.get(dataToWrite, 0, mBufferInfo.size);
                            saveH264DataToFile(dataToWrite);
                        }
                        mMediaCodec.releaseOutputBuffer(encoderStatus, false);
                        Log.e("123", "luzhi");
                    }
                }
            } catch (Exception e) {
                Log.e("ads", e.getMessage());
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }
        }
    }

    //保存H264视频到本地
    private void saveH264DataToFile(byte[] dataToWrite) {
        try {

            mH264DataFile.write(dataToWrite, 0, dataToWrite.length);

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            Log.e("error", e.getMessage());
        }
    }

    //释放所有资源
    private void release() {
        if (mMediaCodec != null) {
            mMediaCodec.stop();
            mMediaCodec.release();
            mMediaCodec = null;
        }
        if (mVirtualDisplay != null) {
            mVirtualDisplay.release();
            mVirtualDisplay = null;
        }
        if (mMediaProjection != null) {
            mMediaProjection.stop();
            mMediaProjection = null;
        }
        mScreenRecorderThread = null;
    }
}
