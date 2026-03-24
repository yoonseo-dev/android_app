package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.ImageView
import android.widget.LinearLayout
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.BaseAdapter
import android.widget.ListView
import com.example.myapplication.databinding.ActivityMainBinding

data class Contact(val name:String, val phone: String)

class MultiTypeAdapter(private val context: Context, private val dataList: List<Contact>) :
    BaseAdapter(){
    override fun getViewTypeCount(): Int = 3

    override fun getItemViewType(position: Int): Int {
        return position % 3
    }

    override fun getCount(): Int = dataList.size

    override fun getItem(position: Int): Any = dataList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewType = getItemViewType(position)
        var view = convertView

        if(view==null){
            val inflater = LayoutInflater.from(context)

            view = when(viewType){
                0 -> inflater.inflate(R.layout.item_simple, parent, false)
                1 -> inflater.inflate(R.layout.item_product, parent, false)
                else -> inflater.inflate(R.layout.item_feed, parent, false)
            }
        }

        val contact = dataList[position]

        when(viewType){
            0 -> {
                val tvName = view!!.findViewById<TextView>(R.id.tvName)
                val tvPhone = view.findViewById<TextView>(R.id.tvPhone)
                tvName.text = contact.name
                tvPhone.text = contact.phone
            }
            1 -> {
                val tvTitle = view!!.findViewById<TextView>(R.id.tvTitle)
                val tvPrice = view.findViewById<TextView>(R.id.tvPrice)
                tvTitle.text = "[상품] " + contact.name
                tvPrice.text = "가격: 10,000원"
            }
            2 -> {
                val tvAuthorName = view!!.findViewById<TextView>(R.id.tvAuthorName)
                val tvBody = view.findViewById<TextView>(R.id.tvBody)
                tvAuthorName.text = contact.name
                tvBody.text = "연락처: ${contact.phone}"
            }
        }
        return view!!
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // XML에 선언된 ListView 객체 생성
        val listView = ListView(this)
        setContentView(listView)

        // 대량의 더미 데이터 생성
        val dummyData = (1..1000).map { Contact("학생 $it", "010-0000-${String.format("%04d", it)}") }

        // 어댑터 연결 (멀티 뷰 타입 어댑터)
        val adapter = MultiTypeAdapter(this, dummyData)
        listView.adapter = adapter
    }
}