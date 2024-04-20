package com.example.surf_lesson_4

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.surf_lesson_4.constants.InfoConstants
import com.example.surf_lesson_4.constants.IntentConstants
import com.example.surf_lesson_4.databinding.ActivitySurnameFlowBinding
import com.example.surf_lesson_4.functions.Info
import com.example.surf_lesson_4.functions.KeyboardUtils
import com.example.surf_lesson_4.functions.onNext

class SurnameFlowActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySurnameFlowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurnameFlowBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {

            val resultLauncher = registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data = result.data
                    val resultIntent = Intent(this@SurnameFlowActivity, NameFlowActivity::class.java)
                    data?.extras?.let {
                        resultIntent.apply {
                            this.putExtra(
                                IntentConstants.SURNAME_FIELD, editSurnamePlainTextView.text.toString()
                            )
                            this.putExtra(
                                IntentConstants.AGE_FIELD, it.getString(IntentConstants.AGE_FIELD)
                            )
                        }
                    }
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            }

            nextButton.setOnClickListener {
                val intent = Intent(this@SurnameFlowActivity, AgeFlowActivity::class.java)
                resultLauncher.launch(intent)
            }

            backButton.setOnClickListener {
                setResult(RESULT_CANCELED)
                startActivity(Intent(this@SurnameFlowActivity, NameFlowActivity::class.java))
            }

            closeButton.setOnClickListener {
                setResult(RESULT_CANCELED)
                startActivity(Intent(this@SurnameFlowActivity, MainActivity::class.java))
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