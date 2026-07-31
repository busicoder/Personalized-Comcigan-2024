package com.bchs.myapplication;
import android.content.AsyncQueryHandler;
import android.content.ContentValues;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class HttpAct {
    private static HttpAct httpSingleton;
    private String BCHSurl="http://comci.net:4082/36179?NzM2MjlfNDM1ODhfMF8x";
    private TimeJSON tjson;
    public static HttpAct GetHttpST(){
        if(httpSingleton==null) {
            httpSingleton = new HttpAct();
        }
        return httpSingleton;
    }
    public void setTtable(){
        if(tjson==null)
            tjson=TimeJSON.GetSingleton();
        lastSuccess=lastProcessingSuccess=false;
        Network netTask=new Network(BCHSurl);
        netTask.execute();
    }


    private final String USER_AGENT = "Mozilla/5.0";
    public boolean lastSuccess=false;
    public boolean lastProcessingSuccess=false;
    public String GetDt(String url){
        HttpURLConnection myCon=null;
        try {
            URL gurl = new URL(url);
            myCon=(HttpURLConnection) gurl.openConnection();
            myCon.setRequestMethod("GET");
            myCon.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = myCon.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(myCon.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println("HTTP 응답 코드 : " + responseCode);
            if(responseCode!=200){
                return null;
            }
            //System.out.println("HTTP body : " + response.toString());
            return response.toString();
        }catch(MalformedURLException e){
            System.out.println(e);
        }catch(IOException e2){
            System.out.println(e2);
        }finally {
            if(myCon!=null){
                myCon.disconnect();
            }
        }
        return null;
    }
    private class Network extends AsyncTask<Void, Void, String>{
        private JSONObject timetableraw;
        private String url;
        private stInfo stDt;
        public Network(String _url){
            url=_url;
        }
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            HttpAct httpact = new HttpAct();
            result = httpact.GetDt(this.url); // 해당 URL로 부터 결과물을 얻어온다.
            if(result==null){
                lastSuccess=false;
                return null;
            }
            lastSuccess=true;
            String return_=null;
            if(result.lastIndexOf('}')>0){
                return_=result.substring(0,result.lastIndexOf('}')+1);
            }
            System.out.println("test");
            return return_;
        }//Network 분리 후 new로 할당후 메모리 해제식 운영 필요

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println(s.length());
            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            if(s==null){
                System.out.println("응답 없음");
                return;
            }
            try {
                timetableraw = new JSONObject(s);
                System.out.println("tt setup try");
                tjson.setTtable(timetableraw);
                System.out.println("tt setup end");
            }catch(JSONException e){
                System.out.println(e);
            }
            if(stDt==null)
                stDt=stInfo.GetstInfoClass();
            if(stDt.hasFile){
                stDt.setMyTable();
            }else{
                stDt.Grade=3;
                stDt.Ban=1;
                System.out.println("list 대입 성공");
                stDt.setMyTable();
            }
            lastProcessingSuccess=true;

            //창 여러개 만들어서 데이터 저장 로드 클래스 만들고 데이터 존재하면 그걸로 시간표 부르고 설정에서도 데이터 변경할 수 있게
        }
    }
}
