package com.moral.airtree.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.moral.airtree.R;
import com.moral.airtree.model.Device;

import java.util.List;

/**
 * Created by bin.shen on 5/20/16.
 */
public class DeviceAdapter extends BaseAdapter {

    private List<Device> deviceList;
    private LayoutInflater layoutInflater;

    public DeviceAdapter(Context context, List<Device> devices) {
        layoutInflater = LayoutInflater.from(context);
        deviceList = devices;
    }

    public int getCount() {
        return deviceList.size();
    }

    public Object getItem(int position) {
        return deviceList.get(position);
    }

    public long getItemId(int position) {
        return (long)position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vHolder = null;
        if(convertView == null) {
            vHolder = new DeviceAdapter.ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_devices, null);
            vHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_where);
            vHolder.tvDeviceStatus = (TextView)convertView.findViewById(R.id.tv_device_status);
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }
        Device device = deviceList.get(position);
        vHolder.tv_name.setText(device.getName().isEmpty() ? device.getMac() : device.getName());
        String status = "在线";
        if(device.getStatus() != 1) {
            status = "不在线";
        }
        vHolder.tvDeviceStatus.setText(status);
        return convertView;
    }

    class ViewHolder {
        public TextView tvDeviceStatus;
        public TextView tv_name;
    }
}
