package de.algenubi.plahato.components.factories

import de.algenubi.plahato.components.models.menu.Menu
import de.algenubi.plahato.components.renderers.Renderer
import de.algenubi.plahato.components.renderers.bootstrap.{Direction, MenuRenderer}
import play.twirl.api.Html

/**
 * Factory for menu components
 */
object MenuFactory extends RendererFactory[Menu] {

  def makeHorizontalMenu(name: String, menu: Menu, lang: String = LanguageFactory.languages.default): Html = {
    makeMenu(name, menu, Direction.Horizontal, lang)
  }

  def makeVerticalMenu(name: String, menu: Menu, lang: String = LanguageFactory.languages.default): Html = {
    makeMenu(name, menu, Direction.Vertical, lang)
  }

  private def makeMenu(name: String, menu: Menu, direction: Direction.Value, curLang: String): Html = {
    render(name, menu, new MenuRenderer(_, direction), curLang)
  }
}