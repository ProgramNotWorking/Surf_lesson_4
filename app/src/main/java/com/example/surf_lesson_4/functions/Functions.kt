package com.example.surf_lesson_4.functions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.example.surf_lesson_4.AgeFlowActivity
import com.example.surf_lesson_4.R
import com.example.surf_lesson_4.SurnameFlowActivity
import com.example.surf_lesson_4.constants.InfoConstants
import com.example.surf_lesson_4.constants.IntentConstants

fun onNext(
    context: Context,
    nextActivity: String,
    editTextView: EditText,
    informationType: Info
): Intent? {
    val value = editTextView.text.toString()

    if (value.isEmpty()) {
        Toast.makeText(
            context, R.string.edit_text_field_is_empty, Toast.LENGTH_SHORT
        ).show()
        return null
    } else {
        val intent = Intent(
            context,
            when (nextActivity) {
                InfoConstants.SURNAME -> SurnameFlowActivity::class.java
                InfoConstants.AGE -> AgeFlowActivity::class.java
                else -> SurnameFlowActivity::class.java
            }
        )
        val key = when (informationType) {
            is Info.Name -> IntentConstants.NAME_FIELD
            is Info.Surname -> IntentConstants.SURNAME_FIELD
        }

        intent.putExtra(key, value)
        return intent
    }
}

object KeyboardUtils {
    fun hideKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocus = activity.currentFocus
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }
}

sealed class Info {
    data object Name : Info()
    data object Surname : Info()
}