package com.example.isaquecoelho.mbeventapp.data

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

object FirebaseConnection {

    private var database: FirebaseDatabase? = null
    private var storage: FirebaseStorage? = null

    private fun getDatabase(): FirebaseDatabase {
        if (database == null) {
            database = FirebaseDatabase.getInstance()
            database!!.setPersistenceEnabled(true)
        }
        return database as FirebaseDatabase
    }

    fun getDatabase(url: String): DatabaseReference {
        val databaseReference = getDatabase().getReference(url)
        databaseReference.keepSynced(true)
        return databaseReference
    }

    private fun getStorage(): FirebaseStorage{
        if(storage == null){
            storage = FirebaseStorage.getInstance()
        }
        return storage as FirebaseStorage
    }

    fun getStorage(url: String): StorageReference{
        return getStorage().getReferenceFromUrl(url)
    }

}
