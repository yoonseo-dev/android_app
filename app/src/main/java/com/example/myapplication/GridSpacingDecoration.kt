package com.example.myapplication

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacingPx: Int,
    private val includeEdge: Boolean
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        // 1. 현재 뷰의 어댑터 상 절대 위치(Index)를 조회
        val position = parent.getChildAdapterPosition(view)

        // 2. 해당 아이템이 그리드 상에서 몇 번째 열(Column)에 위치하는지 모듈러 연산으로 산출
        val column = position % spanCount

        if (includeEdge) {
            // 외곽 가장자리 여백을 포함하여 균등 분배하는 수식 적용
            outRect.left = spacingPx - column * spacingPx / spanCount
            outRect.right = (column + 1) * spacingPx / spanCount

            // 1열(첫 번째 행)에 속한 아이템들에게만 상단(Top) 여백을 별도로 부여
            if (position < spanCount) {
                outRect.top = spacingPx
            }
            outRect.bottom = spacingPx

        } else {
            // 화면 외곽 여백은 무시하고 아이템 사이의 중앙 공간만 산출하는 수식 적용
            outRect.left = column * spacingPx / spanCount
            outRect.right = spacingPx - (column + 1) * spacingPx / spanCount

            // 두 번째 행부터 상단(Top) 여백을 부여하여 행간 간격을 확보
            if (position >= spanCount) {
                outRect.top = spacingPx
            }
        }
    }
}