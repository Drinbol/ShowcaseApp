package com.alpha.showcase.common

import com.alpha.showcase.common.socket.DeviceDiscovery
import com.alpha.showcase.common.socket.TcpCommunication
import com.alpha.showcase.common.socket.TcpCommunication.receiveData
import com.alpha.showcase.common.socket.TcpCommunication.sendData
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock

object Startup {
	fun run() {
		Napier.base(DebugAntilog())
//		runBlocking {
//			println("Hello, World!")
//
//
//			// 服务器端
//			launch {
//				// 启动UDP监听
//				launch {
//					DeviceDiscovery.listenForBroadcasts { clientAddress ->
//						println("Discovered client at: $clientAddress")
//						launch {
//							DeviceDiscovery.sendResponse(clientAddress)
//						}
//					}
//				}
//
//				launch {
//					DeviceDiscovery.broadcastPresence()
//				}
//
//				// 启动TCP服务器
//				TcpCommunication.startServer { clientSocket ->
//					println("Client connected via TCP: ${clientSocket.remoteAddress}")
//					val receivedData = clientSocket.receiveData()
//					println("Received data: $receivedData")
////					clientSocket.sendData("Server received: $receivedData ${Clock.System.now()}")
////					clientSocket.close()
//				}
//
//
//			}
//		}
	}
}