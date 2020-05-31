package de.algenubi.plahato.util

import de.algenubi.plahato.components.factories.{LanguageFactory, MenuFactory}
import de.algenubi.plahato.components.models.menu.{Menu, MenuComponent}
import play.twirl.api.Html

/**
 * Convenience trait to provide the methods for getting a main- and submenu
 */
trait MenuHelper extends LogCapable {
  
  /**
   * Main menu definition
   */
  protected val mainMenu: Menu

  /**
    * Map for side bar menu's (will be selected by link names)
    */
  protected val menuMap: Map[String, List[MenuComponent]]
  
  /**
   * Main menu for the top bar
   */
  def getMainMenu(lang: String = LanguageFactory.languages.default): Html = {
    getMenu(mainMenu)
  }

  def getMenu(menu: Menu, lang: String = LanguageFactory.languages.default, name: String = "main"): Html = {
    MenuFactory.makeHorizontalMenu(name, menu, lang)    
  }
  
  /**
   * Sub menu for side bar
   */
  def getSubMenu(name: Option[String], lang: String = LanguageFactory.languages.default): Html = {
    name match {
      case Some(value) => {
        menuMap.get(value) match {
          case Some(menu) => {
            logger.fine("Matched menu: " + value)
            MenuFactory.makeVerticalMenu(value, Menu(menu), lang)
          }
          case None => {
            logger.fine("No menu found with name " + value)
            Html("")
          }
        }
      }
      case None => Html("")
    }
  }

}