package com.example.myapplication

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView

class HeaderUnderlineDecoration : RecyclerView.ItemDecoration() {

    // 도화지에 그림을 그릴 붓(Paint) 객체를 초기화합니다.
    // 안티앨리어싱(Anti-alias) 플래그를 설정하여 외곽선을 부드럽게 처리하고 색상과 선 두께를 지정합니다.
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FF5252") // 강렬한 붉은색
        strokeWidth = 12f // 12 픽셀 두께의 선
    }

    // 뷰 본체가 렌더링 완료된 이후 최상단 레이어(Foreground)에 오버레이를 그리는 콜백
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        if (childCount == 0) return

        // 캔버스의 좌우 경계 좌표 계산
        val left = parent.paddingLeft.toFloat()
        val right = (parent.width - parent.paddingRight).toFloat()

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            // 어댑터 상의 0번째 항목(헤더)인지 검사
            if (parent.getChildAdapterPosition(child) == 0) {

                // 0번 뷰 하단(Bottom) Y좌표 획득 후 선을 그림
                // 선 두께의 절반만큼 아래로 오프셋을 조정하여 뷰를 덮어쓰지 않도록 정밀 조정합니다.
                val bottomY = child.bottom.toFloat() + (paint.strokeWidth / 2)
                c.drawLine(left, bottomY, right, bottomY, paint)

                break // 목적을 달성했으므로 불필요한 루프 탈출
            }
        }
    }
}