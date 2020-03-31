package com.example.getcoordinatessample

import android.content.pm.PackageManager
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*



val REQUEST_CODE_COORDINATES = 200

class MainActivity : AppCompatActivity() {

    var isAccessLocationEnabled = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->

            checkPermission()
            //println("testetetetetetet")
            //getCoordinates()
        }
    }

    private fun checkPermission() {
        val permissionStatusOfLocation = ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionStatusOfLocation == PackageManager.PERMISSION_GRANTED) getCoordinates() else permissionRequest()
    }

    private fun permissionRequest() {
        val isNeedDialogOfLocation: Boolean =
            ActivityCompat.shouldShowRequestPermissionRationale(
                this@MainActivity,
                android.Manifest.permission.ACCESS_FINE_LOCATION)

        if (!isNeedDialogOfLocation) {
            //requestPermission()メソッドを実行する
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_COORDINATES)
            return
        }

        AlertDialog.Builder(this@MainActivity).apply {
            setTitle("位置情報取得には許可が必要になります。")
            setMessage("許可を得ないと位置情報を取得せずに終了することになります。ご注意下さい。")
            setPositiveButton("許可する"){dialog, which ->
                //requestPermission()メソッドの実行
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE_COORDINATES)
            }
            setNegativeButton("許可しない"){dialog, which ->
                Toast.makeText(this@MainActivity, "許可が得られなかったので中止しました。", Toast.LENGTH_SHORT).show()
                finish()
            }
        }.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != REQUEST_CODE_COORDINATES) return

        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            finish()
            return
        }
        getCoordinates()
    }


    fun getCoordinates(){
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener {
            Toast.makeText(this, "座標点を取得しました。", Toast.LENGTH_SHORT).show()
            val latitude: String = it.latitude.toString()
            val longitude: String = it.longitude.toString()
            tvCoordinates.setText(longitude + " , " + latitude)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


}
