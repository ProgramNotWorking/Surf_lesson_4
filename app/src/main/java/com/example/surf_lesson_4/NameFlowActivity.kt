package com.example.surf_lesson_4

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.surf_lesson_4.constants.IntentConstants
import com.example.surf_lesson_4.databinding.ActivityNameFlowBinding
import com.example.surf_lesson_4.functions.KeyboardUtils

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

            val resultLauncher = registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data = result.data
                    val resultIntent = Intent(this@NameFlowActivity, MainActivity::class.java)
                    data?.extras?.let {
                        resultIntent.apply {
                            this.putExtra(
                                IntentConstants.NAME_FIELD, editNamePlainTextView.text.toString(),
                            )
                            this.putExtra(
                                IntentConstants.SURNAME_FIELD, it.getString(IntentConstants.SURNAME_FIELD)
                            )
                            this.putExtra(
                                IntentConstants.AGE_FIELD, it.getString(IntentConstants.AGE_FIELD)
                            )
                        }
                    }
                    setResult(RESULT_OK, resultIntent)
                    finish()
                } else if (result.resultCode == RESULT_CANCELED) {
                    val intent = Intent(this@NameFlowActivity, MainActivity::class.java)
                    setResult(RESULT_CANCELED, intent)
                    finish()
                }
            }

            nextButton.setOnClickListener {
                if (editNamePlainTextView.text.toString() == "") {
                    Toast.makeText(
                        this@NameFlowActivity, R.string.edit_text_field_is_empty, Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val intent = Intent(this@NameFlowActivity, SurnameFlowActivity::class.java)
                    resultLauncher.launch(intent)
                }
            }

            backButton.setOnClickListener {
                finish()
            }

            closeButton.setOnClickListener {
                val intent = Intent(this@NameFlowActivity, MainActivity::class.java)
                setResult(RESULT_CANCELED, intent)
                finish()
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