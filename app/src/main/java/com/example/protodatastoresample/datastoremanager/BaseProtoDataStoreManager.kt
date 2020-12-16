package com.example.protodatastoresample.datastoremanager

import android.util.Log
import androidx.datastore.DataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.IOException

abstract class BaseProtoDataStoreManager {

    /**
     * This method will create the specific Proto DataStore where implemented in child class
     *
     * @param block     Callback with success/fail (Sometimes you need callback on clearing data)
     */
    abstract suspend fun clear(block: ((Boolean) -> Unit)? = null)

    /**
     * Generic method to save Proto Typed data in DataStore
     *
     * @param   data        Generic Proto Typed data to store
     * @param   dataStore   Specific Proto DataStore in which data need to be stored
     * @param   block       Callback to receive the success/fail storage operation
     */
    suspend inline fun <reified T : Any> save(data: T, dataStore: DataStore<T>,
                                              noinline block: ((Boolean) -> Unit)? = null) {
        withContext(Dispatchers.IO) {
            try {
                dataStore.updateData { data }
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


    /**
     * Generic method to get once (do not listen) Proto typed object from DataStore
     *
     * @param   dataStore   Specific Proto DataStore from which data need to be retrieved
     * @return  T?          Generic proto typed object or empty instance if not found
     */
    suspend inline fun <reified T : Any> getValue(datastore: DataStore<T>): T? {
        return withContext(Dispatchers.IO) {
            try {
                val data = datastore.data.first()
                withContext(Dispatchers.Main) { data }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) { T::class.java.newInstance() }
            }
        }
    }

    /**
     * Generic method to get (listening) Proto typed object from DataStore
     * Any changes in the proto object, this will emit changes
     *
     * @param   dataStore   Specific Proto DataStore from which data need to be retrieved as Flow
     *
     * @return  Flow<T>     Generic typed object as flow
     */
    inline fun <reified T> getValueAsFlow(datastore: DataStore<T>): Flow<T> =
        datastore.data
            .catch { exception ->
                if (exception is IOException) {
                    Log.e("TAG", "getValueFlow: $exception")
                    emit(T::class.java.newInstance())
                } else {
                    throw exception
                }
            }
}