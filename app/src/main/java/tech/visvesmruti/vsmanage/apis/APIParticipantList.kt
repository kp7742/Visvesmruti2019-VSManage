package tech.visvesmruti.vsmanage.apis

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.widget.Toast

import com.appizona.yehiahd.fastsave.FastSave

import org.json.JSONException
import org.json.JSONObject

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import tech.visvesmruti.vsmanage.Consts
import tech.visvesmruti.vsmanage.activities.ParticipantListActivity

class APIParticipantList(private val activity: Activity) : AsyncTask<Void, Void, String>() {
    private val client: OkHttpClient
    private var dialog: ProgressDialog? = null

    init {
        this.client = OkHttpClient()
    }

    override fun onPreExecute() {
        dialog = ProgressDialog(activity)
        dialog!!.setMessage("Fetching Participant List...")
        dialog!!.setCancelable(false)
        dialog!!.show()
    }

    override fun doInBackground(vararg voids: Void): String? {
        val formBody = FormBody.Builder()
                .add("apikey", FastSave.getInstance().getString(Consts.TOKEN, ""))
                .build()

        val request = Request.Builder()
                .url("https://visvesmruti.tech/api/participants/list/")
                .post(formBody)
                .build()

        try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    return response.body!!.string()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    override fun onPostExecute(s: String?) {
        dialog!!.cancel()
        if (s == null) {
            Toast.makeText(activity, "Error!, Internet", Toast.LENGTH_LONG).show()
        } else {
            try {
                val result = JSONObject(s)
                val code = result.getInt("Code")
                if (code == 200) {
                    val i = Intent(activity, ParticipantListActivity::class.java)
                    i.putExtra(Consts.DATA, result.getString(Consts.DATA))
                    activity.startActivity(i)
                } else if (code == 404) {
                    Toast.makeText(activity, result.getString("Message"), Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(activity, "Error!, Wrong Status", Toast.LENGTH_LONG).show()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                Toast.makeText(activity, "Error!, Result Problem", Toast.LENGTH_LONG).show()
            }

        }
    }
}
