package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ItemComplexHeaderBinding
import com.example.myapplication.databinding.ItemComplexProductBinding

// 1. 다형성을 위한 데이터 래퍼 클래스
sealed class GridData {
    object Header : GridData()
    data class Product(val name: String) : GridData()
}

// 2. 멀티 뷰 타입 어댑터 구현
class ComplexGridAdapter(private val items: List<GridData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_PRODUCT = 1
    }

    inner class HeaderViewHolder(val binding: ItemComplexHeaderBinding) : RecyclerView.ViewHolder(binding.root)

    inner class ProductViewHolder(val binding: ItemComplexProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: GridData.Product) {
            binding.tvProductName.text = product.name
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is GridData.Header -> VIEW_TYPE_HEADER
            is GridData.Product -> VIEW_TYPE_PRODUCT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_HEADER) {
            val binding = ItemComplexHeaderBinding.inflate(inflater, parent, false)
            HeaderViewHolder(binding)
        } else {
            val binding = ItemComplexProductBinding.inflate(inflater, parent, false)
            ProductViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is ProductViewHolder && item is GridData.Product) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int = items.size
}

// 3. 메인 액티비티
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 데이터 생성: 첫 번째 항목은 헤더, 나머지는 일반 상품 40개
        val dataList = mutableListOf<GridData>()
        dataList.add(GridData.Header)
        for (i in 1..50) {
            dataList.add(GridData.Product("상품 $i"))
        }

        val adapter = ComplexGridAdapter(dataList)
        binding.rvSimpleGrid.adapter = adapter

        // 복합적인 배치를 수용하기 위해 전체 화면 너비를 2등분(2열)으로 정의합니다.
        val complexManager = GridLayoutManager(this, 2)

        // SpanSizeLookup을 구현하여 위치별 점유 크기를 지정합니다.
        complexManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = adapter.getItemViewType(position)
                return when (viewType) {
                    // 헤더는 2칸을 모두 차지하여 화면을 가로로 꽉 채웁니다.
                    ComplexGridAdapter.VIEW_TYPE_HEADER -> 2
                    // 상품은 1칸만 차지하여 2열로 배치됩니다.
                    ComplexGridAdapter.VIEW_TYPE_PRODUCT -> 1
                    else -> 1
                }
            }
        }

        binding.rvSimpleGrid.layoutManager = complexManager
    }
}