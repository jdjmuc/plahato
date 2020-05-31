package de.algenubi.plahato.components.factories

import de.algenubi.plahato.components.renderers.Renderer
import play.twirl.api.Html

trait RendererFactory[M] {

  private val map = scala.collection.mutable.Map.empty[String, Renderer[M]]

  protected def render(name: String, model: M, creator: M => Renderer[M], lang: String): Html = synchronized {
      val renderer = map.get(name) match {
      case Some(value) => value
      case None => {
        val value = creator(model)
        map += (name -> value)
        value
      }
    }
    renderer.lang = lang
    Html(renderer.render)
  }

}