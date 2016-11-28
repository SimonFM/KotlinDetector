package com.nintendont.utils

/**
 * Created by simon on 28/11/2016.
 */

class OpenCV {
    companion object {
        fun load(openCVPath: String){
            val libPath = "E:\\opencv\\build\\java\\x64\\opencv_java310.dll"
            val pathToLoad = if(openCVPath.isEmpty()) libPath else openCVPath
            try {
                System.load(pathToLoad)
            } catch (e: Exception) {
               println("Unable to load OpenCV: " + e.message)
            }

        }
    }
}