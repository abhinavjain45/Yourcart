package com.ecommerce.yourcart;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class GridProductLayoutAdapter extends BaseAdapter {

    List<HorizontalProductScrollModal> horizontalProductScrollModalList;

    public GridProductLayoutAdapter(List<HorizontalProductScrollModal> horizontalProductScrollModalList) {
        this.horizontalProductScrollModalList = horizontalProductScrollModalList;
    }

    @Override
    public int getCount() {
        return horizontalProductScrollModalList.size();
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

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent = new Intent(parent.getContext(), ProductDetailsActivity.class);
                    productDetailsIntent.putExtra("PRODUCT_ID", horizontalProductScrollModalList.get(position).getProductID());
                    parent.getContext().startActivity(productDetailsIntent);
                }
            });

            ImageView productImage = view.findViewById(R.id.horizontal_scroll_product_image);
            TextView productTitle = view.findViewById(R.id.horizontal_scroll_product_title);
            TextView productSpecification = view.findViewById(R.id.horizontal_scroll_product_specification);
            TextView productPrice = view.findViewById(R.id.horizontal_scroll_product_price);

            Glide.with(parent.getContext()).load(horizontalProductScrollModalList.get(position).getProductImage()).apply(new RequestOptions().placeholder(R.mipmap.productplaceholder)).into(productImage);
            productTitle.setText(horizontalProductScrollModalList.get(position).getProductTitle());
            productSpecification.setText(horizontalProductScrollModalList.get(position).getProductSpecification());
            productPrice.setText("Rs. "+horizontalProductScrollModalList.get(position).getProductPrice()+"/-");
        } else {
            view = convertView;
        }
        return view;
    }
}
