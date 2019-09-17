package tech.visvesmruti.vsmanage.activities

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.appizona.yehiahd.fastsave.FastSave
import com.karumi.dexter.Dexter

import tech.visvesmruti.vsmanage.Consts
import tech.visvesmruti.vsmanage.R
import tech.visvesmruti.vsmanage.apis.APILogin
import tech.visvesmruti.vsmanage.apis.APILoginCheck
import tech.visvesmruti.vsmanage.utils.FontsOverride
import com.karumi.dexter.PermissionToken
import androidx.appcompat.app.AlertDialog
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class MainActivity : AppCompatActivity() {
    private lateinit var mLoginid: EditText
    private lateinit var mLoginpass: EditText
    private lateinit var mLoginbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {}
                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {}
                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        if (response!!.isPermanentlyDenied) {
                            val builder = AlertDialog.Builder(this@MainActivity)
                            builder.setTitle("Need Permissions")
                            builder.setMessage("Please grant the camera permissions to scan qr code. Go to settings")

                            builder.setPositiveButton("OK!") { dialog, _ ->
                                dialog.cancel()
                                Toast.makeText(applicationContext, "Thank you!", Toast.LENGTH_LONG).show()
                            }
                            builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
                            builder.show()
                        } else {
                            val builder = AlertDialog.Builder(this@MainActivity)
                            builder.setTitle("Need Permissions")
                            builder.setMessage("Please grant the camera permissions to scan qr code")
                            builder.setPositiveButton("OK!") { dialog, _ ->
                                dialog.cancel()
                                Toast.makeText(applicationContext, "Thank you!", Toast.LENGTH_LONG).show()
                            }
                            builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
                            builder.show()
                        }
                    }
                }).check()

        mLoginid = findViewById(R.id.loginid)
        mLoginpass = findViewById(R.id.loginpass)
        mLoginbtn = findViewById(R.id.loginbtn)

        if (FastSave.getInstance().isKeyExists(Consts.ID)) {
            mLoginid.setText(FastSave.getInstance().getString(Consts.ID, ""))
        }

        if (FastSave.getInstance().isKeyExists(Consts.TOKEN)) {
            APILoginCheck(this@MainActivity).execute()
        }

        mLoginbtn.setOnClickListener {
            val id = mLoginid.text.toString()
            val pass = mLoginpass.text.toString()
            if (id.isNotEmpty() && pass.isNotEmpty()) {
                APILogin(this@MainActivity).execute(id, pass)
            } else {
                Toast.makeText(this@MainActivity, "Please Input All Data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (FastSave.getInstance().isKeyExists(Consts.TOKEN)) {
            APILoginCheck(this@MainActivity).execute()
        }
    }

    override fun onStart() {
        super.onStart()
        val override = FontsOverride.instance(this)
        override.setCustomFont()
        override.overrideFonts(window.decorView)
    }
}
