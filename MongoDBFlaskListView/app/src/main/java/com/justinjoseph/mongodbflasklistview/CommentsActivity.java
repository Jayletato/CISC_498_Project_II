package com.justinjoseph.mongodbflasklistview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class CommentsActivity extends AppCompatActivity {
    Customer customer;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comments);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.comments_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton backButton = findViewById(R.id.comment_back);
        backButton.setOnClickListener(v -> {
            finish();
//            Intent intent = new Intent(CommentsActivity.this, MainActivity.class);
//            startActivity(intent);
        });


        RequestQueue queue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        customer = intent.getSerializableExtra("customer", Customer.class);

        ListView listView = findViewById(R.id.comments_listview);
        CommentAdapter cAdapter = new CommentAdapter(customer,this);

        ArrayList<String> comments = customer.getComments();
        Log.d("./CommentsActivity", customer.getName());

        listView.setAdapter(cAdapter);

        ImageButton addCommentButton = findViewById(R.id.add_comment_button);
        EditText enterComment = findViewById(R.id.comment_text_container);
        // Add onclick for adding a comment
        addCommentButton.setOnClickListener(v -> {
//            Log.d("./CommentsActivity/addCommentButton", enterComment.getText().toString());
            if (enterComment.getText().toString().isBlank()){
                Toast.makeText(this, "Error; Comment field is blank!", Toast.LENGTH_SHORT).show();
                return;
            }
            String newComment = enterComment.getText().toString();

            // Make and send a request
            queue.start();

            JSONObject data = new JSONObject();
            try {
                data.put("phone", customer.getPhone());
                data.put("comment", newComment);
            } catch (JSONException je) {
                je.printStackTrace();
                Log.d("./CommentsActivity/JsonObject", "JSONError");
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("./CommentsActivity/JsonObject", "Error");
            }
            String url = getString(R.string.url_string) + "/update_ac";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, data, (Response.Listener<JSONObject>) response -> {
                Log.d("./CommentsActivity", "JsonObjectRequest Success!");
            }, error -> {
                Log.d("./CommentsActivity", "JsonObjectRequest Fail");
            });
            queue.add(jsonObjectRequest);
//            queue.stop();
            this.recreate();
//            queue.stop();
        });
    }

    @NonNull
    @Override
    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        return super.getOnBackInvokedDispatcher();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        queue.stop();
        finish();
    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.d("MainActivity.onRestart", "onRestart called");
//        queue.stop();
////        recreate();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d("MainActivity.onStop", "onStop called");
//        queue.stop();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d("MainActivity.onPause", "onPause called");
//        queue.stop();
//    }
}