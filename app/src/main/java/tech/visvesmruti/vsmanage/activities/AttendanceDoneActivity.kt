package tech.visvesmruti.vsmanage.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import tech.visvesmruti.vsmanage.Consts
import tech.visvesmruti.vsmanage.R

class AttendanceDoneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance_done)

        val zoomout = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_down)
        val mApproved = findViewById<ImageView>(R.id.approved)
        val mNamestud = findViewById<TextView>(R.id.namestud)
        val mEventname = findViewById<TextView>(R.id.eventname)
        val mEmailstud = findViewById<TextView>(R.id.emailstud)
        mApproved.startAnimation(zoomout)

        if (intent != null) {
            mNamestud.text = intent.getStringExtra(Consts.NAME)
            mEventname.text = intent.getStringExtra(Consts.EVENT)
            mEmailstud.text = intent.getStringExtra(Consts.EMAIL)
        }
    }
}
