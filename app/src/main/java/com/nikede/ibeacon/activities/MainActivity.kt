package com.nikede.ibeacon.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.nikede.ibeacon.R
import com.nikede.ibeacon.adapters.DevicesAdapter
import com.nikede.ibeacon.models.DeviceModel
import com.nikede.ibeacon.presenters.DevicesPresenter
import com.nikede.ibeacon.views.DevicesView
import kotlinx.android.synthetic.main.activity_main.*
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import android.util.Log
import com.nikede.ibeacon.helpers.Permissions
import com.nikede.ibeacon.models.BeaconModel
import org.altbeacon.beacon.*
import android.os.RemoteException


class MainActivity : MvpAppCompatActivity(), DevicesView, BeaconConsumer {
    private lateinit var beaconManager: BeaconManager

    override fun onBeaconServiceConnect() {
        beaconManager.removeAllRangeNotifiers();
        val rangeNotifier = RangeNotifier { beacons, p1 ->
            beacons.forEach {
                devicesPresenter.addDevice(
                    BeaconModel(
                        it.parserIdentifier,
                        it.rssi.toString(),
                        it.id1.toString(),
                        it.id2.toString(),
                        it.distance
                    )
                )
            }
        }
        beaconManager.addRangeNotifier(rangeNotifier)
    }

    private val TAG = "MainActivity"

    @InjectPresenter
    lateinit var devicesPresenter: DevicesPresenter

    private val mAdapter = DevicesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        beaconManager = BeaconManager.getInstanceForApplication(this)

        recycler_devices.adapter = mAdapter
        recycler_devices.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        recycler_devices.hasFixedSize()
        beaconManager.bind(this)

        btn_switch_searched_devices.setOnClickListener {
            devicesPresenter.switchButtonClick()
        }
        btn_search.setOnClickListener {
            devicesPresenter.loadDevices()
        }

        Permissions.getPermissions(this, arrayOf(Permissions.PERMISSION_REQUEST_FINE_LOCATION))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Permissions.PERMISSION_REQUEST_FINE_LOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "fine location permission granted")
                } else {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle(getString(R.string.functionality_limited))
                    builder.setMessage(getString(R.string.location_access_not_granted))
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setOnDismissListener { }
                    builder.show()
                }
                return
            }
        }
    }

    override fun addDevice(device: DeviceModel) {
        mAdapter.addDevice(device)
    }

    override fun showError(textSource: Int) {
        Toast.makeText(applicationContext, getString(textSource), Toast.LENGTH_SHORT).show()
    }

    override fun setupEmptyList() {
        recycler_devices.visibility = View.GONE
        txt_devices_no_items.visibility = View.VISIBLE
    }


    override fun setupDevicesList(devicesList: ArrayList<DeviceModel>) {
        recycler_devices.visibility = View.VISIBLE
        txt_devices_no_items.visibility = View.GONE

        mAdapter.setupDevices(devicesList)
    }

    override fun startLoading(all: Boolean) {
        recycler_devices.visibility = View.GONE
        txt_devices_no_items.visibility = View.GONE
        cpv_devices.visibility = View.VISIBLE
        mAdapter.setupDevices(arrayListOf())
        if (!all)
            beaconManager.startRangingBeaconsInRegion(Region("myRangingUniqueId", null, null, null))
    }

    override fun endLoading(all: Boolean) {
        cpv_devices.visibility = View.GONE
        if (!all)
            beaconManager.stopMonitoringBeaconsInRegion(Region("myRangingUniqueId", null, null, null))
    }

    override fun setSwitchButtonTitle(textSource: Int) {
        btn_switch_searched_devices.text = getString(textSource)
    }
}
