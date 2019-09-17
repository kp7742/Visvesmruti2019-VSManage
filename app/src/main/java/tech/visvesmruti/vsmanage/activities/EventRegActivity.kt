package tech.visvesmruti.vsmanage.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View

import tech.visvesmruti.vsmanage.R
import tech.visvesmruti.vsmanage.apis.APIEventReg
import tech.visvesmruti.vsmanage.utils.FontsOverride

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*

class EventRegActivity : AppCompatActivity() {
    private val depts = arrayOf("Select your event Department", "Computer", "Civil", "Mechanical", "Electrical", "Chemical", "Science & Humanities", "BVOC. Software")

    private val compevents = arrayOf("Select Computer Dept's event", "Paper Presentation", "Poster Presentation", "Programming Date", "Placement Drive", "Pitchers", "Pragmatist of Wall Street", "Puzzle with Snake and Ladder")
    private val compeventcode = arrayOf("", "COMP-PAPER", "COMP-POST", "COMP-PROG", "COMP-PLAC", "COMP-PITCH", "COMP-PRAGM", "COMP-PUZZ")
    private val compteamcount = intArrayOf(0, 2, 3, 1, 1, 3, 3, 3)

    private val civilevents = arrayOf("Select Civil's Dept event", "Paper Presentation", "Poster Presentation", "Model Presentation", "E-Placement", "Absolute H2O", "Chakravyuh")
    private val civileventcode = arrayOf("", "CIVIL-PAPER", "CIVIL-POST", "CIVIL-MODEL", "CIVIL-EPLA", "CIVIL-AH2O", "CIVIL-CHKR")
    private val civilteamcount = intArrayOf(0, 2, 3, 3, 1, 3, 4)

    private val chemevents = arrayOf("Select Chemical's Dept event", "Paper Presentation", "Poster Presentaion", "Model Presentation", "Chem-O-Quiz", "Chem-O-Live", "Chem-O-Cryst", "Contraption", "Hepta League(Cricket)")
    private val chemeventcode = arrayOf("", "CHEM-PAPER", "CHEM-POST", "CHEM-MODEL", "CHEM-OQUIZ", "CHEM-OLIVE", "CHEM-CRYST", "CHEM-CONTR", "CHEM-HEPTA")
    private val chemteamcount = intArrayOf(0, 2, 2, 4, 3, 3, 2, 5, 7)

    private val mechevents = arrayOf("Select Mechanical's Dept event", "Paper Presentation", "Poster Presentation", "Model Presentation", "Junk Yard", "Lathe War")
    private val mecheventcode = arrayOf("", "MECH-PAPER", "MECH-POST", "MECH-MODEL", "MECH-JUNKY", "MECH-LATH")
    private val mechteamcount = intArrayOf(0, 2, 2, 4, 4, 2)

    private val electevents = arrayOf("Select Electrical's Dept event", "Paper Presentation", "Poster Presentation", "Model Presentation", "E-Google", "Virtual Placement", "Buzz Wire", "E-Quiz", "Aqua Robo")
    private val electeventcode = arrayOf("", "ELEC-PAPER", "ELEC-POST", "ELEC-MODEL", "ELEC-EGOG", "ELEC-VIRT", "ELEC-BUZZ", "ELEC-QUIZ", "ELEC-AQUA")
    private val electteamcount = intArrayOf(0, 3, 3, 4, 1, 1, 1, 3, 5)

    private val scihumevents = arrayOf("Select Science & Humanities's Dept event", "Musing Fizik")
    private val scihumeventcode = arrayOf("", "SCIH-MUZF")
    private val scihumteamcount = intArrayOf(0, 6)

    private val bvocsevents = arrayOf("Select BVOC Software's Dept event", "Blind Coding", "Techno Castle", "Social Media Quiz")
    private val bvocseventcode = arrayOf("", "BVOC-BCODE", "BVOC-TECH", "BVOC-QUIZ")
    private val bvocsteamcount = intArrayOf(0, 1, 2, 2)

    private lateinit var mEventdept: Spinner
    private lateinit var mDeptevent: Spinner
    private lateinit var mRegemail1: EditText
    private lateinit var mRegemail2: EditText
    private lateinit var mRegemail3: EditText
    private lateinit var mRegemail4: EditText
    private lateinit var mRegemail5: EditText
    private lateinit var mRegemail6: EditText
    private lateinit var mRegemail7: EditText
    private lateinit var mPaid: CheckBox
    private lateinit var mSubmreg: Button

    private var code = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_reg)

        mEventdept = findViewById(R.id.eventdept)
        mDeptevent = findViewById(R.id.deptevent)
        mRegemail1 = findViewById(R.id.regemail1)
        mRegemail2 = findViewById(R.id.regemail2)
        mRegemail3 = findViewById(R.id.regemail3)
        mRegemail4 = findViewById(R.id.regemail4)
        mRegemail5 = findViewById(R.id.regemail5)
        mRegemail6 = findViewById(R.id.regemail6)
        mRegemail7 = findViewById(R.id.regemail7)
        mPaid = findViewById(R.id.paid)
        mSubmreg = findViewById(R.id.submreg)

        EmailBoxAction(7, false)

        mEventdept.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, depts)
        mEventdept.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                code = ""
                EmailBoxAction(7, false)
                when (position) {
                    0 -> {
                        mDeptevent.adapter = null
                        mDeptevent.onItemSelectedListener = null
                    }
                    1 //Computer
                    -> {
                        mDeptevent.adapter = ArrayAdapter(this@EventRegActivity, android.R.layout.simple_spinner_dropdown_item, compevents)
                        mDeptevent.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                                EmailBoxAction(7, false)
                                code = compeventcode[position]
                                EmailBoxAction(compteamcount[position], true)
                            }
                            override fun onNothingSelected(parent: AdapterView<*>) {}
                        }
                    }
                    2 //Civil
                    -> {
                        mDeptevent.adapter = ArrayAdapter(this@EventRegActivity, android.R.layout.simple_spinner_dropdown_item, civilevents)
                        mDeptevent.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                                EmailBoxAction(7, false)
                                code = civileventcode[position]
                                EmailBoxAction(civilteamcount[position], true)
                            }
                            override fun onNothingSelected(parent: AdapterView<*>) {}
                        }
                    }
                    3 //Mechanical
                    -> {
                        mDeptevent.adapter = ArrayAdapter(this@EventRegActivity, android.R.layout.simple_spinner_dropdown_item, mechevents)
                        mDeptevent.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                                EmailBoxAction(7, false)
                                code = mecheventcode[position]
                                EmailBoxAction(mechteamcount[position], true)
                            }
                            override fun onNothingSelected(parent: AdapterView<*>) {}
                        }
                    }
                    4 //Electrical
                    -> {
                        mDeptevent.adapter = ArrayAdapter(this@EventRegActivity, android.R.layout.simple_spinner_dropdown_item, electevents)
                        mDeptevent.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                                EmailBoxAction(7, false)
                                code = electeventcode[position]
                                EmailBoxAction(electteamcount[position], true)
                            }
                            override fun onNothingSelected(parent: AdapterView<*>) {}
                        }
                    }
                    5 //Chemical
                    -> {
                        mDeptevent.adapter = ArrayAdapter(this@EventRegActivity, android.R.layout.simple_spinner_dropdown_item, chemevents)
                        mDeptevent.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                                EmailBoxAction(7, false)
                                code = chemeventcode[position]
                                EmailBoxAction(chemteamcount[position], true)
                            }
                            override fun onNothingSelected(parent: AdapterView<*>) {}
                        }
                    }
                    6 //SciHum
                    -> {
                        mDeptevent.adapter = ArrayAdapter(this@EventRegActivity, android.R.layout.simple_spinner_dropdown_item, scihumevents)
                        mDeptevent.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                                EmailBoxAction(7, false)
                                code = scihumeventcode[position]
                                EmailBoxAction(scihumteamcount[position], true)
                            }
                            override fun onNothingSelected(parent: AdapterView<*>) {}
                        }
                    }
                    7 //BVOC. Software
                    -> {
                        mDeptevent.adapter = ArrayAdapter(this@EventRegActivity, android.R.layout.simple_spinner_dropdown_item, bvocsevents)
                        mDeptevent.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                                EmailBoxAction(7, false)
                                code = bvocseventcode[position]
                                EmailBoxAction(bvocsteamcount[position], true)
                            }
                            override fun onNothingSelected(parent: AdapterView<*>) {}
                        }
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        mSubmreg.setOnClickListener {
            if (getEmailCount() > 0) {
                APIEventReg(this@EventRegActivity, getEmailCount(), mPaid.isChecked)
                        .execute(code, mRegemail1.text.toString(), mRegemail2.text.toString(), mRegemail3.text.toString(),
                                mRegemail4.text.toString(), mRegemail5.text.toString(), mRegemail6.text.toString(), mRegemail7.text.toString())
            } else {
                Toast.makeText(this@EventRegActivity, "Please Fill All Details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getEmailCount(): Int {
        var count = 0
        if (mRegemail1.text.isNotEmpty()){
            count++
        }
        if (mRegemail2.text.isNotEmpty()){
            count++
        }
        if (mRegemail3.text.isNotEmpty()){
            count++
        }
        if (mRegemail4.text.isNotEmpty()){
            count++
        }
        if (mRegemail5.text.isNotEmpty()){
            count++
        }
        if (mRegemail6.text.isNotEmpty()){
            count++
        }
        if (mRegemail7.text.isNotEmpty()){
            count++
        }
        return count
    }

    private fun EmailBoxAction(count: Int, isVisible: Boolean) {
        mRegemail1.hint = if (count > 1) "1st Participant Email(Leader)" else "1st Participant Email"
        val action = if (isVisible) VISIBLE else GONE
        when (count) {
            7 -> {
                mRegemail7.setText("")
                mRegemail7.visibility = action
                mRegemail6.setText("")
                mRegemail6.visibility = action
                mRegemail5.setText("")
                mRegemail5.visibility = action
                mRegemail4.setText("")
                mRegemail4.visibility = action
                mRegemail3.setText("")
                mRegemail3.visibility = action
                mRegemail2.setText("")
                mRegemail2.visibility = action
                mRegemail1.setText("")
                mRegemail1.visibility = action
            }
            6 -> {
                mRegemail6.setText("")
                mRegemail6.visibility = action
                mRegemail5.setText("")
                mRegemail5.visibility = action
                mRegemail4.setText("")
                mRegemail4.visibility = action
                mRegemail3.setText("")
                mRegemail3.visibility = action
                mRegemail2.setText("")
                mRegemail2.visibility = action
                mRegemail1.setText("")
                mRegemail1.visibility = action
            }
            5 -> {
                mRegemail5.setText("")
                mRegemail5.visibility = action
                mRegemail4.setText("")
                mRegemail4.visibility = action
                mRegemail3.setText("")
                mRegemail3.visibility = action
                mRegemail2.setText("")
                mRegemail2.visibility = action
                mRegemail1.setText("")
                mRegemail1.visibility = action
            }
            4 -> {
                mRegemail4.setText("")
                mRegemail4.visibility = action
                mRegemail3.setText("")
                mRegemail3.visibility = action
                mRegemail2.setText("")
                mRegemail2.visibility = action
                mRegemail1.setText("")
                mRegemail1.visibility = action
            }
            3 -> {
                mRegemail3.setText("")
                mRegemail3.visibility = action
                mRegemail2.setText("")
                mRegemail2.visibility = action
                mRegemail1.setText("")
                mRegemail1.visibility = action
            }
            2 -> {
                mRegemail2.setText("")
                mRegemail2.visibility = action
                mRegemail1.setText("")
                mRegemail1.visibility = action
            }
            1 -> {
                mRegemail1.setText("")
                mRegemail1.visibility = action
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val override = FontsOverride.instance(this)
        override.setCustomFont()
        override.overrideFonts(window.decorView)
    }
}