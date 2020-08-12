package com.example.networktest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendBtn = findViewById(R.id.sendRequestBtn);
        textView = findViewById(R.id.responseText);



        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendRequestWithHttpURLConnection();  // 使用HttpURLConnection
                sendRequestWithOkHttp();  // 使用OkHttp
            }
        });
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("username", "admin")
                        .add("password", "123456")
                        .build();

                Request request = new Request.Builder()
                        .url("http://192.168.9.103/get_data.xml")
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    if (responseData != null) {
//                        showResponse(responseData);
                        parseXMLWithPull(responseData);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void pareseJSONWithJSONObject(String jsonData) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonData);
        for (int i = 0; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String version = jsonObject.getString("version");
            Log.d("MainActivity", "id"+id);
            Log.d("MainActivity", "name"+name);
            Log.d("MainActivity", "version"+version);

        }
    }

    private void parseXMLWithPull(String xmlData) {
        try {
            // 获取XmlPullParserFactory实例
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            // 获取XmlPullParser对象
            XmlPullParser xmlPullParser = factory.newPullParser();
            // 将获取的 XML数据设置进去
            xmlPullParser.setInput(new StringReader(xmlData));
            // 得到当前解析的事件getEventType()
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != xmlPullParser.END_DOCUMENT) {  // 判断解析工作是否完成
                String nodeName = xmlPullParser.getName();  // 获取当前节点的名字
                switch (eventType) {
                    // 开始解析某个节点
                    case XmlPullParser.START_TAG :
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();  // 获取节点的具体内容
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    // 完成解析某个点
                    case XmlPullParser.END_TAG:
                        if ("app".equals(nodeName)) {
                            Log.d("MainActivity","id:"+id);
                            Log.d("MainActivity","name:"+name);
                            Log.d("MainActivity","version:"+version);
                        }
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next();  // 获取下一个解析事件
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendRequestWithHttpURLConnection() {
        // 开启线程发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("https://www.baidu.com");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);
                    InputStream input = connection.getInputStream();
                    // 下面将获取的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    showResponse(response.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行UI操作
                textView.setText(response);
            }
        });
    }
}
