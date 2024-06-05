import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.swing.JFrame
import kotlin.concurrent.thread

class Showcase{
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
//            if (isMac()){
//                System.setProperty("apple.awt.application.appearance", "system")
//                System.setProperty("apple.awt.application.name", "Showcase App")
//                System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Showcase App")
//            }

            Showcase().main()
        }
    }

    fun main() = application {

        var rProcess: Process? = null
        val icon = painterResource("showcase_logo.png")
        Window(
            onCloseRequest = {
                rProcess?.destroy()
                exitApplication()
            },
            state = rememberWindowState(position = WindowPosition.Aligned(Alignment.Center), width = 960.dp, height = 640.dp),
            icon = icon,
            title = "",
        ) {
            val jFrame: JFrame = this.window

            LaunchedEffect(Unit) {

                jFrame.minimumSize = java.awt.Dimension(480, 640)

                if (isMacOS()){
                    jFrame.rootPane.putClientProperty("apple.awt.transparentTitleBar", true)
                    jFrame.rootPane.putClientProperty("apple.awt.fullWindowContent", true)
                }
            }

            Column(modifier = Modifier.fillMaxSize()) {
                MainApp()
            }

            val appSupportPath = Paths.get(getConfigDirectory())
            if (Files.notExists(appSupportPath)) {
                Files.createDirectories(appSupportPath)
            }
            val resourcesDir = File(System.getProperty("compose.application.resources.dir"))
            println("resourcesDir: $resourcesDir")

//            thread {
//                rProcess?.destroy()
//                rProcess = ProcessBuilder(resourcesDir.resolve("rclone").absolutePath, "serve", "http", "--addr", ":12121", "google:").start()
//                rProcess!!.inputStream.bufferedReader().readLine()
//                rProcess!!.waitFor()
//            }

        }
    }
}

