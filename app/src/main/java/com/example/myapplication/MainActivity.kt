package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. 시스템 프레임워크가 강제로 생성한 ActionBar의 참조(Reference)를 획득하여 제어
        // 기본 테마가 NoActionBar인 경우 null을 반환하므로 안전한 호출(?.) 필수
        supportActionBar?.title = "안드로이드앱개발"
        supportActionBar?.subtitle = "송윤서"
        // ==
        //val actionbar = getSupportActionBar()
        //actionBar?.setTitle("안드로이드앱개발") => 이 방식도 가능함

    }

    // 2. 시스템 액션바 영역에 메뉴를 인플레이트
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)
        return true // 이벤트 체인에 메뉴가 생성되었음을 알림
    }

    // 3. 인플레이트된 뷰들의 클릭 이벤트 라우팅
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                Toast.makeText(this, "공유 로직 수행", Toast.LENGTH_SHORT).show()
                true // 이벤트가 소비(Consumed)되었음을 시스템에 반환
            }
            R.id.action_delete -> {
                Toast.makeText(this, "삭제 로직 수행", Toast.LENGTH_SHORT).show()
                true // 이벤트가 소비(Consumed)되었음을 시스템에 반환
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}