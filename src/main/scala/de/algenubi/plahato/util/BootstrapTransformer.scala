package de.algenubi.plahato.util

import scala.xml.Elem

class BootstrapTransformer extends HtmlTransformer {

  def transform(html: String): String = {
    addAttribute(html, "img", "class", "img-fluid")
  }
}