package com.ecommerce.yourcart;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GridProductLayoutAdapter extends BaseAdapter {

    List<HorizontalProductScrollModal> horizontalProductScrollModalList;

    public GridProductLayoutAdapter(List<HorizontalProductScrollModal> horizontalProductScrollModalList) {
        this.horizontalProductScrollModalList = horizontalProductScrollModalList;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,null);
            view.setElevation(0);
            view.setBackgroundColor(Color.parseColor("#ffffff"));
            ImageView productImage = view.findViewById(R.id.horizontal_scroll_product_image);
            TextView productTitle = view.findViewById(R.id.horizontal_scroll_product_title);
            TextView productSpecification = view.findViewById(R.id.horizontal_scroll_product_specification);
            TextView productPrice = view.findViewById(R.id.horizontal_scroll_product_price);

            productImage.setImageResource(horizontalProductScrollModalList.get(position).getProductImage());
            productTitle.setText(horizontalProductScrollModalList.get(position).getProductTitle());
            productSpecification.setText(horizontalProductScrollModalList.get(position).getProductSpecification());
            productPrice.setText(horizontalProductScrollModalList.get(position).getProductPrice());
        } else {
            view = convertView;
        }
        return view;
    }
}
