package com.kanawish.recordstore

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kanawish.recordstore.state.ProductEditorState
import com.kanawish.recordstore.state.ProductEditorState.*
import com.kanawish.recordstore.state.Product
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber

/**
 * https://developer.android.com/training/testing/junit-runner
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class MainActivityTest {

    @Test
    fun testFoo() {
        val database = Firebase.database
        val ref = database.getReference("types")
        ref.setValue("Hello world!")
            .addOnSuccessListener {
                Timber.i("onSuccess")
            }
            .addOnFailureListener {
                Timber.i("onFailure")
            }
            .addOnCanceledListener {
                Timber.i("onCanceled")
            }
            .addOnCompleteListener {
                Timber.i("onComplete")
            }

    }

    @Test
    fun testTypes() {
        val database = Firebase.database
        val ref = database.getReference("types/${ProductEditorState::class.simpleName!!}")
        ref.child(Closed::class.simpleName!!).setValue(Closed::class.simpleName!!)
        ref.child(Editing::class.simpleName!!).setValue(Editing(Product("Hello Product")))
        ref.child(Saving::class.simpleName!!).setValue(Saving(Product("Saving Product")))
        ref.child(Deleting::class.simpleName!!).setValue(Deleting(Product("Deleting Product")))
        ref.child(Error::class.simpleName!!).setValue(Error(Product("Error Product"), "Error Message"))
    }
}