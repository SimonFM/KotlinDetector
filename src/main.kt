import com.nintendont.utils.ImageWindow
import com.nintendont.utils.NintendontImage
import com.nintendont.utils.OpenCV
import org.opencv.core.Core

/**
 * Created by simon on 21/11/2016.
 */



fun main(args : Array<String>){
    OpenCV.load("")

    val url: String = "C:\\Users\\simon\\Documents\\GitHub\\KotlinDetector\\src\\com\\nintendont\\images\\simons car.jpg"
    val image: NintendontImage = NintendontImage(url)
    val rgbWindow : ImageWindow = ImageWindow(image.resizeImage(image.rgbImage), "RGB window")
    val hlsWindow : ImageWindow = ImageWindow(image.resizeImage(image.hlsImage), "HLS window")
    rgbWindow.show()
    hlsWindow.show()
}
