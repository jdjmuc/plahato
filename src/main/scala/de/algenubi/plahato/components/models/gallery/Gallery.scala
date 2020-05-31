package de.algenubi.plahato.components.models.gallery

import scala.collection.mutable.ListBuffer
import scala.xml._

/**
 * Describes a photo gallery
 */
class Gallery(val title: String = "", val path: String = "") {

  val photos: ListBuffer[PhotoItem] = new ListBuffer()

}

object Gallery {

  def fromXML(node: Node, title: String = "", path: String = ""): Gallery = {
    if (node.label != "gallery") {
      throw new IllegalArgumentException("Root element of XML must be 'gallery'")
    }
    val gallery = new Gallery(title, path)

    // TODO: read XML file and fill list
    val items = node \ "item"

    def getNodeContent(node: Node, name: String): String = {
      val items = node \ name
      if (items.length > 0) items.head.text else ""
    }

    gallery.photos ++= items.map { node =>
      PhotoItem(
        node \@ "ima",
        getNodeContent(node, "title"),
        getNodeContent(node, "author"),
        getNodeContent(node, "desc"),
        getNodeContent(node, "date"),
        getNodeContent(node, "location"),
        getNodeContent(node, "instrument"))
    }

    return gallery
  }
}