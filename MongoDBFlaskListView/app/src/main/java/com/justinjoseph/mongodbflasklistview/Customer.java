package com.justinjoseph.mongodbflasklistview;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Customer {
    protected String _id;
    protected String name;
    protected String address;
    protected String phone;
    protected ArrayList<String> comments;

    public Customer(JSONObject jsonData) throws JSONException {
        if (jsonData.has("_id")) {
            this.setId(jsonData.getString("_id"));
        }
        this.name = (jsonData.getString("name"));
        this.address = (jsonData.getString("address"));
        this.phone = (jsonData.getString("phone"));
//        JSONArray comment_array = jsonData.getJSONArray("comments");
//        for (int i = 0; i < comment_array.length(); i++) {
//            comments.add(comment_array.getString(i));
//            Log.d("./Customer", "Added Customer!");
//        }
//            this.comment = (jsonData.getJSONArray("comments").);

//      From when comments was a list of comments
        if (jsonData.has("comments")) {
            JSONArray cmts = jsonData.getJSONArray("comments");
            if (null != cmts) {
                comments = new ArrayList<String>();
                for (int i=0 ; i < cmts.length(); i++) {
                    comments.add(cmts.get(i).toString());
                }
            }
        }
        else {
            comments = new ArrayList<String>();
        }
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        _id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<String> getComments(){
        return comments;
    }
    public void addComment(String comment){
        comments.add(comment);
    }

    public void removeComment(int index) {
        comments.remove(index);
    }
}
