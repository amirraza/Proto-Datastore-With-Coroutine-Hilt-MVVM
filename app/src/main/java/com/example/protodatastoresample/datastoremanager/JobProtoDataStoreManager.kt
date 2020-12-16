package com.example.protodatastoresample.datastoremanager

import android.content.Context
import androidx.datastore.createDataStore
import com.example.protodatastoresample.JobProto
import com.example.protodatastoresample.SettingProto
import com.example.protodatastoresample.protoserializer.JobProtoSerializer
import com.example.protodatastoresample.protoserializer.SettingsProtoSerializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * This Manager is used to manage Proto DataStore for JobActivity
 */
class JobProtoDataStoreManager(context: Context) : BaseProtoDataStoreManager() {

    //DataStore reference which is used to manipulate object from database
    val jobdatastore =
        context.createDataStore(fileName = "Job_Pref", serializer = JobProtoSerializer)
    val settingsDatastore =
        context.createDataStore(fileName = "Job_Pref", serializer = SettingsProtoSerializer)

    /**
     * This will save job object in `Job_Pref` file
     *
     * Building a dummy Job Proto object to store in database
     */
    suspend fun saveJob() {
        val dp = JobProto.DriverProto.newBuilder()
            .setDriverId("some_driverId")
            .setName("DriverName")
            .setAge(24)
            .build()

        val jp = JobProto.newBuilder()
            .setId("A3f5323adsf42fD4fF")
            .setStatus(false)
            .setWalletAmount(4)
            .setDriver(dp)
            .build()

        //calling the base class's save method to abstract the saving implementation
        save(jp, jobdatastore)
    }

    /**
     * Getting Job from `Job_Pref` file as proto object
     * Will get in stream of data (listening the changes)
     *
     * @return  Flow<JobProto>
     */
    fun getJobAsFlow() = getValueAsFlow(jobdatastore)

    /**
     * Getting Settings Proto from `Job_Pref` file as proto object
     * Will get only once of data (NOT listening the changes)
     *
     * @return  SettingsProto
     */
    suspend fun getSettingsMsg() = getValue(settingsDatastore)

    /**
     * Set the message for SettingsProto
     *
     * @param   msg    message to be stored in SettingsProto
     */
    suspend fun setSettingsMsg(msg: String, block: (Boolean) -> Unit) {
        val sp = SettingProto.newBuilder().setMessage(msg).build()
        save(sp, settingsDatastore) {
            block(it)
        }
    }

    /**
     * This will clear values of JobProto object
     *
     * @param block     Callback with success/fail
     */
    override suspend fun clear(block: ((Boolean) -> Unit)?) {
        withContext(Dispatchers.IO) {
            try {
                jobdatastore.updateData { it.toBuilder().clear().build() }
                block?.let {
                    withContext(Dispatchers.Main) { it(true) }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                block?.let {
                    withContext(Dispatchers.Main) { it(false) }
                }
            }

        }
    }
}