package tech.visvesmruti.vsmanage.activities

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import tech.visvesmruti.vsmanage.Consts
import tech.visvesmruti.vsmanage.R
import tech.visvesmruti.vsmanage.apis.APIPaid
import tech.visvesmruti.vsmanage.utils.FontsOverride

class PayingAmountActivity : AppCompatActivity() {
    var hasPaid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paying_amount)

        val zoomout = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_down)

        val mPayicon = findViewById<ImageView>(R.id.payicon)
        val mCampname = findViewById<TextView>(R.id.campname)
        val mCampid = findViewById<TextView>(R.id.campid)
        val mMessage = findViewById<TextView>(R.id.message)
        val mAmount = findViewById<TextView>(R.id.amount)
        val mPaybtn = findViewById<Button>(R.id.paybtn)

        mPaybtn.visibility = GONE
        mPayicon.startAnimation(zoomout)

        if (intent != null) {
            mCampname.text = intent.getStringExtra(Consts.NAME)
            mCampid.text = intent.getStringExtra(Consts.EMAIL)
            mAmount.text = "₹ " + intent.getStringExtra(Consts.AMOUNT)!!
            if(intent.getIntExtra(Consts.ID, 0) == 1) {
                mCampname.visibility = GONE
                mPaybtn.visibility = VISIBLE
                mCampid.text = "Participant: " + intent.getStringExtra(Consts.EMAIL)
                mMessage.text = intent.getStringExtra(Consts.MSG)
                mPaybtn.setOnClickListener {
                    AlertDialog.Builder(this@PayingAmountActivity)
                            .setTitle("Are You Sure?")
                            .setMessage("Please Confirm that You had collected Given Fees Amount")
                            .setCancelable(true)
                            .setNegativeButton("Cancel") { dialogInterface, _ ->
                                AlertDialog.Builder(this@PayingAmountActivity)
                                        .setTitle("Are You Sure?")
                                        .setMessage("You Really Want to Cancel This Payment?")
                                        .setCancelable(true)
                                        .setNegativeButton("No") { dialogInterface2, _ ->
                                            dialogInterface2.dismiss()
                                        }
                                        .setPositiveButton("Yes") { dialogInterface2, _ ->
                                            dialogInterface.dismiss()
                                            finish()
                                        }
                                        .show()
                            }
                            .setPositiveButton("Yes") { dialogInterface, _ ->
                                APIPaid(this@PayingAmountActivity).execute(intent.getStringExtra(Consts.ERCODE))
                                hasPaid = true
                            }
                            .show()
                }
            }
        }
    }

    override fun onBackPressed() {
        if(intent.getIntExtra(Consts.ID, 0) == 1 && !hasPaid) {
            Toast.makeText(this, "You can't Go Back :)", Toast.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
        val override = FontsOverride.instance(this)
        override.setCustomFont()
        override.overrideFonts(window.decorView)
    }
}

