package de.algenubi.plahato.util

import java.util.zip.CRC32

object HtmlUtils {
  
  /**
   * Makes a link HTML tag
   * 
   */
  def makeLink(link: String, name: String, attributes: String = "") = {
    val isLocal = !link.startsWith("http://") && !link.startsWith("/") && !link.startsWith("#")
    val space = if (!attributes.isEmpty) " " else ""
    val contentType = ContentType(link) 
    val suffix = if (contentType == ContentType.NoSuffix) ".html" else ""
    val modLink = if (contentType == ContentType.Directory) link.stripSuffix("/") else link  
    "<a href=\"" +
      (if (isLocal) "/" else "") +
      modLink + suffix + "\"" + space + attributes + ">" + name + "</a>"
  }

  /**
    * Makes an ID for e.g. using in a menu
    * @param prefix prefix
    * @param text text from hashcode ID is generated
    * @return ID
    */
  def makeId(prefix: String, text: String): String = {
      val crc = new CRC32()
      crc.update(text.getBytes())
      val hash = crc.getValue
      f"${prefix}_$hash%x"
  }
}