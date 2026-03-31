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

// 1. 데이터 객체를 보관할 ViewHolder 정의
class SimpleGridViewHolder(val binding: ItemSimpleGridBinding) : RecyclerView.ViewHolder(binding.root)

// 2. 어댑터 정의
class SimpleGridAdapter(private val dataList: List<String>) : RecyclerView.Adapter<SimpleGridViewHolder>() {

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

        val items = mutableListOf<String>()
        for (i in 1..100) {
            items.add("반응형 $i")
        }

        val adapter = SimpleGridAdapter(items)
        binding.rvSimpleGrid.adapter = adapter

        //동적 SpanCount 산출 함수 호출
        val optimalSpanCount = calculateOptimalSpanCount()

        //도출된 최적의 열 개수를 매니저에 주입
        val responsiveManager = GridLayoutManager(this, optimalSpanCount)
        binding.rvSimpleGrid.layoutManager = responsiveManager
    }

    /**
     * 디바이스의 물리적 디스플레이 너비를 측정하여
     * 아이템당 최소 권장 너비(120dp)를 보장하는 최대 열 개수를 수학적으로 산출합니다.
     */
    private fun calculateOptimalSpanCount(): Int {
        val displayMetrics = resources.displayMetrics

        //픽셀 단위인 화면 너비를 기기 밀도로 나누어 논리적 픽셀로 변환
        val screenwidthDp = displayMetrics.widthPixels / displayMetrics.density

        //한 아이템이 차지해야 할 최소 너비를 120dp로 설정
        val minimumItemWidthDp = 120

        //전체 너비를 아이템 너비로 나누어 화면에 들어갈 수 있는 열 개수를 계산
        val spanCount = (screenwidthDp / minimumItemWidthDp).toInt()

        //화면이 아무리 좁앋 최소 2열은 보장하도록 강제
        return spanCount.coerceAtLeast(2)
    }
}