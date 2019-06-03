# test-facility-sample


It display a list of facilities and its options. Each facility allows to select only one option from enabled options. The option are disabled/enabled based on the mutual exclusions list.

 ### Tech & Library used:

 - MVVM pattern using ViewModel, LiveData. It is used 'cause VM  expose data to View and rather than callback,
  VM hold data of view and its not gets destroyed on orientation change, Relation between View and ViewModel is many to many.
 - Room for local data storage
 - Dagger & Assisted for dependency injection, clean code, and flexibility.
 - Retrofit for Rest Api, Gson for Pojo class as adapter to Retrofit.
 - RxJava for Thread Scheduling, Api callback, Room database operation callback, and error handling.
 - Timber for logs.
 - Android X and Kotlin, Kotlin extension for codding,operation.
 - WorkManager for 24 hour periodic sync form server


