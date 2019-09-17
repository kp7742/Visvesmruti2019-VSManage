package tech.visvesmruti.vsmanage.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Toast

import tech.visvesmruti.vsmanage.R
import tech.visvesmruti.vsmanage.apis.APIRegister

class StudentRegActivity : AppCompatActivity() {
    private val items = arrayOf("Select your department", "Computer", "Civil", "Mechanical", "Electrical", "Chemical", "IT", "EC", "Others")

    private lateinit var mFname: EditText
    private lateinit var mLname: EditText
    private lateinit var mEmail: EditText
    private lateinit var mMobno: EditText
    private lateinit var mClgname: EditText
    private lateinit var mSem: EditText
    private lateinit var mMalebtn: RadioButton
    private lateinit var mFemalebtn: RadioButton
    private lateinit var mOtherbtn: RadioButton
    private lateinit var mDeptspinner: Spinner
    private lateinit var mRegsubmit: Button
    private lateinit var mDepartment: String
    private lateinit var mGender: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_reg)

        mFname = findViewById(R.id.fname)
        mLname = findViewById(R.id.lname)
        mEmail = findViewById(R.id.email)
        mMobno = findViewById(R.id.mobno)
        mClgname = findViewById(R.id.clgname)
        mSem = findViewById(R.id.sem)
        mMalebtn = findViewById(R.id.malebtn)
        mFemalebtn = findViewById(R.id.femalebtn)
        mOtherbtn = findViewById(R.id.otherbtn)
        mDeptspinner = findViewById(R.id.deptspinner)
        mRegsubmit = findViewById(R.id.regsubmit)

        mDeptspinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        mDeptspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (position > 0) {
                    mDepartment = items[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        mRegsubmit.setOnClickListener {
            val fname = mFname.text.toString()
            val lname = mLname.text.toString()
            val email = mEmail.text.toString()
            val mobilenumber = mMobno.text.toString()
            val colgname = mClgname.text.toString()
            val semester = mSem.text.toString()

            mGender = when {
                mMalebtn.isChecked -> "Male"
                mFemalebtn.isChecked -> "Female"
                else -> "Other"
            }

            if (fname.isNotEmpty() && lname.isNotEmpty() && email.isNotEmpty() && mobilenumber.isNotEmpty() && colgname.isNotEmpty() && semester.isNotEmpty()) {
                if(mobilenumber.length == 10){
                    APIRegister(this@StudentRegActivity).execute(email, fname, lname, colgname, mDepartment, semester, mobilenumber, mGender)
                } else {
                    Toast.makeText(this@StudentRegActivity, "Please Enter valid Mobile Number", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@StudentRegActivity, "Please Fill All Details", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
