package com.example.a65667.road.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.example.a65667.road.BuildConfig;
import com.example.a65667.road.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class VideoActivity extends Activity {

    private SurfaceView mSurfaceView;
    private Button mReadButton;
    private MediaCodec mCodec;
    Thread readFileThread;
    boolean isInit = false;

    // Video Constants
    private final static String MIME_TYPE = "video/avc"; // H.264 Advanced Video
    private static int VIDEO_WIDTH = 1024;
    private static int VIDEO_HEIGHT = 600;
    private final static int TIME_INTERNAL = 30;
    private final static int HEAD_OFFSET = 512;
    private final static String LOG_TAG = "asir";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mReadButton = (Button) findViewById(R.id.btn_readfile);
        mReadButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isInit) {
                    initDecoder();
                    isInit = true;
                }

                readFileThread = new Thread(readFile);
                readFileThread.start();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        readFileThread.interrupt();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void initDecoder() {
        try {
            mCodec = MediaCodec.createDecoderByType(MIME_TYPE);
            MediaFormat mediaFormat = MediaFormat.createVideoFormat(MIME_TYPE, VIDEO_WIDTH, VIDEO_HEIGHT);
            mCodec.configure(mediaFormat, mSurfaceView.getHolder().getSurface(), null, 0);
            mCodec.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int mCount = 0;
//    final byte[] h264Split = {0, 0, 0, 1};

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean onFrame(byte[] buf, int length) {
        Log.e(LOG_TAG, "onFrame start");
        // Get input buffer index
        long ptsUsec = 0;

        if (mCount == 0) {
            ByteBuffer[] inputBuffers = mCodec.getInputBuffers();
            int inputBufferIndex = mCodec.dequeueInputBuffer(100000);

            Log.e(LOG_TAG, "onFrame index:" + inputBufferIndex);
            if (inputBufferIndex >= 0) {
                ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
                inputBuffer.clear();
                inputBuffer.put(buf, 0, length).rewind();
                mCodec.queueInputBuffer(inputBufferIndex, 0, length, 0, MediaCodec.BUFFER_FLAG_CODEC_CONFIG);
            } else {
                Log.e(LOG_TAG, "inputBufferIndex < 0 " + inputBufferIndex);
                return false;
            }
        } else {
            ByteBuffer[] inputBuffers = mCodec.getInputBuffers();
            int inputBufferIndex = mCodec.dequeueInputBuffer(100000);

            Log.e(LOG_TAG, "onFrame index:" + inputBufferIndex);
            if (inputBufferIndex >= 0) {
                inputBuffers[inputBufferIndex].clear();
                inputBuffers[inputBufferIndex].put(buf, 0, length).rewind();
                mCodec.queueInputBuffer(inputBufferIndex, 0, length, ptsUsec, 0);
            } else {
                Log.e(LOG_TAG, "inputBufferIndex < 0 " + inputBufferIndex);
                return false;
            }
        }
        mCount++;
        // Get output buffer index
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        int outputBufferIndex = mCodec.dequeueOutputBuffer(bufferInfo, 10000);
        while (outputBufferIndex >= 0) {
            mCodec.releaseOutputBuffer(outputBufferIndex, true);
            outputBufferIndex = mCodec.dequeueOutputBuffer(bufferInfo, 0);
        }
        Log.e(LOG_TAG, "onFrame end");
        return true;
    }

    /**
     * Find H264 frame head
     *
     * @param buffer
     * @param len
     * @return the offset of frame head, return 0 if can not find one
     */
    static int findHead(byte[] buffer, int len) {
        int i;
        for (i = HEAD_OFFSET; i < len; i++) {
            if (checkHead(buffer, i))
                break;
        }
        if (i == len)
            return 0;
        if (i == HEAD_OFFSET)
            return 0;
        return i;
    }

    /**
     * Check if is H264 frame head
     *
     * @param buffer
     * @param offset
     * @return whether the src buffer is frame head
     */
    static boolean checkHead(byte[] buffer, int offset) {
        // 00 00 00 01
        if (buffer[offset] == 0 && buffer[offset + 1] == 0
                && buffer[offset + 2] == 0 && buffer[3] == 1)
            return true;
        // 00 00 01
        if (buffer[offset] == 0 && buffer[offset + 1] == 0
                && buffer[offset + 2] == 1)
            return true;
        return false;
    }

    Runnable readFile = new Runnable() {
        @Override
        public void run() {
            boolean readFlag = true;
            int h264Count = 0;
            while (!Thread.interrupted() && readFlag) {
                if (h264Count > 300) {
                    try {
                        Thread.sleep(20);
                        h264Count = 0;
                        continue;
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "Exception for: " + e.getMessage());
                    }
                }
                BufferedInputStream bis = null;
                String sdFileName = "/sdcard/" + "video_0" + ".264";
                String assetsFileName = "video_0" + ".264";
                Log.e(LOG_TAG, "Read from fileName: " + sdFileName);

//                FileInputStream fos = null;
                try {
                    File h264 = new File(sdFileName);
                    if (!h264.exists()) {
                        Log.e(LOG_TAG, "File isn't exist: " + sdFileName);
                        return;
                    }
// 读取SD卡
//                    fos = new FileInputStream(h264);
//                    bis = new BufferedInputStream(fos);
// 读取assets目录

                    InputStream is = getResources().getAssets().open(assetsFileName);
                    bis = new BufferedInputStream(is);

                    int fileLen = (int) h264.length();
                    byte[] buffer = new byte[fileLen];
                    try {
                        bis.read(buffer);
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "error: read from file " + e.getMessage());
                    }
                    onFrame(buffer, fileLen);
                } catch (Exception e) {
                    if (BuildConfig.DEBUG) {
                        Log.e(LOG_TAG, " Error: " + e.getMessage());
                    }
                }
                h264Count++;
            }
            Log.d(LOG_TAG, "end loop");
        }
    };
}
