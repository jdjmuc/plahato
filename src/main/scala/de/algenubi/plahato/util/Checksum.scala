package de.algenubi.plahato.util

import java.util.zip.CRC32

import scala.collection.mutable

/**
  * Checksum calculator tool
  *
  * Created by jdj on 28.01.17.
  */
object Checksum {

  private val checksumCalc = new CRC32

  private val map = new mutable.HashMap[String,String]()


  def getChecksum(data: Any, url: Option[String] = None): String = {
    this.synchronized {
      if (url.isEmpty) {
        return getChecksumBytes(data)
      }
      map.get(url.get) match {
        case Some(text) => text
        case None =>
          val chksum = getChecksumBytes(data)
          map(url.get) = chksum
          chksum
      }
    }
  }

  private def getChecksumBytes(data: Any): String = {
    val bytes = data match {
      case text: String => text.getBytes()
      case byteArr: Array[Byte] => byteArr
      case any => any.toString.getBytes
    }
    checksumCalc.reset()
    checksumCalc.update(bytes)
    checksumCalc.getValue.toHexString
  }
}
