package de.algenubi.plahato.components.renderers.bootstrap

import de.algenubi.plahato.components.UnitTest
import de.algenubi.plahato.components.models.gallery.{Gallery, GalleryTest}

import scala.xml.XML

class GalleryRendererTest extends UnitTest {
  
  val gallery = Gallery.fromXML(XML.loadString(GalleryTest.testXML))
  
  val renderer = new GalleryRenderer(gallery)
  
  val html = renderer.render 
  println(html)
  
  val lines = html.split("\n")
  
  def numOfMatches(text: String): Int = {
    lines.filter { _.contains(text) }.length
  }
  
  "gallery" should "contain 3 items" in {
    numOfMatches("gallery-item") mustBe 3
  }
  
  it should "have matching open/close div tags" in  {
    numOfMatches("<div") mustBe numOfMatches("</div>")
  }
}