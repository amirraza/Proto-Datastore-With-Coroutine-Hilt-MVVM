# Proto-Datastore-With-Coroutine-Hilt-MVVM
This sample project contains the usage of Jetpack Proto DataStore as Multiple files for datastore and multiple objects in the same file 

#### Brief Explanation:
- [common.proto](https://github.com/amirraza/Proto-Datastore-With-Coroutine-Hilt-MVVM/blob/main/app/src/main/proto/common.proto) contains `JobProto` and `UserProto`
- [settings.proto](https://github.com/amirraza/Proto-Datastore-With-Coroutine-Hilt-MVVM/blob/main/app/src/main/proto/settings.proto) contains `SettingsProto`
- [JobProtoSerializer](https://github.com/amirraza/Proto-Datastore-With-Coroutine-Hilt-MVVM/blob/main/app/src/main/java/com/example/protodatastoresample/protoserializer/JobProtoSerializer.kt), [SettingsProtoSerializer](https://github.com/amirraza/Proto-Datastore-With-Coroutine-Hilt-MVVM/blob/main/app/src/main/java/com/example/protodatastoresample/protoserializer/SettingsProtoSerializer.kt) and [UserProtoSerializer](https://github.com/amirraza/Proto-Datastore-With-Coroutine-Hilt-MVVM/blob/main/app/src/main/java/com/example/protodatastoresample/protoserializer/UserProtoSerializer.kt) are the `serializer classes` to read/write data to proto store
- [BaseProtoDataStoreManager](https://github.com/amirraza/Proto-Datastore-With-Coroutine-Hilt-MVVM/blob/main/app/src/main/java/com/example/protodatastoresample/datastoremanager/BaseProtoDataStoreManager.kt) is the base `abstract class` that contains the generic handling of saving and getting (at once OR as streamed - Flow&lt;T&gt;)`Proto object` as and `abstract` clear method.
- [JobProtoDataStoreManager](https://github.com/amirraza/Proto-Datastore-With-Coroutine-Hilt-MVVM/blob/main/app/src/main/java/com/example/protodatastoresample/datastoremanager/JobProtoDataStoreManager.kt) and [MainProtoDataStoreManager](https://github.com/amirraza/Proto-Datastore-With-Coroutine-Hilt-MVVM/blob/main/app/src/main/java/com/example/protodatastoresample/datastoremanager/MainProtoDataStoreManager.kt) are the child classes of `BaseProtoDataStoreManager` to handle its own implementation.
#### Demo
<img src="/docs/proto_datastore_demo.gif" width="200" height="360" />
