package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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

        val dataList = (1..20).map { if(it==0) "★ 중요 헤더 공지사항" else "일반 아이템 $it" }
        binding.rvMain.adapter = SimpleListAdapter(dataList) // 실습 1의 어댑터 재활용
        binding.rvMain.layoutManager = LinearLayoutManager(this)

        // [핵심] 데코레이션의 누적성 (Additive Nature) 확인
        // 리사이클러뷰는 여러 데코레이션을 중첩 체인 형태로 동시 부착할 수 있습니다.
        val spacingPx = (8 * resources.displayMetrics.density).toInt()
        binding.rvMain.addItemDecoration(GridSpacingItemDecoration(1, spacingPx, true)) // 여백 확보용

        binding.rvMain.addItemDecoration(HeaderUnderlineDecoration()) // 그림 그리기용
    }
}