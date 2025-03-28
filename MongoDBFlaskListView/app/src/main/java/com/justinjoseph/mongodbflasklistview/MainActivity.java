package com.justinjoseph.mongodbflasklistview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.PixelCopy;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RequestQueue queue;
    private Gson gson;
    protected ListView list;
    protected String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        url = getString(R.string.url_string) + "/all";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        list = (ListView) findViewById(R.id.listView);
        List<Customer> customers = new ArrayList<>();

        queue = Volley.newRequestQueue(this);
        queue.start();

        // Iterate through the request response to get each customer
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null, response -> {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        Log.d(".onResponse", "onResponse start");
                        Customer cus = gson.fromJson(response.getJSONObject(i).toString(), Customer.class);
                        customers.add(cus);
                        Log.d(".onResponse", "Added customer " + cus.getName());
                    } catch (JSONException e) {
                        Log.d(".onResponse", "JSONArray error :(");
                        e.printStackTrace();
                    } catch (Exception e) {
                        Log.d("./MainActivity/jsonArrayRequest", "Some other error?");
                    }
                }
                CustomerAdapter adapter = new CustomerAdapter(customers, list.getContext());

//                view.setOnClickListener(v -> {
//    //            View commentView = LayoutInflater.from(context).inflate(R.layout.);
//                    Intent intent = new Intent(this.context, AddCustomerActivity.class);
//                    startActivity(intent);
//                    Log.d(".CustomerAdapter.onClick", "started activity");
//                });
                list.setAdapter(adapter);
                list.setOnItemClickListener((parent, view, position, id) -> {
                    Intent intent = new Intent(MainActivity.this, CommentsActivity.class);
                    intent.putExtra("customer", customers.get(position));
                    startActivity(intent);
                    Log.d(".CustomerAdapter.onClick", "started activity");

                });
        }, error -> {
            error.printStackTrace();
                Log.d("./MainActivity/ERROR", "Error loading content.");
        });
        queue.add(jsonArrayRequest);

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCustomerActivity.class);
                startActivity(intent);
                Log.d(".MainActivity.onClick", "started activity");
            }
        });
    }

    public void drawPopUpWindow(View v, LayoutInflater inflater) {
        //Draw a pop up window
        View popupView = inflater.inflate(R.layout.edit_customer_popup, null);
        PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(this.getCurrentFocus(), Gravity.CENTER,0,0);//

        // Functionality for add and cancel buttons
        ImageButton add_comment = popupView.findViewById(R.id.add_comment_button);
        ImageButton delete_comment = popupView.findViewById(R.id.delete_comment_button);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MainActivity.onRestart", "onRestart called");
        recreate();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity.onStop", "onStop called");
        queue.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity.onPause", "onPause called");
        queue.stop();
    }
}