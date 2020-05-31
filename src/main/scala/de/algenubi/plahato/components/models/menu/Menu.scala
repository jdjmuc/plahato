package de.algenubi.plahato.components.models.menu

import de.algenubi.plahato.components.models.language.Internationalized

/**
 * Defines a menu, which may be a sub-menu
 * 
 * 
 */
class Menu(val menuComponents: List[MenuComponent], val optName: Option[String] = None, 
    val optLink: Option[String] = None, 
    private val altNames: Map[String, String] = Map.empty[String, String]) 
extends MenuComponent with Internationalized {
  
  def this(menuComponents: List[MenuComponent], name: String) = this(menuComponents, Some(name))

  def isSubMenu: Boolean = optName.isDefined
  
  def name: String = {
    optName match {
      case Some(s) => getText(s,altNames)
      case None => ""
    }
  }  
}

object Menu {
  def apply(menuComponents: List[MenuComponent]) = new Menu(menuComponents)  
  def apply(menuComponents: List[MenuComponent], name: String) = new Menu(menuComponents, Some(name))  
  def apply(menuComponents: List[MenuComponent], name: String, link: String) = new Menu(menuComponents, Some(name), Some(link))  
}

