package com.b1nd.alimo.data.remote.service

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.DetailNotificationModel
import com.b1nd.alimo.data.model.EmojiModel
import com.b1nd.alimo.data.remote.response.BaseResponse
import kotlinx.coroutines.flow.Flow

interface DetailService {

    suspend fun patchEmojiEdit(
        notificationId: Int,
        reaction: String
    ): Flow<Resource<BaseResponse<String?>>>

    suspend fun loadNotification(
        notificationId: Int
    ): Flow<Resource<BaseResponse<DetailNotificationModel>>>

    suspend fun loadEmoji(
        notificationId: Int
    ): Flow<Resource<BaseResponse<List<EmojiModel>>>>

}