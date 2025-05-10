package com.ling.voiceassistant.asr

import ai.picovoice.porcupine.Porcupine
import ai.picovoice.porcupine.PorcupineException
import android.content.Context
import java.io.File

class WakeWordDetector {

    private var porcupine: Porcupine? = null

    fun initialize(context: Context) {
        val keywordPath = copyAssetToFiles(context, "hello-Sunny_en_android_v3_0_0.ppn")
        try {
            porcupine = Porcupine.Builder()
                .setAccessKey("2QLWe2AeCvvbMqCFenaRcbh1oB1UX5BgrEFZ8SyYBNCNNLGmkp5Z4w==") // 替换为你的访问密钥
                .setKeywordPath(keywordPath) // 设置唤醒词模型文件路径
                .setSensitivity(0.5f) // 设置唤醒词检测敏感度
                .build(context)
        } catch (e: PorcupineException) {
            e.printStackTrace()
        }
    }

    private fun copyAssetToFiles(context: Context, assetFileName: String): String {
        val file = File(context.filesDir, assetFileName)
        if (!file.exists()) {
            context.assets.open(assetFileName).use { inputStream ->
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }
        return file.absolutePath
    }

    fun processAudioFrame(audioFrame: ShortArray) {
        porcupine?.let {
            val result = it.process(audioFrame) == 1
            if (result) {
                // 检测到唤醒词
                onWakeWordDetected()
            }
        }
    }

    private fun onWakeWordDetected() {
        // 处理唤醒事件
        println("唤醒词已检测到！")
    }

    fun release() {
        porcupine?.delete()
    }
}