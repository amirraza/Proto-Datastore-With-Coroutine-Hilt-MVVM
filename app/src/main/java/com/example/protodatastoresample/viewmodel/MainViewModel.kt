package com.example.protodatastoresample.viewmodel

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.protodatastoresample.datastoremanager.MainProtoDataStoreManager
import com.example.protodatastoresample.model.UserModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(@ApplicationContext context: Context) :
    ViewModel() {
    /**
     * Proto Datastore object reference to manipulate data in respocted .pb file
     */
    private val mainProtoDatastoreManager = MainProtoDataStoreManager(context)

    //listening the user changes as Flow
    var _userAsFlow = mainProtoDatastoreManager.getUserAsFlow()

    //getting the user object changes as LiveData
    var userAsLiveData = MutableLiveData<UserModel>()

    /**
     * Saving the user object into `User_Pref` Datastore file
     *
     * @param userModel     UserModel
     */
    fun saveUser(userModel: UserModel) {
        viewModelScope.launch {
            mainProtoDatastoreManager.saveUser(userModel)
        }
    }

    /**
     * Retrieve User object at once (NOT listening as Flow streamed) and update the livedata
     */
    fun getUser() {
        viewModelScope.launch {
            val user = mainProtoDatastoreManager.getUser()
            userAsLiveData.value = user
        }
    }
}