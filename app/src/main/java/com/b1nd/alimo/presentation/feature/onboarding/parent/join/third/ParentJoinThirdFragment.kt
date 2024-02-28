package com.b1nd.alimo.presentation.feature.onboarding.parent.join.third

import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentParentJoinThirdBinding
import com.b1nd.alimo.presentation.MainActivity
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.third.ParentJoinThirdViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.third.ParentJoinThirdViewModel.Companion.ON_CLICK_BACKGROUND
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.third.ParentJoinThirdViewModel.Companion.ON_CLICK_CERTIFICATION
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.third.ParentJoinThirdViewModel.Companion.ON_CLICK_CHECK
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.third.ParentJoinThirdViewModel.Companion.ON_CLICK_JOIN
import com.b1nd.alimo.presentation.utiles.hideKeyboard
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import com.b1nd.alimo.presentation.utiles.startActivityWithFinishAll
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ParentJoinThirdFragment :
    BaseFragment<FragmentParentJoinThirdBinding, ParentJoinThirdViewModel>(
        R.layout.fragment_parent_join_third
    ) {
    override val viewModel: ParentJoinThirdViewModel by viewModels()
    private val args: ParentJoinThirdFragmentArgs by navArgs()


    override fun initView() {
        var finish = true

        lifecycleScope.launch {
            viewModel.parentJoinState.collectLatest {
                val accessToken = it.accessToken
                val refreshToken = it.refreshToken
                if (accessToken != null && refreshToken != null) {
                    mBinding.joinBtnOff.visibility = View.INVISIBLE
                    mBinding.joinBtnOn.visibility = View.VISIBLE
                } else {
                    mBinding.error.visibility = View.VISIBLE
                }
            }
        }
        bindingViewEvent { event ->
            event.onSuccessEvent {
                when (it) {
                    ON_CLICK_BACK -> {
                        findNavController().navigate(R.id.action_parentJoinThird_to_onboardingThird)
                    }

                    ON_CLICK_BACKGROUND -> {
                        mBinding.idEditTextLayout.clearFocus()
                        view?.hideKeyboard()
                    }

                    ON_CLICK_JOIN -> {
                        startActivityWithFinishAll(MainActivity::class.java)
                    }

                    ON_CLICK_CERTIFICATION -> {

                        viewModel.postEmail(args.email)

                        mBinding.check.visibility = View.VISIBLE
                        mBinding.time.visibility = View.VISIBLE
                        mBinding.certification.visibility = View.GONE


                        object : CountDownTimer(300000, 1000) {

                            override fun onTick(millisUntilFinished: Long) {
                                val minutes = millisUntilFinished / 60000
                                val seconds = (millisUntilFinished % 60000) / 1000
                                val timeString = String.format("%d:%02d", minutes, seconds)
                                mBinding.time.text = timeString
                            }

                            override fun onFinish() {
                                mBinding.time.text = "0:00"
//                                findNavController().navigate(R.id.action_parentJoinThird_to_onboardingThird)
                            }

                        }.start()

                    }

                    ON_CLICK_CHECK -> {
                        Log.d("TAG", "initView: ${args.email} ${ mBinding.idEditText.text.toString()}")
                        viewModel.emailCheck(
                            email = args.email,
                            code = mBinding.idEditText.text.toString()
                        )
                        view?.hideKeyboard()
                    }
                }
            }
        }

        mBinding.checkLayout.bringToFront()


    }


}