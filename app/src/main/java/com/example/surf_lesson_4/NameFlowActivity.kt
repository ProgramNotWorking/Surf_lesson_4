package com.example.surf_lesson_4

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.surf_lesson_4.constants.InfoConstants
import com.example.surf_lesson_4.databinding.ActivityNameFlowBinding
import com.example.surf_lesson_4.functions.Info
import com.example.surf_lesson_4.functions.KeyboardUtils
import com.example.surf_lesson_4.functions.onNext

class NameFlowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNameFlowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNameFlowBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {

            nextButton.setOnClickListener {
                val intent = onNext(
                    this@NameFlowActivity,
                    InfoConstants.SURNAME,
                    editNamePlainTextView,
                    Info.Name
                )
                intent?.let { startActivity(it) }
            }

            backButton.setOnClickListener {
                setResult(RESULT_CANCELED)
                finish()
            }

            closeButton.setOnClickListener {
                setResult(RESULT_CANCELED)
                finishAffinity()
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