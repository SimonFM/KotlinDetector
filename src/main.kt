import com.nintendont.kotlin.detection.LicensePlate
import com.nintendont.kotlin.detection.utils.NintendontImage
import com.nintendont.kotlin.detection.utils.OpenCV
import org.opencv.core.Mat

/**
 * Created by simon on 21/11/2016.
 */

val images = arrayOf("C:\\Users\\simon\\Documents\\GitHub\\KotlinDetector\\src\\com\\nintendont\\resources\\images\\simons car.jpg",
                     "C:\\Users\\simon\\Documents\\GitHub\\KotlinDetector\\src\\com\\nintendont\\resources\\images\\car1.jpg",
                     "C:\\Users\\simon\\Documents\\GitHub\\KotlinDetector\\src\\com\\nintendont\\resources\\images\\car2.jpg",
                     "C:\\Users\\simon\\Documents\\GitHub\\KotlinDetector\\src\\com\\nintendont\\resources\\images\\car3.jpg",
                     "C:\\Users\\simon\\Documents\\GitHub\\KotlinDetector\\src\\com\\nintendont\\resources\\images\\car4.jpg",
                     "C:\\Users\\simon\\Documents\\GitHub\\KotlinDetector\\src\\com\\nintendont\\resources\\images\\car5.jpg",
                     "C:\\Users\\simon\\Documents\\GitHub\\KotlinDetector\\src\\com\\nintendont\\resources\\images\\car6.jpg",
                     "C:\\Users\\simon\\Documents\\GitHub\\KotlinDetector\\src\\com\\nintendont\\resources\\images\\american-cars-pics.jpg",
                     "C:\\Users\\simon\\Documents\\GitHub\\KotlinDetector\\src\\com\\nintendont\\resources\\images\\TestImage.jpg")


fun main(args : Array<String>) {
    OpenCV.load("")
    for (url : String in images){
        println(url)
        val licensePlate = LicensePlate(NintendontImage(url))
        val maskedImage : Mat = licensePlate.maskImage()
        licensePlate.findLicensePlateOutline(maskedImage)
       // System.`in`.read()
    }
}

