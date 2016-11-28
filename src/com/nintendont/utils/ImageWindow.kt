package com.nintendont.utils

import org.opencv.core.Mat
import java.awt.FlowLayout
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel

/**
 * Created by simon on 21/11/2016.
 */
class ImageWindow {
    private var frame: JFrame? = null
    private var image: Mat = Mat()
    private var icon: ImageIcon? = null
    private var lbll: JLabel? = null

    constructor(image: Mat, title: String) {
        this.image = image
        newWindow(image, title)
    }

    private fun newWindow(image: Mat, title: String) {
        this.frame = JFrame(title)
        this.frame!!.layout = FlowLayout()
        setFrame(image)
    }

    fun setFrame(image: Mat) {
        val imageToDisplay = Mat2BufferedImage(image)
        this.icon = ImageIcon(imageToDisplay)
        this.frame!!.setSize(imageToDisplay.getWidth(null) + 50, imageToDisplay.getHeight(null) + 50)

        if (this.lbll != null) {
            this.frame!!.remove(this.lbll)
        }
        this.lbll = JLabel()
        this.lbll!!.icon = icon
        this.frame!!.add(lbll)

    }

    fun show() {
        frame!!.isVisible = false
        frame!!.isVisible = true
        frame!!.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    }

    private fun Mat2BufferedImage(m: Mat): BufferedImage {
        var type = BufferedImage.TYPE_BYTE_GRAY
        if (m.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR
        }
        val bufferSize = m.channels() * m.cols() * m.rows()
        val b = ByteArray(bufferSize)
        m.get(0, 0, b) // get all the pixels
        val image = BufferedImage(m.cols(), m.rows(), type)
        val targetPixels = (image.raster.dataBuffer as DataBufferByte).data
        System.arraycopy(b, 0, targetPixels, 0, b.size)
        return image
    }
}