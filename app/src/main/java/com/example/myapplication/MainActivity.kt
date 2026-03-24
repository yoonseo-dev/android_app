package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.ImageView
import android.widget.LinearLayout
import android.util.TypedValue
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        populateScrollView()
    }

    private fun populateScrollView() {
        // 100개의 데이터를 수신했다고 가정합니다.
        for (i in 1..100) {
            // 1. 메모리에 새로운 TextView 인스턴스 할당
            val dynamicTextView = TextView(this).apply {
                text = "${i}번째 동적 데이터 블록입니다."
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                setPadding(0, 24, 0, 24)
            }

            // 2. ScrollView의 컨테이너(LinearLayout)에 뷰를 부착(Attach)
            binding.llContentContainer.addView(dynamicTextView)
        }

        // OOM(Out of Memory) 테스트 이벤트
        binding.btnCrashTest.setOnClickListener {
            // 대규모 이미지를 반복 생성하여 메모리 임계값을 초과시킵니다.
            for (i in 1..10000) {
                val imageView = ImageView(this).apply {
                    setImageResource(android.R.mipmap.sym_def_app_icon)
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        300 // 고정 높이 지정
                    )
                }
                binding.llContentContainer.addView(imageView)
            }
        }
    }
}