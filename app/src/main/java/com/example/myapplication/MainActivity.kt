package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ItemSimpleGridBinding

class SimpleGridViewHolder(val binding: ItemSimpleGridBinding) : RecyclerView.ViewHolder(binding.root)

class SimpleGridAdapter(private val dataList: List<String>) : RecyclerView.Adapter<SimpleGridViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleGridViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSimpleGridBinding.inflate(inflater, parent, false)

        return SimpleGridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SimpleGridViewHolder, position: Int) {
        holder.binding.tvGridText.text = dataList[position]
    }

    override fun getItemCount(): Int = dataList.size
}
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. 출력할 1,000개의 대용량 더미(Dummy) 데이터 컬렉션 생성
        // (실제 앱에서는 Retrofit 네트워크 통신이나 Room DB에서 데이터를 가져옵니다)
        val items = mutableListOf<String>()
        for (i in 1..60) {
            items.add("항목 $i")
        }

        // 2. 어댑터 인스턴스 생성 및 데이터 소스 주입
        val adapter = SimpleGridAdapter(items)

        // 3. RecyclerView 아키텍처 조립 (중요)
        binding.rvSimpleGrid.adapter = adapter

        // 4. LayoutManager 지정 (필수 설정)
        // 기본 1차원 선형 나열 (수직 스크롤)
        val layoutManager = GridLayoutManager(this, 3)
        binding.rvSimpleGrid.layoutManager = layoutManager
    }
}