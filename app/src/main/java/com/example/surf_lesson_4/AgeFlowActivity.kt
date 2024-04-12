package com.example.surf_lesson_4

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.surf_lesson_4.constants.InfoConstants
import com.example.surf_lesson_4.constants.IntentConstants
import com.example.surf_lesson_4.databinding.ActivityAgeFlowBinding
import com.example.surf_lesson_4.functions.KeyboardUtils

class AgeFlowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgeFlowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgeFlowBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {

            finishRegistrationButton.setOnClickListener {
                if (editAgePlainTextView.text.toString().isEmpty()) {
                    Toast.makeText(
                        this@AgeFlowActivity, R.string.edit_text_field_is_empty, Toast.LENGTH_SHORT
                    ).show()
                } else {
                    var data: Bundle? = null
                    intent.extras?.let {
                        data = bundleOf(
                            InfoConstants.NAME to it.getString(IntentConstants.NAME_FIELD),
                            InfoConstants.SURNAME to it.getString(IntentConstants.SURNAME_FIELD),
                            InfoConstants.AGE to editAgePlainTextView.text.toString()
                        )
                    }
                    val newIntent = Intent(this@AgeFlowActivity, MainActivity::class.java)
                    data?.let { newIntent.putExtras(it) }
                    setResult(RESULT_OK, newIntent)
                    finish()
                }
            }

        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    KeyboardUtils.hideKeyboard(this)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

}