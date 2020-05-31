package de.algenubi.plahato.components.renderers.bootstrap

import de.algenubi.plahato.components.factories.LanguageFactory
import de.algenubi.plahato.components.models.language.{Internationalized, Languages}
import de.algenubi.plahato.components.renderers.Renderer

case class LangChoiceRenderer(curLang: String = LanguageFactory.languages.default, routePrefix: String = "language") extends Renderer[Languages] {

  val model: Languages = languages

  lang = curLang
  
  def renderContent = {
    content ++= """<div class="btn-group">""" + "\n"
    for (langItem <- languages.available) {
      val color = if (langItem == lang) "green" else "grey"
      val markup = s""" style="color: $color""""
      content ++= s"""<a href="/$routePrefix/$langItem" role="button" class="btn btn-default"$markup>$langItem</a>""" + "\n"
    }
    content ++= "</div>\n"
  }
}

