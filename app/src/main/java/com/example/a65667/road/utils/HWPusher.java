package com.example.a65667.road.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.a65667.road.activity.HWResultActivity;
import com.example.a65667.road.bean.JSON_hw_service;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HWPusher {
    private static String token = "MIIa5AYJKoZIhvcNAQcCoIIa1TCCGtECAQExDTALBglghkgBZQMEAgEwghj2BgkqhkiG9w0BBwGgghjnBIIY43sidG9rZW4iOnsiZXhwaXJlc19hdCI6IjIwMTktMTEtMDdUMTI6MjM6MDUuNDkxMDAwWiIsIm1ldGhvZHMiOlsicGFzc3dvcmQiXSwiY2F0YWxvZyI6W10sInJvbGVzIjpbeyJuYW1lIjoidGVfYWRtaW4iLCJpZCI6IjAifSx7Im5hbWUiOiJ0ZV9hZ2VuY3kiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9laXBfaXB2NiIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3Jkc19tY3MiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3Nfc3BvdF9pbnN0YW5jZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2l2YXNfdmNyX3ZjYSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2llZl9ub2RlZ3JvdXAiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jY2lfbW91bnRvYnNwb3NpeCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Vjc19hc2NlbmRfa2FpMSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Nlc19hZ3QiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9kYnNfcmkiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9ibXNfaHBjX2gybGFyZ2UiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9ldnNfZXNzZCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2lvZHBzIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYmF0Y2hfZWNzX2NsdXN0ZXIiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3NfZ3B1X3YxMDAiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jYnNfcWkiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9kd3NfcG9jIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZXJzIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfbWVldGluZ19lbmRwb2ludF9idXkiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9tZWVldGluZ193aGl0ZWJvYXJkX2J1eSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Zjc19CaW90ZWNoIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZWNzcXVpY2tkZXBsb3kiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9WSVNfSW50bCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Vjc19ncHVfcDJzIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZXZzX3ZvbHVtZV9yZWN5Y2xlX2JpbiIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3ZjYyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2RwcCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX25vc3FsX2NyZWF0ZUNhc3NhbmRyYSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX29jc21hcnRjYW1wdXMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9zaXNfYXNyX2xvbmciLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3NfcmVjeWNsZWJpbiIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX21lZXRpbmdfaGFyZGFjY291bnRfYnV5IiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfbXVsdGlfYmluZCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX25scF9tdCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX21lZWV0aW5nX2N1cnJlbnRfYnV5IiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfaWVmX2Z1bmN0aW9uIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZmluZV9ncmFpbmVkIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfcHJvamVjdF9kZWwiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9tNm10IiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZXZzX3JldHlwZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2FhZF9mcmVlIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfcmRzX3BnOTQiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lbGJfZ3VhcmFudGVlZCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2FfY24tc291dGh3ZXN0LTJiIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfc2ZzdHVyYm8iLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9odl92ZW5kb3IiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9tcnNfYXJtX3JjMyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Vjc19oaTMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9hX2NuLW5vcnRoLTRlIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYV9jbi1ub3J0aC00ZCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Vjc19ncHVfcDQiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9yZHNpMyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX0lFQyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3RhcyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3NlcnZpY2VzdGFnZV9tZ3JfZHRtIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYV9jbi1ub3J0aC00ZiIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2lvdGVkZ2VfYmFzaWMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jcGgiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9Nb2RlbEFydHNBc2NlbmQ5MTAiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9tZWV0aW5nX2hpc3RvcnlfY3VzdG9tX2J1eSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Ric3NfZnJlZXRyYWlsIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfd3MiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9zZHdhbiIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Nzc19hcm0iLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3NfZ3B1X2c1ciIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX25vc3FsX2NyZWF0ZVJlZGlzIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZWxiX21pZ3JhdGUiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9pb3RlZGdlX2NhbXB1cyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2VsYl9sb2dfb2FtIiwiaWQiOiIwIn0seyJuYW1lIjoib";

    public void push(File file, Context context) {
        String url = "https://iam.cn-north-4.myhuaweicloud.com/v3/auth/tokens";
        String url_service = "https://10473aa4ece145a2adbc1a86948c31f2.apigw.cn-north-4.huaweicloud.com/v1/infers/6494284c-43d9-423b-b6e7-9a16708f3e17";

        String data = "{\n" +
                "            \"auth\": {\n" +
                "            \"identity\": {\n" +
                "                \"methods\": [\n" +
                "                \"password\"\n" +
                "          ],\n" +
                "                \"password\": {\n" +
                "                    \"user\": {\n" +
                "                        \"name\": \"wmpscc\",\n" +
                "                                \"password\": \"20@qq.com\",\n" +
                "                                \"domain\": {\n" +
                "                            \"name\": \"wmpscc\"\n" +
                "                        }\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"scope\": {\n" +
                "                \"project\": {\n" +
                "                    \"name\": \"cn-north-4\"\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "       }";

        OkGo.<String>post(url)
                .tag(this)
                .headers("Content-Type", "application/x-www-form-urlencoded")
                .upString(data)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        token = response.headers().get("x-subject-token");
                        Log.e("hw_token", token + "");

                        List<File> files = new ArrayList<>();
                        files.add(file);
                        OkGo.<String>post(url_service)
                                .tag(this)
                                .isMultipart(true)
                                .headers("X-Auth-Token", token)
                                .addFileParams("images", files)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        JSON_hw_service hw = JSON.parseObject(response.body(), JSON_hw_service.class);
                                        Log.e("crack_weight", hw.getCrack_weight() + "");
                                        Intent intent = new Intent(context, HWResultActivity.class);
                                        intent.putExtra("weight", hw.getCrack_weight());
                                        intent.putExtra("gt", hw.getGt());

                                        context.startActivity(intent);

                                    }
                                });
                    }
                });
    }


}
