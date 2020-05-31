package de.algenubi.plahato.util

/**
  * Defines the supported content types and contains a method to get them from the path
  */
object ContentType extends Enumeration with LogCapable {

  val Html, Jpeg, Png, Gif, Pdf, Svg, NotSupported, NoSuffix, Directory = Value

  /**
    * Gets the type of a path and adds a suffix if needed
    */
  def apply(path: String): ContentType.Value = {
    val htmlMatch = """(.+\.)(html{0,1})$""".r
    val jpgMatch = """.+\.(jpe{0,1}g)$""".r
    val pngMatch = """.+\.(png)$""".r
    val gifMatch = """.+\.(gif)$""".r
    val pdfMatch = """.+\.(pdf)$""".r
    val svgMatch = """.+\.(svg)$""".r
    val suffixMatch = """.+\.\w+$""".r

    val contentType = path match {
      case htmlMatch(prefix, suffix) => ContentType.Html
      case jpgMatch(_*) => ContentType.Jpeg
      case pngMatch(_*) => ContentType.Png
      case pdfMatch(_*) => ContentType.Pdf
      case gifMatch(_*) => ContentType.Gif
      case svgMatch(_*) => ContentType.Svg
      case suffixMatch(_*) => ContentType.NotSupported
      case s =>
        if (s.endsWith("/")) ContentType.Directory
        else ContentType.NoSuffix
    }
    logger.finest("Content type is " + contentType)
    contentType
  }
}
