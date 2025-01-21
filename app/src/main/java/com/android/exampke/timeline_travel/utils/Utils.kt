package com.android.exampke.timeline_travel.utils

import coil3.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

/**
 * coil3.Bitmap 객체를 JPEG 파일로 저장하는 함수
 *
 * @param file 저장할 파일 경로
 * @return 저장 성공 여부
 */
fun Bitmap.saveToFile(file: File): Boolean {
    return try {
        // OutputStream을 통해 파일에 Bitmap 저장
        val outputStream: OutputStream = FileOutputStream(file)
        this.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}