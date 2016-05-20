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
        ViewHolde vHolde = null;
        if(convertView == null) {
            vHolde = new DeviceAdapter.ViewHolde();
            convertView = layoutInflater.inflate(R.layout.list_devices, null);
            vHolde.tv_name = (TextView) convertView.findViewById(R.id.tv_where);
            vHolde.tvDeviceStatus = (TextView)convertView.findViewById(R.id.tv_device_status);
            convertView.setTag(vHolde);
        } else {
            vHolde = (ViewHolde) convertView.getTag();
        }
        Device device = deviceList.get(position);
        vHolde.tv_name.setText(device.getMac() + " (" + device.getIp() + ")");
        String status = "在线";
        int code = device.getStatus();
        if(code == 1) {
            status = "不在线";
        } else if(code == 2) {

        }
        vHolde.tvDeviceStatus.setText(status);
        return convertView;
    }

    class ViewHolde {
        public TextView tvDeviceStatus;
        public TextView tv_name;
    }
}
