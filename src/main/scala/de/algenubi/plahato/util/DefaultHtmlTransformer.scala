package de.algenubi.plahato.util

/**
 * The default HTML transformer does nothing
 */
class DefaultHtmlTransformer extends HtmlTransformer {

  def transform(html: String): String = {
		  html
  }
}