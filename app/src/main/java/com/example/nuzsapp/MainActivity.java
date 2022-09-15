package com.example.nuzsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsListAdaptor.NewsItemClicked {

    RecyclerView recyclerView;
    NewsListAdaptor mAdaptor;

//    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
//        lottieAnimationView=findViewById(R.id.loading_lottie);

       // LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
        fetchData();
        mAdaptor= new NewsListAdaptor(getApplicationContext(),this);

        recyclerView.setAdapter(mAdaptor);

    }

    void fetchData(){
        String url="https://saurav.tech/NewsAPI/top-headlines/category/general/in.json";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                       JSONArray newsJsonArray=response.getJSONArray("articles");
                            ArrayList<News> newsArray=new ArrayList<>();

                            for(int i=0; i<newsJsonArray.length();i++){
                                JSONObject newsJsonObj= newsJsonArray.getJSONObject(i);
                                News news=new News(newsJsonObj.getString("author"),newsJsonObj.getString("title"),newsJsonObj.getString("url"),newsJsonObj.getString("urlToImage"));
                                newsArray.add(news);

                            }
                            mAdaptor.updateNews(newsArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MainActivity.this, "Some error occur", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "error" + error, Toast.LENGTH_SHORT).show();

                    }
                });

        queue.add(jsonObjectRequest);
    }


    @Override
    public void onItemClicked(News item) {

        String url = item.url;
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));

    }
}