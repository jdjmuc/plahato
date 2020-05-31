package de.algenubi.plahato.components.renderers.bootstrap

import de.algenubi.plahato.components.UnitTest
import de.algenubi.plahato.components.models.menu.{Divider, Item, Menu}

class MenuRendererTest extends UnitTest {

  val subMenu1 = Menu(List(Item("About", "about", Map("de" -> "Über")),
    Item("Services", "services", Map("de" -> "Dienste")),
    Divider(), Item("Products", "products",
      Map("de" -> "Produkte"))), "subMenu")
  val menu = Menu(List(Item("Home", "home"), subMenu1))

  val renderer = new MenuRenderer(menu, Direction.Horizontal)
  renderer.lang = "en"

  "Menu" should "contain bootstrap menu" ignore {
    val html = renderer.render
    html must startWith("""<div class="btn-group">
<a href="/home.html" role="button" class="btn btn-default" id="menuItem_71d60cd0">Home</a>
<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">subMenu<span class="caret"></span></button>
<ul class="dropdown-menu">
<li><a href="/about.html">About</a></li>
<li><a href="/services.html">Services</a></li>
<li><a href="/products.html">Products</a></li>
</ul>
</div>
""")
  }

  it should "contain german menu" ignore {
    renderer.lang = "de"
    val html = renderer.render
    html must startWith("""<div class="btn-group">
<a href="/home.html" role="button" class="btn btn-default">Home</a>
<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">subMenu<span class="caret"></span></button>
<ul class="dropdown-menu">
<li><a href="/about.html">Über</a></li>
<li><a href="/services.html">Dienste</a></li>
<li><a href="/products.html">Produkte</a></li>
</ul>
</div>
""")
  }

}