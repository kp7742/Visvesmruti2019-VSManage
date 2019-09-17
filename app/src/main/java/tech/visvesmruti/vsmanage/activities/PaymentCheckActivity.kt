package tech.visvesmruti.vsmanage.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import tech.visvesmruti.vsmanage.Consts
import tech.visvesmruti.vsmanage.R
import tech.visvesmruti.vsmanage.apis.APIAttendance
import tech.visvesmruti.vsmanage.apis.APIPaid

class PaymentCheckActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_check)

        val mNamestud = findViewById<TextView>(R.id.namestud)
        val mEventname = findViewById<TextView>(R.id.eventname)
        val mEmailstud = findViewById<TextView>(R.id.emailstud)
        val mPaybtn = findViewById<Button>(R.id.paybtn)

        if (intent != null) {
            mNamestud.text = intent.getStringExtra(Consts.NAME)
            mEventname.text = intent.getStringExtra(Consts.EVENT)
            mEmailstud.text = intent.getStringExtra(Consts.EMAIL)
            mPaybtn.setOnClickListener {
                APIPaid(this@PaymentCheckActivity).execute(intent.getStringExtra(Consts.ERCODE))
                APIAttendance(this@PaymentCheckActivity).execute(intent.getStringExtra(Consts.ERCODE))
            }
        } else {
            finish()
        }
    }

    override fun onBackPressed() {
        Toast.makeText(this, "You can't Go Back :)", Toast.LENGTH_SHORT).show()
    }
}
