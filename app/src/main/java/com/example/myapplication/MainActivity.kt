package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ItemBoardBinding

// 1. 상태를 가변적(var)으로 변경할 수 있도록 데이터 클래스 설계
data class TodoItem(
    val id: Int,
    var content: String,
    var isCompleted: Boolean = false
)

// 2. 어댑터: 클릭 이벤트를 Activity로 넘겨주기 위한 람다 함수 두 개를 인자로 받습니다.
class BoardAdapter(
    private val dataList: List<TodoItem>,
    private val onUpdateClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {

    inner class BoardViewHolder(val binding: ItemBoardBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            // 뷰가 생성될 때 단 한 번 리스너를 장착하여 메모리 누수를 방지합니다.
            binding.btnUpdate.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) onUpdateClick(pos)
            }
            binding.btnDelete.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) onDeleteClick(pos)
            }
        }

        fun bind(item: TodoItem) {
            // 데이터의 완료 상태(isCompleted)에 따라 텍스트와 UI가 동적으로 달라지게 처리합니다.
            if (item.isCompleted) {
                binding.tvTask.text = "[완료] " + item.content
                binding.tvTask.setTextColor(android.graphics.Color.GRAY)
            } else {
                binding.tvTask.text = item.content
                binding.tvTask.setTextColor(android.graphics.Color.WHITE)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val binding = ItemBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BoardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount() = dataList.size
}

// 3. 메인 액티비티: 동적 데이터 제어의 주체
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BoardAdapter

    // 동적으로 데이터가 삽입/삭제되어야 하므로 MutableList로 관리합니다.
    private val todoList = mutableListOf<TodoItem>()
    private var idCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 초기 더미 데이터 투입
        todoList.add(TodoItem(idCounter++, "안드로이드 스튜디오 설치하기"))
        todoList.add(TodoItem(idCounter++, "RecyclerView 기초 복습"))

        // 1. 어댑터 조립 및 콜백 함수 구현
        adapter = BoardAdapter(
            dataList = todoList,
            onUpdateClick = { position ->
                // [수정] 해당 인덱스의 아이템 상태를 반전시키고
                todoList[position].isCompleted = !todoList[position].isCompleted

                // [핵심] 어댑터에게 데이터가 '변경'되었음을 통보하여 리스트를 재구성시킵니다.
                adapter.notifyDataSetChanged()
            },
            onDeleteClick = { position ->
                // [삭제] 컬렉션에서 실제 데이터를 날리고
                todoList.removeAt(position)

                // [핵심] 통보합니다.
                adapter.notifyDataSetChanged()
            }
        )

        binding.rvMain.adapter = adapter
        binding.rvMain.layoutManager = LinearLayoutManager(this)

        // 2. 추가 버튼 클릭 로직
        binding.btnAdd.setOnClickListener {
            val inputText = binding.etInput.text.toString()
            if (inputText.isBlank()) {
                Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // [추가] 새로운 항목을 배열에 삽입하고
            val newItem = TodoItem(idCounter++, inputText)
            todoList.add(newItem)

            // [핵심] 어댑터에 데이터가 갱신되었음을 통보합니다.
            adapter.notifyDataSetChanged()

            // 텍스트 필드를 비우고, 사용자가 편하게 볼 수 있도록 맨 밑으로 스크롤 이동
            binding.etInput.text.clear()
            binding.rvMain.scrollToPosition(todoList.size - 1)
        }
    }
}