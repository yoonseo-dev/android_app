package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ItemListSimpleBinding

// 1. 간단한 문자열을 처리하는 어댑터 클래스
class SimpleListAdapter(private val items: List<String>) : RecyclerView.Adapter<SimpleListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemListSimpleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListSimpleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvListItem.text = items[position]
    }

    override fun getItemCount() = items.size
}
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataList = (1..45).map { "그리드 $it" }
        binding.rvMain.adapter = SimpleListAdapter(dataList) // 실습 1의 어댑터 재활용

        val gridManager = GridLayoutManager(this, 3)
        binding.rvMain.layoutManager = gridManager

        // dp 단위를 디바이스의 물리적 픽셀(px) 단위로 변환하는 연산 (16dp 기준)
        val spacingInPixels = (16 * resources.displayMetrics.density).toInt()

        // 산출된 픽셀 값과 설정 플래그를 주입하여 ItemDecoration 인스턴스화 및 부착
        binding.rvMain.addItemDecoration(
            GridSpacingItemDecoration(3, spacingInPixels, true)
        )
    }
}