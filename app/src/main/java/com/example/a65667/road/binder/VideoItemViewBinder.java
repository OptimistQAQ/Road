package com.example.a65667.road.binder;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.example.a65667.road.Item.VideoItem;
import com.example.a65667.road.R;
import com.example.a65667.road.bean.LocalSaveGPSPointJson;
import com.example.a65667.road.utils.SaveAndLoadLocalFile;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import cn.jzvd.JzvdStd;
import cn.nodemedia.pusher.GPSPoint;
import cn.nodemedia.pusher.ShareBean;
import me.drakeet.multitype.ItemViewBinder;

public class VideoItemViewBinder extends ItemViewBinder<VideoItem, VideoItemViewBinder.ViewHolder> {

    private View root;
    private VideoItem videoItem;
    private TextView fileName;
    private Button btnDelete, btnShangchuan;

    private String keyFileName;

    private JzvdStd jzvdStd;
    private String videoUrl = "http://ishero.net/share/valvideo/ccd901f5-bfabffd7.mov";
    private String video_url = "https://road-oss.oss-cn-beijing.aliyuncs.com/test.mp4";
    private static OSS oss;
    private String path = "";

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        root = inflater.inflate(R.layout.item_video_item, parent, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        initView();
        initOSS();
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull VideoItem item) {
        holder.setIsRecyclable(false);
        this.videoItem = item;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        fileName.setText(format.format(item.getDate()));

        jzvdStd.setUp(item.getFileUrl(), "路面回放", JzvdStd.SCREEN_NORMAL);
        path = item.getFileUrl();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnShangchuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveAndLoadLocalFile saveAndLoadLocalFile = new SaveAndLoadLocalFile(root.getContext());
                String data = saveAndLoadLocalFile.load(item.getFileName().replace(".mp4", ".json"));
                upload(item.getFileUrl(), item.getFileName());

                pushChange(item.getFileName());
                List<GPSPoint> localSaveGPSPointJson = JSON.parseArray(data, GPSPoint.class);
                for (int i = 0; i < localSaveGPSPointJson.size(); i++) {

                    Log.e("read_json", localSaveGPSPointJson.size() + "---" + localSaveGPSPointJson.get(i).getLatitude() + "");
                    OkGo.<String>post("http://ishero.net:5000/record_location")
                            .params("Uno", "" + ShareBean.uno)
                            .params("Lno", "" + ShareBean.uno + "_" + ShareBean.utotalLine)
                            .params("Lon", localSaveGPSPointJson.get(i).getLongitude())
                            .params("Lat", localSaveGPSPointJson.get(i).getLatitude())
                            .params("Seconds", i)
                            .tag(this)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.e("upload_f", "success upload gps to server");

                                }
                            });

                }

            }
        });
    }

    private void pushChange(String filename) {


        String url = "https://road-oss.oss-cn-beijing.aliyuncs.com/" + filename;


        OkGo.<String>post("http://ishero.net:5000/online_process") // 开始新的线路检测
                .params("Uno", "" + ShareBean.uno)
                .params("Lno", "" + ShareBean.uno + "_" + ShareBean.utotalLine)
                .params("url", url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("upload_f", "success upload line to server");
                    }
                });

    }

    private void initView() {
        fileName = root.findViewById(R.id.file_name);
        btnDelete = root.findViewById(R.id.btnDelete);
        btnShangchuan = root.findViewById(R.id.btnShangchuan);
        jzvdStd = root.findViewById(R.id.file_video);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @SuppressLint("NewApi")
    private void initOSS() {
        String endpoint = "oss-cn-beijing.aliyuncs.com";

        String stsServer = "http://ishero.net:5000/auth/sts";
// 推荐使用OSSAuthCredentialsProvider。token过期可以及时更新。
        OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(stsServer);

// 配置类如果不设置，会有默认配置。
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒。
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒。
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个。
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次。

        oss = new OSSClient(root.getContext(), endpoint, credentialProvider);

        OSSLog.enableLog();
    }

    private void upload(String local, String ObjectName) {
        // 构造上传请求。
        PutObjectRequest put = new PutObjectRequest("road-oss", ObjectName,
                local);

// 异步上传时可以设置进度回调。
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常。
                if (clientExcepion != null) {
                    // 本地异常，如网络异常等。
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常。
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
// task.cancel(); // 可以取消任务。
// task.waitUntilFinished(); // 等待上传完成。
    }
}
