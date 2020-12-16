package com.example.protodatastoresample.viewmodel

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.protodatastoresample.datastoremanager.JobProtoDataStoreManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch

class JobViewModel @ViewModelInject constructor(@ApplicationContext context: Context) : ViewModel() {

    /**
     * Proto Datastore object reference to manipulate data in respocted .pb file
     */
    private val jobProtoDatastoreManager = JobProtoDataStoreManager(context)

    //listening JobProto object from `Job_Pref` as Flow the convert as Livedata to observe in Screen
    val job = jobProtoDatastoreManager.getJobAsFlow().asLiveData()

    //get message string from SettingsProto object from `Job_Pref` as Livedata to observe in Screen
    val message = MutableLiveData<String>()

    /**
     * Save job in Proto Datastore
     */
    fun saveJob() {
        viewModelScope.launch {
            jobProtoDatastoreManager.saveJob()
        }
    }

    /**
     * This method will save the dummy message in SettingsProto and listen the callback of
     * data to be saved in Job_Pref file
     */
    fun showMsg() {
        viewModelScope.launch {
            val msg = "This message is from SettingsProto from the Same file of Job Proto"
            jobProtoDatastoreManager.setSettingsMsg(msg) {
                message.value = msg
            }
        }
    }

    /**
     * this will clear all data from Job_Pref file, in this case JobProto and SettingsProto
     */
    fun clearJob() {
        viewModelScope.launch {
            jobProtoDatastoreManager.clear()
        }
    }
}