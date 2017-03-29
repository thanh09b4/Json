package thanh.com.json;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ListView lv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv1 = (ListView) findViewById(R.id.listview1);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new readjson().execute("http://khoapham.vn/KhoaPhamTraining/laptrinhios/jSON/demo3.json");
            }
        });
    }
    class readjson extends AsyncTask <String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            String chuoi = docNoiDung_Tu_URL(params[0]);
            return chuoi;
        }

        @Override
        protected void onPostExecute(String s) {

           try {
               JSONObject root = new JSONObject(s);
               JSONArray mang = root.getJSONArray("danhsach");
               ArrayList<String> mangkhoahoc = new ArrayList<String>();

               for(int i = 0; i< mang.length();i++){
                   JSONObject son = mang.getJSONObject(i);
                   mangkhoahoc.add(son.getString("khoahoc"));
               }
               ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                       MainActivity.this, android.R.layout.simple_list_item_1,mangkhoahoc
               );
               lv1.setAdapter(adapter);
           }catch (JSONException e){
               e.printStackTrace();
           }
        }
    }



    private static String docNoiDung_Tu_URL(String theUrl)
    {
        StringBuilder content = new StringBuilder();

        try
        {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }

}
