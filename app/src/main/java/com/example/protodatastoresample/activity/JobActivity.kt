package com.example.protodatastoresample.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.protodatastoresample.viewmodel.MainViewModel
import com.example.protodatastoresample.R
import com.example.protodatastoresample.viewmodel.JobViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_job.*

@AndroidEntryPoint
class JobActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job)

        val viewmodel = ViewModelProvider(this).get(JobViewModel::class.java)

        lifecycleScope.launchWhenResumed {

            //Observe the message livedata from SettingsProto Datastore object that is saved
            //in `Job_Pref` datastore file
            viewmodel.message.observe(this@JobActivity, Observer {
                Toast.makeText(this@JobActivity, it, Toast.LENGTH_SHORT).show()
            })

            //Observe the job livedata from JobProto Datastore object that is saved
            //in `Job_Pref` datastore file and show in textview in the below formatted
            viewmodel.job.observe(this@JobActivity, Observer {
                showJob.text = """
                    Job ID: ${it.id}
                    Job Status: ${it.status}
                    Wallet Amount: ${it.walletAmount}
                    
                    Driver name: ${it.driver.name}
                    Driver age: ${it.driver.age}
                """.trimIndent()
            })

            //click to show message from SettingProto Datastore
            showSettingMsgBtn.setOnClickListener {
                viewmodel.showMsg()
            }

            //click to clear JobProto
            clearJobBtn.setOnClickListener {
                viewmodel.clearJob()
            }

            //Will save Dummy job at the start of screen
            viewmodel.saveJob()
        }
    }
}