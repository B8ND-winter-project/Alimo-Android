package com.b1nd.alimo.feature.onboarding.third

import com.b1nd.alimo.base.BaseViewModel

class OnboardingThirdViewModel:BaseViewModel() {
    
    fun onClickBack() = viewEvent(ON_CLICK_BACK)

    fun onClickStudent() = viewEvent(ON_CLICK_STUDENT)

    fun onClickParent() = viewEvent(ON_CLICK_PARENT)

    fun onClickJoin() = viewEvent(ON_CLICK_JOIN)

    fun onClickLogin() = viewEvent(ON_CLICK_LOGIN)

    fun onClickDodamLogin() = viewEvent(ON_CLICK_DODAM_LOGIN)


    companion object{
        const val ON_CLICK_BACK = "ON_CLICK_BACK"
        const val ON_CLICK_STUDENT = "ON_CLICK_STUDENT"
        const val ON_CLICK_PARENT = "ON_CLICK_PARENT"
        const val ON_CLICK_JOIN = "ON_CLICK_JOIN"
        const val ON_CLICK_LOGIN = "ON_CLICK_LOGIN"
        const val ON_CLICK_DODAM_LOGIN = "ON_CLICK_DODAM_LOGIN"
    }
}