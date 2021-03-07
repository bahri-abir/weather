package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private ImageButton btn_search;
    private EditText input_search;
    private String input_text;
    private TextView address,updated_at,temp,temp_min,temp_max,statu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_search=findViewById(R.id.btn_search);
        input_search=findViewById(R.id.title_search);
        address=findViewById(R.id.address);
        updated_at=findViewById(R.id.updated_at);
        temp=findViewById(R.id.temp);
        temp_min=findViewById(R.id.temp_min);
        temp_max=findViewById(R.id.temp_max);
        statu=findViewById(R.id.status);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_text = input_search.getText().toString();
                if (input_text.isEmpty())
                    Toast.makeText(MainActivity.this, "Complete form .... !", Toast.LENGTH_SHORT).show();
                else
                {
                    searchMeteo(input_text);
                }
            }
        });
    }

    private void searchMeteo(String country_title) {
        String apikey="00a7037d9b1489dfec3c0b0379260a98";
        String url_api="http://api.openweathermap.org/data/2.5/weather?q="+country_title+"&appid="+apikey;
        Ion.with(MainActivity.this)
                .load(url_api)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        if(e != null)
                        {
                            Toast.makeText(MainActivity.this, "Error ", Toast.LENGTH_SHORT).show();
                            Log.e("TAG", "onCompleted: ERROR = "+e.getMessage() );
                        }
                        else
                        {
//
                            JsonArray weatherjson=result.get("weather").getAsJsonArray();
                            JsonObject ele_0=weatherjson.get(0 ).getAsJsonObject();
                            String description=ele_0.get("description").getAsString();

                            address.setText(result.get("name").getAsString());
                            //statu.setText(result.get("description").getAsString());


                          updated_at.setText("Updated at  : "+result.get("timezone").getAsString());
                            JsonObject mainjson=result.get("main").getAsJsonObject();
                            JsonObject windjson=result.get("wind").getAsJsonObject();
                            //JsonObject updatedAt=result.get("dt").getAsJsonObject();
                           temp.setText(mainjson.get("temp").getAsString()+"°C");



                          temp_min.setText("temp Min :"+mainjson.get("temp_min").getAsString()+"°C");

                            temp_max.setText("temp Max :"+mainjson.get("temp_max").getAsString());
                            Log.d("TAG", "onCompleted: "+result);



                        }

                    }
                });



    }
}