package com.example.kursova;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {

    private final Context context;
    private final ArrayList<Product> products;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        super(context, R.layout.product_item, products);
        this.context = context;
        this.products = products;
    }

    private static class ViewHolder {
        TextView nameTextView;
        TextView typeTextView;
        TextView weightTextView;
        CheckBox checkBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.nameTextView = convertView.findViewById(R.id.textView4);
            viewHolder.typeTextView = convertView.findViewById(R.id.textView5);
            viewHolder.weightTextView = convertView.findViewById(R.id.textView7);
            viewHolder.checkBox = convertView.findViewById(R.id.checkBox);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Product product = products.get(position);
        viewHolder.nameTextView.setText(product.getName());
        viewHolder.typeTextView.setText(product.getType());
        viewHolder.weightTextView.setText(String.valueOf(product.getWeight()));
        viewHolder.checkBox.setChecked(product.isChecked());
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.setSelected(true);
            }
        });

        return convertView;
    }
}

