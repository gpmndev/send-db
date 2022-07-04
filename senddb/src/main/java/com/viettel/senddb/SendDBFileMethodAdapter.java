package com.viettel.senddb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class SendDBFileMethodAdapter extends BaseAdapter {
    private final List<SendDBFileMethod> methods;
    private final Context context;

    public SendDBFileMethodAdapter(Context context, List<SendDBFileMethod> methods) {
        this.context = context;
        this.methods = methods;
    }

    @Override
    public int getCount() {
        return methods.size();
    }

    @Override
    public SendDBFileMethod getItem(int position) {
        return methods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.item_send_db_file_method, null);
        TextView tvText = convertView.findViewById(R.id.tvText);
        ImageView imvIcon = convertView.findViewById(R.id.imvIcon);
        SendDBFileMethod method = getItem(position);
        tvText.setText(method.text);
        imvIcon.setImageResource(method.iconId);
        return convertView;
    }
}
