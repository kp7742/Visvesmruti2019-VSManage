package tech.visvesmruti.vsmanage.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import com.appizona.yehiahd.fastsave.FastSave

import tech.visvesmruti.vsmanage.Consts
import tech.visvesmruti.vsmanage.R
import tech.visvesmruti.vsmanage.apis.APIParticipantList
import tech.visvesmruti.vsmanage.apis.APIPayingCheck
import tech.visvesmruti.vsmanage.utils.FontsOverride

class DashboardActivity : AppCompatActivity() {
    private lateinit var mWelcome: TextView
    private lateinit var mRegevent: Button
    private lateinit var mRegvs: Button
    private lateinit var mAmntbutton: Button
    private lateinit var mListParti: Button
    private lateinit var mTakeattend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        mWelcome = findViewById(R.id.welcome)
        mRegevent = findViewById(R.id.regevent)
        mRegvs = findViewById(R.id.regvs)
        mAmntbutton = findViewById(R.id.amntbutton)
        mListParti = findViewById(R.id.list_parti)
        mTakeattend = findViewById(R.id.takeattend)

        mTakeattend.visibility = GONE
        mAmntbutton.visibility = GONE

        if(intent.getStringExtra(Consts.TYPE)!!.equals(Consts.UNK)){
            Toast.makeText(this, "Unknown Identity", Toast.LENGTH_SHORT).show()
            finish()
        }

        mWelcome.text = if(!intent.getStringExtra(Consts.TYPE)!!.equals(Consts.CAMPA)){
            "Welcome " + intent.getStringExtra(Consts.NAME) + "!, " +
                    "Our VS " + intent.getStringExtra(Consts.TYPE) + " of " + intent.getStringExtra(Consts.EVENT) +
                    " of " + intent.getStringExtra(Consts.DEPT) + " Department"
        } else {
            "Welcome " + intent.getStringExtra(Consts.NAME) + "!, Our VS " + intent.getStringExtra(Consts.TYPE)
        }

        mRegvs.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, StudentRegActivity::class.java))
        }

        if(intent.getStringExtra(Consts.TYPE)!!.equals(Consts.COORD)) {
            mTakeattend.visibility = VISIBLE
            mTakeattend.setOnClickListener {
                startActivity(Intent(this@DashboardActivity, TakeAttendanceActivity::class.java))
            }
        }

        mRegevent.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, EventRegActivity::class.java))
        }

        mListParti.setOnClickListener { APIParticipantList(this@DashboardActivity).execute() }

        if(intent.getStringExtra(Consts.TYPE)!!.equals(Consts.CAMPA)) {
            mAmntbutton.visibility = VISIBLE
            mAmntbutton.setOnClickListener { APIPayingCheck(this@DashboardActivity).execute() }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.logout) {
            FastSave.getInstance().deleteValue(Consts.TOKEN)
            FastSave.getInstance().deleteValue(Consts.ID)
            val i = Intent(this@DashboardActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        } else if(id == R.id.devloper){
            startActivity(Intent(this@DashboardActivity, CodersActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        val override = FontsOverride.instance(this)
        override.setCustomFont()
        override.overrideFonts(window.decorView)
    }
}
