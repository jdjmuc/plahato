package de.algenubi.plahato.util

/**
 * Class which may do some useful HTML transform operations on a given string with HTML code, e.g. to
 * add a default attribute to a specific type.
 *
 * By default this just reformats the HTML by means of the XML library
 */
trait HtmlTransformer {

  /**
   * Method to do the HTML transformation with some helper methods
   */
  def transform(html: String): String

  /**
   * Adds an attribute value to all the elements of a given name
   */
  def addAttribute(html: String, elem: String, attrName: String, attrValue: String): String = {

    val matchElemContent = raw"""(<$elem.*?)>""".r

    val matchAttrib = raw"""(.+)(?=$attrName)(?:$attrName\s*?=\s*?\"([^\"]+)\")(.*)""".r

    matchElemContent replaceAllIn (html, m => m match {
      case matchAttrib(prefix, curValue, suffix) => {
        val updValue = if (!curValue.contains(attrValue)) curValue + " " + attrValue else curValue
        prefix + s"""$attrName="$updValue"""" + suffix
      }
      case _ => m.group(1) + s""" $attrName="$attrValue">"""
    })
  }
}