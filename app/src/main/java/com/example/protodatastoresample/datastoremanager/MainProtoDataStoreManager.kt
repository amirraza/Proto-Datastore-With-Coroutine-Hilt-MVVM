package com.example.protodatastoresample.datastoremanager

import android.content.Context
import androidx.datastore.createDataStore
import com.example.protodatastoresample.UserProto
import com.example.protodatastoresample.model.UserModel
import com.example.protodatastoresample.protoserializer.UserProtoSerializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * This Manager is used to manage Proto DataStore for MainActivity
 */
class MainProtoDataStoreManager(context: Context) : BaseProtoDataStoreManager() {

    //DataStore reference which is used to manipulate object from database
    val userdatastore =
        context.createDataStore(fileName = "User_Pref", serializer = UserProtoSerializer)

    /**
     * This will save user object in `User_Pref` file
     * @param user      UserModel [contains user data used to build Proto Typed object to store]
     */
    suspend fun saveUser(user: UserModel) = withContext(Dispatchers.IO) {
        val userProto = UserProto.newBuilder()
            .setFullName(user.name)
            .setPhone(user.phone)
            .setHight(user.height)
            .setImageUrl(user.image)
            .setIsMale(user.isMale)
            .build()

        //calling the base class's save method to abstract the saving implementation
        save(userProto, userdatastore)
    }

    /**
     * Getting user from `User_Pref` file as proto object
     * Will get only once for each call of this method (NOT listening the changes)
     *
     * @return [UserModel] - After building from returned UserProto
     */
    suspend fun getUser() : UserModel {
        val userProto = getValue(userdatastore)
        return userProto?.let {
            UserModel(userProto.fullName, userProto.imageUrl, userProto.phone, userProto.hight, userProto.isMale)
        } ?: run {
            UserModel("", "", 0, 0f, false)
        }
    }

    /**
     * Getting user from `User_Pref` file as proto object
     * Will get in stream of data (listening the changes)
     *
     * @return  Flow<UserModel> - After building from streamed UserProto
     */
    fun getUserAsFlow() = getValueAsFlow(userdatastore).map {
        UserModel(it.fullName, it.imageUrl, it.phone, it.hight, it.isMale)
    }

    /**
     * will clear the Proto Object
     */
    override suspend fun clear(block: ((Boolean) -> Unit)?) {

    }
}