package com.example.authorization.CouponCreationList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.authorization.R;

import java.util.List;

public class CouponCreatorAdapter  extends ArrayAdapter<CouponList> {
    private LayoutInflater inflater;
    private int layout;
    private List<CouponList> couponCreators;

    public CouponCreatorAdapter (Context context, int resource, List<CouponList> couponCreators)
    {
        super(context,resource, couponCreators);
        this.couponCreators = couponCreators;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = inflater.inflate(this.layout,parent,false);

        TextView description = (TextView) view.findViewById(R.id.description);
        TextView idView = (TextView) view.findViewById(R.id.username);

        CouponList couponCreator = couponCreators.get(position);

        description.setText(couponCreator.getDescription());
        idView.setText(String.valueOf(couponCreator.id));

        return view;
    }
}
