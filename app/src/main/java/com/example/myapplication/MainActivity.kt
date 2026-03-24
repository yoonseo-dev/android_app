package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. 출력할 1,000개의 대용량 더미(Dummy) 데이터 컬렉션 생성
        // (실제 앱에서는 Retrofit 네트워크 통신이나 Room DB에서 데이터를 가져옵니다)
        val dummyData = mutableListOf<ContactData>()
        for (i in 1..1000) {
            dummyData.add(ContactData("학생 $i", "010-1234-${String.format("%04d", i)}"))
        }

        // 2. 어댑터 인스턴스 생성 및 데이터 소스 주입
        val adapter = ContactAdapter(dummyData)

        // 3. RecyclerView 아키텍처 조립 (중요)
        binding.rvContacts.adapter = adapter

        // 4. LayoutManager 지정 (필수 설정)
        // 기본 1차원 선형 나열 (수직 스크롤)
        binding.rvContacts.layoutManager = LinearLayoutManager(this)
    }
}