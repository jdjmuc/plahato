package de.algenubi.plahato.util

object StringUtils {
  def trimQuotes(s: String): String = {
    val quoteSpaceRegex = """(\s*")(.*?)("\s*)""".r
    s match {
      case quoteSpaceRegex(_, content, _) => content
      case _ => s
    }
  }
}
