package com.justinjoseph.mongodbflasklistview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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

public class CommentAdapter extends BaseAdapter {
    private Context context;
    Customer customer;

    public CommentAdapter(Customer customer, Context context) {
        this.context = context;
        this.customer = customer;
    }
//    public ArrayList<String> getComments(){return customer.getComments();}

    @Override
    public int getCount() {
        return null == customer.getComments() ? 0 : customer.getComments().size(); // idk what this means
    }

    @Override
    public Object getItem(int i) {
        return null == customer.getComments() ? null : customer.getComments().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_comment_list, viewGroup, false);

        TextView commentText =  view.findViewById(R.id.comment_text_container);
        commentText.setText(customer.getComments().get(i));

        ImageButton deleteButton = view.findViewById(R.id.delete_comment_button);

//        confirmButton.setOnClickListener(v -> {
//            String newComment = commentText.getText().toString();
//            customer.getComments().set(i, newComment);
//
//            String url = context.getString(R.string.url_string) + "/add_comment";
//
//            RequestQueue queue = Volley.newRequestQueue(context);
//            JSONObject data = new JSONObject();
//            data.
//        });

        deleteButton.setOnClickListener(v -> {
            String url = context.getString(R.string.url_string) + "/update_dc";

            customer.getComments().remove(i);
            RequestQueue queue = Volley.newRequestQueue(context);
            JSONObject data = new JSONObject();
            try{
                data.put("phone", customer.getPhone());
                data.put("index", i);
            } catch (JSONException je) {
                Log.d("./CommentAdapter/onClick", "JSONError");
                je.printStackTrace();
                return;
            } catch (Exception e) {
                Log.d("./CommentAdapter/onClick", "Error");
                e.printStackTrace();
                return;
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, data,
                    response -> Log.d("./CommentAdapter/jsonObjectRequest", "Success deleting comment"),
                    error -> Log.d("./CommentAdapter/jsonObjectRequest", "Error deleting comment")
            );
            queue.add(jsonObjectRequest);
            view.setVisibility(View.GONE);
            queue.stop();
        });


        return view;
    }


}

