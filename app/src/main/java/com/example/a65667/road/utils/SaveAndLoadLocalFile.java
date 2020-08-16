package com.example.a65667.road.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SaveAndLoadLocalFile {
    private Context context;
    public SaveAndLoadLocalFile(Context context){
        this.context = context;
    }

    public void save(String data, String filename){
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = context.openFileOutput(filename, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data);
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer!= null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String load (String filename){
        FileInputStream in = null;
        BufferedReader reader =  null;
        StringBuilder content = new StringBuilder();
        try {
            //获取FileInputStream对象
            in = context.openFileInput(filename);
            //借助FileInputStream对象, 构建出一个BufferedReader对象
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            //通过BufferedReader对象进行一行行的读取, 把文件中的所有内容全部读取出来
            // 并存放在StringBuilder对象中
            while ((line = reader.readLine())!= null){
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //最后将读取到的内容返回
        return  content.toString();
    }
}
