package tech.visvesmruti.vsmanage.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

import org.json.JSONArray
import org.json.JSONException

import tech.visvesmruti.vsmanage.Consts
import tech.visvesmruti.vsmanage.R
import tech.visvesmruti.vsmanage.adapters.ParticipantAdapter
import tech.visvesmruti.vsmanage.models.ParticipantInfo
import tech.visvesmruti.vsmanage.utils.FontsOverride

import java.util.ArrayList

class ParticipantListActivity : AppCompatActivity() {
    private lateinit var mFilterEmail: EditText
    private lateinit var mCardList: RecyclerView
    private lateinit var alldata: ArrayList<ParticipantInfo>
    private lateinit var filtered: ArrayList<ParticipantInfo>
    private lateinit var adapter: ParticipantAdapter
    private lateinit var data: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participant_list)

        mFilterEmail = findViewById(R.id.filter_email)

        mCardList = findViewById(R.id.cardList)
        mCardList.setHasFixedSize(true)
        mCardList.layoutManager = LinearLayoutManager(this)

        alldata = ArrayList()
        filtered = ArrayList()

        if (intent != null) {
            data = intent.getStringExtra(Consts.DATA)!!
            try {
                val jsonArray = JSONArray(data)
                for (i in 0 until jsonArray.length()) {
                    val `object` = jsonArray.getJSONObject(i)
                    alldata.add(ParticipantInfo(
                            `object`.getString(Consts.NAME),
                            `object`.getString(Consts.COLLEGE),
                            `object`.getString(Consts.MOBILE),
                            `object`.getString(Consts.SEM),
                            `object`.getString(Consts.EMAIL),
                            `object`.getString(Consts.EVENT)))
                }

                filtered.addAll(alldata)
                adapter = ParticipantAdapter()
                adapter.setList(filtered)
                mCardList.adapter = adapter

                mFilterEmail.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                        filtered.clear()
                        if (s.toString().isEmpty()) {
                            filtered.addAll(alldata)
                        } else {
                            for (info in alldata) {
                                if (info.email.contains(s.toString())) {
                                    filtered.add(info)
                                }
                            }
                        }
                        adapter.setList(filtered)
                        adapter.notifyDataSetChanged()
                    }

                    override fun afterTextChanged(s: Editable) {}
                })
            } catch (e: JSONException) {
                e.printStackTrace()
                finish()
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


















