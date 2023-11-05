package com.example.stopwatch

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Light
import androidx.compose.ui.unit.dp
import com.example.stopwatch.service.ServiceHelper
import com.example.stopwatch.service.StopwatchService
import com.example.stopwatch.service.StopwatchState

import com.example.stopwatch.util.Constants.ACTION_SERVICE_CANCEL
import com.example.stopwatch.util.Constants.ACTION_SERVICE_START
import com.example.stopwatch.util.Constants.ACTION_SERVICE_STOP

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@ExperimentalAnimationApi
@Composable
fun MainScreen(stopwatchService: StopwatchService) {
    val context = LocalContext.current
    val hours by stopwatchService.hours
    val minutes by stopwatchService.minutes
    val seconds by stopwatchService.seconds
    val currentState by stopwatchService.currentState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Black)
            .padding(30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.weight(weight = 9f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(targetState = hours, transitionSpec = { addAnimation() }, label = "") {
                Text(
                    text = hours,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = if (hours == "00") Color.White else Blue,
                    )
                )
            }
            AnimatedContent(targetState = minutes, transitionSpec = { addAnimation() }, label = "") {
                Text(
                    text = minutes, style = TextStyle(
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = if (minutes == "00") Color.White else Blue
                    )
                )
            }
            AnimatedContent(targetState = seconds, transitionSpec = { addAnimation() }, label = "") {
                Text(
                    text = seconds, style = TextStyle(
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = if (seconds == "00") Color.White else Blue
                    )
                )
            }
        }
        Row(modifier = Modifier.weight(weight = 1f)) {

            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(0.8f),
                onClick = {
                    ServiceHelper.triggerForegroundService(
                        context = context, action = ACTION_SERVICE_CANCEL
                    )
                },
                enabled = seconds != "00" && currentState != StopwatchState.Started,
                colors = ButtonDefaults.buttonColors(disabledContainerColor = White)
            ) {
                Text(text = "Cancel")
            }

            Spacer(modifier = Modifier.width(30.dp))

            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(0.8f),
                onClick = {
                    ServiceHelper.triggerForegroundService(
                        context = context,
                        action = if (currentState == StopwatchState.Started) ACTION_SERVICE_STOP
                        else ACTION_SERVICE_START
                    )
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = if (currentState == StopwatchState.Started) Red else Blue,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (currentState == StopwatchState.Started) "Stop"
                    else if ((currentState == StopwatchState.Stopped)) "Resume"
                    else "Start"
                )
            }

        }
    }
}

@ExperimentalAnimationApi
fun addAnimation(duration: Int = 600): ContentTransform {
    return slideInVertically(animationSpec = tween(durationMillis = duration)) { height -> height } + fadeIn(
        animationSpec = tween(durationMillis = duration)
    ) with slideOutVertically(animationSpec = tween(durationMillis = duration)) { height -> height } + fadeOut(
        animationSpec = tween(durationMillis = duration)
    )
}