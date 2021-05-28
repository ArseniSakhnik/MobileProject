package com.example.authorization.CouponCreationList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.authorization.R;

import java.util.List;

public class CouponAdapter extends ArrayAdapter<CouponUserList> {
    private LayoutInflater inflater;
    private int layout;
    private List<CouponUserList> couponUserList;

    public CouponAdapter(Context context, int resource, List<CouponUserList> couponUserList) {
        super(context, resource, couponUserList);
        this.couponUserList = couponUserList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);

        TextView description = (TextView) view.findViewById(R.id.description);
        TextView idView = (TextView) view.findViewById(R.id.username);

        CouponUserList couponList = couponUserList.get(position);

        description.setText(couponList.getDescription());
        idView.setText(String.valueOf(couponList.getCouponCreatorId()));

        return view;
    }

    public void setCouponUserList(List<CouponUserList> couponUserList) {
        this.couponUserList = couponUserList;
    }
}
