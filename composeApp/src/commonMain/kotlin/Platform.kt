import com.alpha.showcase.common.networkfile.RService
import com.alpha.showcase.common.networkfile.Rclone

interface Platform {
    val platform: PLATFORM_TYPE
    val name: String
    fun openUrl(url: String)
    fun getConfigDirectory(): String
}


const val PLATFORM_ANDROID = "Android"
const val PLATFORM_IOS = "iOS"
const val PLATFORM_DESKTOP = "Desktop"
const val PLATFORM_WINDOWS = "Windows"
const val PLATFORM_MACOS = "macOS"
const val PLATFORM_LINUX = "Linux"
const val PLATFORM_WEB = "Web"
const val PLATFORM_WEB_WASM = "Web/Wasm"
const val PLATFORM_WEB_JS = "Web/Js"

enum class PLATFORM_TYPE(val intValue: Int, val platformName: String) {
    UNKNOWN(0b00000000, "Unknown"),
    Android(0b00000001, PLATFORM_ANDROID),
    Ios(0b00000010, PLATFORM_IOS),
    Web(0b00000100, PLATFORM_WEB),
    WebWasm(0b00000101, PLATFORM_WEB_WASM),
    WebJS(0b00000110, PLATFORM_WEB_JS),
    Desktop(0b00001000, PLATFORM_DESKTOP),
    Windows(0b00001001, PLATFORM_WINDOWS),
    MacOS(0b00001010, PLATFORM_MACOS),
    Linux(0b00001011, PLATFORM_LINUX)
}



expect fun getPlatform(): Platform

fun getConfigDirectory(): String {
    return getPlatform().getConfigDirectory()
}

fun getPlatformName(): String {
    return getPlatform().name
}

fun isAndroid(): Boolean = getPlatform().platform == PLATFORM_TYPE.Android

fun isWindows(): Boolean {
    return getPlatformName().trim().lowercase().contains("windows")
}

fun isIos(): Boolean = getPlatform().platform == PLATFORM_TYPE.Ios

fun isMacOS(): Boolean {
    val platform = getPlatformName().trim().lowercase()
    return platform.contains("mac") || platform.contains("os x")
}

fun isDesktop(): Boolean {
    return (getPlatform().platform == PLATFORM_TYPE.Desktop) or isWindows() or isMacOS()
}

fun isWeb(): Boolean {
    return getPlatform().platform == PLATFORM_TYPE.Web || getPlatform().platform == PLATFORM_TYPE.WebWasm || getPlatform().platform == PLATFORM_TYPE.WebJS
}


const val TEST_KEY = "1234567890123456"
const val TEST_IV = "0123456789abcdef"// 长度必须是 16 个字节

expect fun randomUUID(): String

fun supportRClone() = isDesktop() || isAndroid()

expect fun rclone(): Rclone

expect fun rService(): RService