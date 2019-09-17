package tech.visvesmruti.vsmanage.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import tech.visvesmruti.vsmanage.R
import tech.visvesmruti.vsmanage.models.ParticipantInfo

class ParticipantAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var participantInfoList: List<ParticipantInfo>? = null

    fun setList(participantInfoList: List<ParticipantInfo>) {
        this.participantInfoList = participantInfoList
    }

    override fun getItemCount(): Int {
        return participantInfoList!!.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.participant_list_item, viewGroup, false)
        return object : RecyclerView.ViewHolder(itemView) {}
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        val card_view = viewHolder.itemView
        val info = participantInfoList!![i]
        val mPName = card_view.findViewById<TextView>(R.id.PName)
        mPName.text = info.name
        val mMobilenum = card_view.findViewById<TextView>(R.id.mobilenum)
        mMobilenum.text = info.mobilenum
        val mSemester = card_view.findViewById<TextView>(R.id.semester)
        mSemester.text = "Sem: " + info.semester
        val mPEmail = card_view.findViewById<TextView>(R.id.PEmail)
        mPEmail.text = info.email
        val mColgname = card_view.findViewById<TextView>(R.id.colgname)
        mColgname.text = info.college
        val mEventname = card_view.findViewById<TextView>(R.id.eventname)
        mEventname.text = info.event
    }
}