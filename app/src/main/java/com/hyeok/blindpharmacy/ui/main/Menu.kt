package com.hyeok.blindpharmacy.ui.main

import com.hyeok.blindpharmacy.R

sealed class Menu(
    val text: String,
    val iconDrawableId: Int,
) {
    data object ChatBot : Menu(text = "챗 봇 상담", iconDrawableId = R.drawable.ic_bot)
    data object Detection : Menu(text = "화면 인식", iconDrawableId = R.drawable.ic_detection)
    data object DrugManage : Menu(text = "복용 관리", iconDrawableId = R.drawable.ic_manage)
    data object Setting : Menu(text = "설정", iconDrawableId = R.drawable.ic_setting)

}

object MenuRepository{
    val menuList = listOf(
        Menu.ChatBot,
        Menu.Detection,
        Menu.DrugManage,
        Menu.Setting
    )

}
