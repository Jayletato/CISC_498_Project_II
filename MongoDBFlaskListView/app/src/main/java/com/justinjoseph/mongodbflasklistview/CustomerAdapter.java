package com.justinjoseph.mongodbflasklistview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomerAdapter extends BaseAdapter{

    List<Customer> customers;
    private Context context;
    TextView name;
    TextView address;
    TextView phone;
//    TextView comment;

    public CustomerAdapter(List<Customer> customers, Context context) {
        this.context = context;
        this.customers = customers;

    }

    @Override
    public int getCount() {
        return null == customers ? 0 : customers.size(); // idk what this means
    }

    @Override
    public Object getItem(int i) {
        return null == customers ? null : customers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_customer_list, viewGroup, false);
        name = view.findViewById(R.id.customer_name);
        phone = view.findViewById(R.id.customer_phone);
        address = view.findViewById(R.id.customer_address);
//        comment = view.findViewById(R.id.customer_comment);

        name.setText(customers.get(i).getName());
        phone.setText(customers.get(i).getPhone());
        address.setText(customers.get(i).getAddress());
//        comment.setText(customers.get(i).getComment());

        return view;
    }
}

