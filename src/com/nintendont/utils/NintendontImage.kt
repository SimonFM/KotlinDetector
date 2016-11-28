package com.nintendont.utils

import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Rect
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import java.util.*

/**
 * Created by simon on 21/11/2016.
 */
class NintendontImage {
    var url:                   String = ""
    var image:                 Mat = Mat()
    var greyImage:             Mat = Mat()
    var rgbImage:              Mat = Mat()
    var hlsImage:              Mat = Mat()
    private var mask:          Mat = Mat()
    private var blurred:       Mat = Mat()
    private var lowThreshold:  Int = 0
    private var highThreshold: Int = 0

    constructor(url : String){
        this.url = url
        this.lowThreshold = 100
        this.highThreshold = 255
        loadImage()
    }

    fun loadImage(){
        image = Imgcodecs.imread(url)
        rgbImage = Imgcodecs.imread(url)
//        rgb()
        hls()
        greyscale()
        blur()
    }

    fun hls(): Mat {
        this.hlsImage = Mat()
        Imgproc.cvtColor(image, hlsImage, Imgproc.COLOR_BGR2HLS)
        return this.hlsImage
    }

    fun rgb(): Mat {
        this.rgbImage = Mat()
        Imgproc.cvtColor(image, rgbImage, Imgproc.COLOR_RGBA2RGB)
        return this.rgbImage
    }

    fun greyscale(): Mat {
        this.greyImage = Mat()
        Imgproc.cvtColor(image, greyImage, Imgproc.COLOR_RGB2GRAY)
        return this.greyImage
    }

    fun getChannels(mat: Mat): List<Mat> {
        val channels = ArrayList<Mat>()
        Core.split(mat, channels)
        return channels
    }


    fun greyscale(imageToGreyScale: Mat): Mat {
        val greyImage = Mat()
        Imgproc.cvtColor(imageToGreyScale, greyImage, Imgproc.COLOR_RGB2GRAY)
        return greyImage
    }

    fun blur(): Mat {
        greyscale()
        Imgproc.blur(greyImage, blurred, Size(3.0, 3.0))
        return blurred
    }

    fun blur(image: Mat): Mat {
        val grey = greyscale(image)
        Imgproc.blur(grey, grey, Size(3.0, 3.0))
        return grey
    }


    fun doCanny(): Mat {
        greyscale()
        Imgproc.blur(greyImage, greyImage, Size(3.0, 3.0))
        val edges = Mat()
        Imgproc.Canny(greyImage, edges, lowThreshold.toDouble(), highThreshold.toDouble())
        return edges
    }

    fun doCanny(image: Mat): Mat {
        val grey = greyscale(image)
        Imgproc.blur(grey, grey, Size(3.0, 3.0))
        val edges = Mat()
        Imgproc.Canny(grey, edges, lowThreshold.toDouble(), highThreshold.toDouble())
        return edges
    }

    fun resizeImage(image: Mat): Mat {
        val rect = Rect(0, 0, image.cols(), image.rows())
        val crop = Mat(image, rect) // NOTE: this will only give you a reference to the ROI of the original data
        val resizeimage = Mat()
        val sz = Size(1000.0, 1000.0)
        Imgproc.resize(crop, resizeimage, sz)
        return resizeimage
    }
}