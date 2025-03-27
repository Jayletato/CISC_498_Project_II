package com.justinjoseph.mongodbflasklistview;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class AddCustomerActivity extends AppCompatActivity {
    protected String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_customer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        url = getString(R.string.url_string) + "/add";

        RequestQueue queue = Volley.newRequestQueue(this);

        EditText name = findViewById(R.id.enter_name);
        EditText address = findViewById(R.id.enter_address);
        EditText phone = findViewById(R.id.enter_phone);

        Button confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queue.start();
                EditText[] editTexts = {name, address, phone};
                String name_string;
                String address_string;
                String phone_string;

                // Makes sure editTexts are valid. If not, stop this onClick early and do nothing.
                if(!isTextValid(editTexts[0], "name")) {
                    return;
                }
                if(!isTextValid(editTexts[1], "address")) {
                    return;
                }
                if(!isTextValid(editTexts[2], "phone")){
                    return;
                }

                // Create JsonArray to send to server and JsonObject to insert into the Array
                JSONObject data = new JSONObject();

                try {
                    data.put("name", name.getText().toString());
                    data.put("address", address.getText().toString());
                    data.put("phone", phone.getText().toString());
                } catch (Exception e) {
                    Log.d(".AddCustomerActivity.java", "Error adding customer. Terminating request.");
                    return;
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(".AddCustomerActivity.onClick", "Success adding content");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(".AddCustomerActivity.onClick", "Error loading content.");
                    }
                });
                queue.add(jsonObjectRequest);

                finish();

            }
        });

        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // Checks to make sure an EditText field is valid and not empty
    protected boolean isTextValid(EditText editText, String name){
        try {
            String text = editText.getText().toString();
            if (text.isEmpty()){
                Toast.makeText(this, String.format("Text field %s is empty!", name), Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        } catch(Exception e) {
            Toast.makeText(this,"Invalid text for: " + name, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}