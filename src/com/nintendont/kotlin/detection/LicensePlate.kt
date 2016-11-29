package com.nintendont.kotlin.detection

import com.nintendont.kotlin.detection.utils.ImageWindow
import org.opencv.core.*
import com.nintendont.kotlin.detection.utils.NintendontImage
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import org.opencv.core.MatOfPoint
import java.util.ArrayList
import org.opencv.core.MatOfPoint2f
import org.opencv.core.Scalar

/**
 * Created by simon on 28/11/2016.
 */
class LicensePlate(original: NintendontImage){
    private val imageData : NintendontImage = original
    private val originalImage : NintendontImage = original
    private val GREEN : Scalar = Scalar(0.0, 255.0, 0.0)
    private val RED : Scalar = Scalar(0.0, 0.0, 255.0)

    fun maskImage(): Mat{
        var gaussianBlur = Mat(); var sobel = Mat()
        var open = Mat(); var close = Mat() ;
        var canny = Mat(); var thresholded = Mat() ; var thresholded1 = Mat()
        val dilate = Mat(); val erode = Mat()
        val kernel = Size(5.0, 5.0)
        val channels = imageData.getChannels(imageData.hlsImage)
        val min : Double = 200.0; val max : Double = 255.0
        var temp = Mat(); val hs = Mat()

        Imgproc.GaussianBlur(channels[1], gaussianBlur, kernel, 0.0)

        Imgproc.Sobel(gaussianBlur, sobel, -1, 1, 0)
        //Imgproc.Canny(gaussianBlur, canny, min, max)
        //var cannyAndSobel = canny.mul(sobel)

        Imgproc.threshold(sobel, thresholded, min, max, Imgproc.THRESH_BINARY)
        var kernelElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, Size(75.0, 75.0))
        Imgproc.morphologyEx(thresholded, dilate, Imgproc.MORPH_DILATE, kernelElement)
        kernelElement = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, Size(20.0, 20.0))
        Imgproc.morphologyEx(dilate, erode, Imgproc.MORPH_ERODE, kernelElement)
        imageData.image.copyTo(temp, erode)

//        ImageWindow(imageData.resizeImage(gaussianBlur), "gaussianBlur").show()
////        ImageWindow(imageData.resizeImage(canny), "Canny").show()
//        ImageWindow(imageData.resizeImage(sobel), "Sobel").show()
////        ImageWindow(imageData.resizeImage(cannyAndSobel), "cannyAndSobel").show()
//        ImageWindow(imageData.resizeImage(thresholded), "thresholdedWindow").show()
//        ImageWindow(imageData.resizeImage(dilate), "dilate").show()
//        ImageWindow(imageData.resizeImage(erode), "erode").show()
        //ImageWindow(imageData.resizeImage(temp), "mask").show()
        return temp;
    }

    fun findLicensePlateOutline(data : Mat): Mat{
        val contours = findContours(data)
        val imageWithContours : Mat = drawContours(contours, data)
        val maskedWindow = ImageWindow(imageData.resizeImage(imageWithContours), "imageWithContours")
        maskedWindow.show()
        return imageWithContours
    }

    private fun findContours(image : Mat): List<MatOfPoint>{
        val contours : List<MatOfPoint> = ArrayList()
        val hierarchy = Mat()
        val toBeProcessed = Mat()
        Imgproc.cvtColor(image, toBeProcessed, Imgproc.COLOR_RGB2GRAY)
        Imgproc.findContours(toBeProcessed, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE, Point(0.0, 0.0))
        return contours
    }

    private fun drawContours(contours: List<MatOfPoint>, image : Mat) : Mat{
        val src = Mat()
        image.copyTo(src)
        val maxContourArea = 10000.0
        val minContourArea = 300.0

        for(contour : MatOfPoint in contours){
            val approxCurve = MatOfPoint2f()
            val contourArea = Imgproc.contourArea(contour)

            //println("Current contour area: " + contourArea)
            val mat2f : MatOfPoint2f = MatOfPoint2f()
            contour.convertTo(mat2f, CvType.CV_32F)

            val approxDistance = Imgproc.arcLength(mat2f, true) * 0.02
            Imgproc.approxPolyDP(mat2f, approxCurve, approxDistance, true)

            val points = MatOfPoint(*approxCurve.toArray())
            val rect = Imgproc.boundingRect(points)
            draw(rect)
        }
        return originalImage.rgbImage
    }

    private fun draw(rect: Rect) {
        val aspectRatio: Double = rect.width.toDouble() / rect.height.toDouble()
        val edgeDensity = 0
        //println("Current contour ratio: " + aspectRatio)
        val topRightX: Double = (rect.x.toDouble() + rect.width)
        val topRightY: Double = (rect.y.toDouble() + rect.height)
        val bottomLeftX: Double = rect.x.toDouble() //- 10
        val bottomLeftY: Double = rect.y.toDouble() //- 10
        val colour = if (aspectRatio > 2 && aspectRatio < 12) RED else GREEN
        Imgproc.rectangle(originalImage.rgbImage, Point(bottomLeftX, bottomLeftY), Point(topRightX, topRightY), colour, 3)
    }

}