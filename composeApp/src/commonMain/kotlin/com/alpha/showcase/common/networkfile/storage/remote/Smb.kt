package com.alpha.showcase.common.networkfile.storage.remote

import com.alpha.showcase.common.networkfile.storage.SMB
import com.alpha.showcase.common.networkfile.util.RConfig
import kotlinx.datetime.Clock
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import randomUUID

@Serializable
@SerialName("SMB")
class Smb(
  override val id: String = randomUUID(),
  override val host: String = "10.5.10.11",
  override val port: Int = SMB.defaultPort,
  override val user: String = "Drinbol",
  override val passwd: String,
  override val name: String,
  override val path: String = "/",
  override val isCrypt: Boolean = false,
  override val description: String = "",
  override val addTime: Long = Clock.System.now().toEpochMilliseconds(),
  override val lock: String = ""
): RemoteStorage() {

  override fun genRcloneOption(): List<String> {

    val options = ArrayList<String>()
    options.add(name)
    options.add("smb")
    options.add("host")
    options.add(host)
    options.add("user")
    options.add(user)
    options.add("port")
    options.add("$port")
    options.add("pass")
    options.add(RConfig.decrypt(passwd))
    return options
  }

  override fun genRcloneConfig(): Map<String, String> {
    val config = mutableMapOf<String, String>()
//    config["type"] = "smb"
//    config["host"] = host
//    config["user"] = user
//    config["port"] = "$port"
//    config["pass"] = RConfig.decrypt(passwd)
    return config
  }
}