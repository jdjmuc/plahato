package de.algenubi.plahato.util

/**
  * Replaces an HTML element with a replacement defined by a function
  * @param elemName name of the HTML element
  * @param replacement replacement function
  */
class ElementTransformer(elemName: String, replacement: () => String) extends HtmlTransformer {

  override def transform(html: String): String = {
    html.replace(s"<$elemName>", replacement())
  }
}
