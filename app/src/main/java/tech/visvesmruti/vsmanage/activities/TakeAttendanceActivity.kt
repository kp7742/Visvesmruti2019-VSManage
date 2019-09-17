package tech.visvesmruti.vsmanage.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import tech.visvesmruti.vsmanage.Consts
import tech.visvesmruti.vsmanage.R
import tech.visvesmruti.vsmanage.apis.APIAttendance

class TakeAttendanceActivity : AppCompatActivity() {
    private lateinit var mStudid: EditText
    private lateinit var mQr: TextView
    private lateinit var mIdsub: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_attendance)

        mStudid = findViewById(R.id.studid)
        mQr = findViewById(R.id.qr)
        mIdsub = findViewById(R.id.idsub)

        if (intent != null) {
            mStudid.setText(intent.getStringExtra(Consts.SID))
        }

        mQr.setOnClickListener {
            startActivity(Intent(this@TakeAttendanceActivity, QRActivity::class.java))
        }

        mIdsub.setOnClickListener {
            if(mStudid.text.toString().isNotEmpty()){
                APIAttendance(this@TakeAttendanceActivity).execute(mStudid.text.toString())
            } else {
                Toast.makeText(this@TakeAttendanceActivity, "Please Enter Event ID!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
