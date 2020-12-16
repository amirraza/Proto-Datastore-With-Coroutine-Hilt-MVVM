package com.example.protodatastoresample.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.protodatastoresample.viewmodel.MainViewModel
import com.example.protodatastoresample.R
import com.example.protodatastoresample.model.UserModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewmodel = ViewModelProvider(this).get(MainViewModel::class.java)

        lifecycleScope.launchWhenResumed {

            //click to save dummy user in UserProto object in User_Pref file
            saveUserBtn.setOnClickListener {
                val name = if(fullnameET.text.toString().isEmpty()) "DummyName" else fullnameET.text.toString()
                val phone = if(phoneET.text.toString().isEmpty()) 300232333 else phoneET.text.toString().toLong()
                val height = if(heightET.text.toString().isEmpty()) 5.3f else heightET.text.toString().toFloat()
                val isMale = maleSwitch.isChecked
                val imageUrl = "http://example.image"
                viewmodel.saveUser(UserModel(name, imageUrl, phone, height, isMale))
            }

            showUserBtn.setOnClickListener {
                viewmodel.getUser()
            }
            //open Job activity screen only
            viewJobBtn.setOnClickListener {
                startActivity(Intent(this@MainActivity, JobActivity::class.java))
            }

            //Observe user object as Flow anc convert to livedata to observe changes
            viewmodel._userAsFlow.asLiveData().observe(this@MainActivity, Observer {
                showUserTV.text = "Saved User:\n$it"
            })

            //Observe user object as livedata to observe changes
            viewmodel.userAsLiveData.observe(this@MainActivity, Observer {
                Toast.makeText(this@MainActivity, it.toString(), Toast.LENGTH_SHORT).show()
            })
        }
    }
}