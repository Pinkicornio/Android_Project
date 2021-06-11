package pk.shop;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ClassCallPhpRegister extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String[] params) {
        HttpURLConnection conn = null;
        JSONObject jObj = null;
        try{
            jObj = new JSONObject();
            try
            {

                //jObj.put("usr", "jbm1USR");
                jObj.put("usr", params[1]);
                jObj.put("email", params[2]);
                jObj.put("pass", params[3]);
                jObj.put("date", params[4]);
                jObj.put("rol",params[5]);
                //jObj.put("date", params[3]);
                //jObj.put("rol", params[4]);
            } catch (JSONException e) {  Log.e("HECHO",e.getMessage()+"1");}
            URL url= new URL(params[0]);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(jObj.toString());
            wr.flush();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                InputStream is = conn.getInputStream();
            }
            else
            {
                InputStream err = conn.getErrorStream();
            }
            Log.e("HECHO","sss");
            return "Done";



        } catch (MalformedURLException e) {
            Log.e("HECHO",e.getMessage()+"2");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("HECHO",e.getMessage()+"3");
            e.printStackTrace();
        }
        finally
        {
            if (conn != null)
            {
                conn.disconnect();
            }
        }
        Log.e("HECHO","final");
        return null;
    }
}
