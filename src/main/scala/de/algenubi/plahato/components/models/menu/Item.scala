package de.algenubi.plahato.components.models.menu

import de.algenubi.plahato.components.models.language.Internationalized

/**
 * Single item in a menu
 * @constructor Constructs the item
 * @param name Name to display in the menu in the default language
 * @param link link to which this menu item refers
 * @param altNames Names in other languages then the default language
 */
class Item(private val _name: String,
  val link: Option[String] = None, private val altNames: Map[String, String] = Map.empty[String, String])
    extends MenuComponent with Internationalized {

  def name = getText(_name, altNames)
}

object Item {
  def apply(name: String, link: String) = new Item(name, Some(link))

  def apply(name: String, link: String, altNames: Map[String,String]) = new Item(name, Some(link), altNames)

  def apply(name: String) = new Item(name)
}