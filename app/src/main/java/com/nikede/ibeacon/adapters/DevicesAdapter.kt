package com.nikede.ibeacon.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikede.ibeacon.R
import com.nikede.ibeacon.models.BeaconModel
import com.nikede.ibeacon.models.DeviceModel
import kotlinx.android.synthetic.main.cell_device.view.*

class DevicesAdapter() : RecyclerView.Adapter<DevicesAdapter.ViewHolder>() {
    private val mDevicesList = ArrayList<DeviceModel>()

    fun setupDevices(devicesList: ArrayList<DeviceModel>) {
        mDevicesList.clear()
        mDevicesList.addAll(devicesList)
        sort()
        notifyDataSetChanged()
    }

    fun addDevice(device: DeviceModel) {
        mDevicesList.add(device)
        sort()
        notifyDataSetChanged()
    }

    private fun sort() {
        val startIndex = 0;
        val endIndex = mDevicesList.size - 1;
        doSort(startIndex, endIndex);
    }

    private fun doSort(start: Int, end: Int) {
        if (start >= end)
            return;
        var i = start
        var j = end;
        var cur = i - (i - j) / 2;
        while (i < j) {
            while (i < cur && ((mDevicesList[i] as BeaconModel).distance <= (mDevicesList[cur] as BeaconModel).distance)) {
                i++;
            }
            while (j > cur && ((mDevicesList[cur] as BeaconModel).distance <= (mDevicesList[j] as BeaconModel).distance)) {
                j--;
            }
            if (i < j) {
                val temp = (mDevicesList[i] as BeaconModel);
                mDevicesList[i] = mDevicesList[j];
                mDevicesList[j] = temp;
                if (i == cur)
                    cur = j;
                else if (j == cur)
                    cur = i;
            }
        }
        doSort(start, cur);
        doSort(cur + 1, end);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.cell_device, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = mDevicesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mDevicesList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(deviceModel: DeviceModel) {
            itemView.device_txt_mac.text =
                "${itemView.context.getString(R.string.mac)}: ${deviceModel.mac}"
            itemView.device_txt_rssi.text =
                "${itemView.context.getString(R.string.rssi)}: ${deviceModel.rssi}"
            if (deviceModel is BeaconModel) {
                itemView.layout_beacon.visibility = View.VISIBLE
                itemView.device_txt_major.text =
                    "${itemView.context.getString(R.string.major)}: ${deviceModel.major}"
                itemView.device_txt_minor.text =
                    "${itemView.context.getString(R.string.minor)}: ${deviceModel.minor}"
                itemView.device_txt_distance.text =
                    "${itemView.context.getString(R.string.distance)}: ${deviceModel.distance}"
            }
        }
    }
}