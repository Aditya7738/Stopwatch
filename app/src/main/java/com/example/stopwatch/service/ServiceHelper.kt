package com.example.stopwatch.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import com.example.stopwatch.MainActivity
import com.example.stopwatch.util.Constants.CANCEL_REQUEST_CODE
import com.example.stopwatch.util.Constants.CLICK_REQUEST_CODE
import com.example.stopwatch.util.Constants.STOP_REQUEST_CODE
import com.example.stopwatch.util.Constants.RESUME_REQUEST_CODE
import com.example.stopwatch.util.Constants.STOPWATCH_STATE

@ExperimentalAnimationApi
object ServiceHelper {

    private const val flag = PendingIntent.FLAG_IMMUTABLE

    fun stopPendingIntent(context: Context): PendingIntent? {
        val stopIntent = Intent(context, StopwatchService::class.java).apply {
            putExtra(STOPWATCH_STATE, StopwatchState.Stopped.name)
        }
        return PendingIntent.getService(context, STOP_REQUEST_CODE, stopIntent, flag)
    }

    fun cancelPendingIntent(context: Context): PendingIntent? {
        val cancelIntent = Intent(context, StopwatchService::class.java).apply{
            putExtra(STOPWATCH_STATE, StopwatchState.Canceled.name)
        }
        return PendingIntent.getService(context, CANCEL_REQUEST_CODE, cancelIntent, flag)
    }

    fun clickPendingIntent(context: Context): PendingIntent? {
        val clickIntent = Intent(context, MainActivity::class.java)

        return PendingIntent.getActivity(
            context, CLICK_REQUEST_CODE, clickIntent, flag
        )
    }

    fun resumePendingIntent(context: Context): PendingIntent? {
        val resumeIntent = Intent(context, StopwatchService::class.java).apply{
            putExtra(STOPWATCH_STATE, StopwatchState.Started.name)
        }
        return PendingIntent.getService(context, RESUME_REQUEST_CODE, resumeIntent, flag)
    }

    fun triggerForegroundService(context: Context, action: String) {
        Intent(context, StopwatchService::class.java).apply {
            this.action = action
            context.startService(this)
        }
    }

}

