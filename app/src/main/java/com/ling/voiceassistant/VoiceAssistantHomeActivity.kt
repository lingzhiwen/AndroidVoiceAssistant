package com.ling.voiceassistant


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ling.voiceassistant.asr.WakeWordDetector

class VoiceAssistantHomeActivity : ComponentActivity() {

    private val wakeWordDetector = WakeWordDetector()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VoiceAssistantHomeUI(
                onStartListening = {
                    wakeWordDetector.initialize(this)
                },
                onStopListening = {
                    wakeWordDetector.release()
                }
            )
        }
    }
}

@Composable
fun VoiceAssistantHomeUI(
    onStartListening: () -> Unit,
    onStopListening: () -> Unit
) {
    var isListening by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf("点击开始监听唤醒词") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "智能语音助手",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = {
                if (isListening) {
                    onStopListening()
                    statusMessage = "监听已停止"
                } else {
                    onStartListening()
                    statusMessage = "正在监听唤醒词..."
                }
                isListening = !isListening
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(if (isListening) "停止监听" else "开始监听")
        }

        Text(
            text = statusMessage,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}