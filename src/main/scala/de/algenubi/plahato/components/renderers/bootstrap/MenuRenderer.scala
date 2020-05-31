package de.algenubi.plahato.components.renderers.bootstrap

import de.algenubi.plahato.components.models.menu.{Divider, Item, Menu, MenuComponent}
import de.algenubi.plahato.components.renderers.Renderer
import de.algenubi.plahato.util.{ContentLoader, HtmlUtils}
import de.algenubi.plahato.components.models.menu.Menu
import de.algenubi.plahato.util.HtmlUtils.makeLink

object Direction extends Enumeration {
  val Horizontal, Vertical = Value
}

class MenuRenderer(menu: Menu, direction: Direction.Value) extends Renderer[Menu] {

  val model: Menu = menu

  def renderContent(): Unit = {
    val dirclass = direction match {
      case Direction.Horizontal =>
        "btn-group"
      case Direction.Vertical =>
        "btn-group-vertical"
    }
    require(!model.isSubMenu)
    content ++= s"""<div class="$dirclass">""" + "\n"
    renderMenu(model)
    content ++= "</div>\n"
  }

  private def renderMenu(menu: Menu): Unit = {
    menu.lang = lang

    def renderMenuContent {
      for (component <- menu.menuComponents) {
        renderMenuComponent(component, menu.isSubMenu)
      }
    }

    if (menu.isSubMenu) {
      val headItem = new Item(menu.name, menu.optLink)
      content ++= """<div class="dropdown"> """
      if (headItem.link.isDefined) {
        content ++= "<div class=\"btn-group\">\n"
        renderItem(headItem, false, false)
        renderItem(new Item(""), false, true)
      } else {
        renderItem(headItem, false, true)
      }
      content ++=
        s"""
           |<div class="dropdown-menu" aria-labelledby="menu_${menu.name}">
           |""".stripMargin
      renderMenuContent
      content ++= "</div>\n"
      if (headItem.link.isDefined) {
        content ++= "</div>\n"
      }
      content ++= "</div>\n"
    } else {
      renderMenuContent
    }
  }


  private def renderMenuComponent(component: MenuComponent, isSubMenu: Boolean = false): Unit = {
    component match {
      case item: Item =>
        renderItem(item, isSubMenu, false)
      case menu: Menu =>
        if (!isSubMenu && menu.isSubMenu) {
          renderMenu(menu)
        }
      case div: Divider => renderDivider()
    }
  }

  private def renderItem(item: Item, isSubMenu: Boolean, isDropDown: Boolean) = {
    item.lang = lang
    if (isSubMenu) {
      val linkClass = s"""class="dropdown-item""""
      item.link match {
        case Some(link) => content ++= makeLink(link, item.name, linkClass)+"\n"
        case None => content ++= makeLink("#", item.name, linkClass)+"\n"
      }
    } else {
      val cls = "btn btn-default" + (if (isDropDown) " dropdown-toggle" else "")
      if (isDropDown) {
        content ++= s"""<button type="button" class="$cls" id="menu_${item.name}" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">"""
      } else {
        // no button for link without dropdown
      }
      content ++= {
        item.link match {
          case Some(link) =>
            makeLink(link, item.name, s"""role="button" class="$cls" id="${makeIdFromLink(link)}"""")
          case None => item.name + "<span class=\"caret\"></span>"
        }
      }
      if (isDropDown) {
        content ++= "</button>\n"
      } else {
        // no button for link without dropdown
        content ++= "\n"
      }
    }
  }

  def makeIdFromLink(link: String): String = {
    val titleOfPage = ContentLoader.getTitleFromPath(link)
    HtmlUtils.makeId("menuItem", titleOfPage)
  }

  private def renderDivider() = "<li role=\"separator\" class=\"divider\"></li>"

}
