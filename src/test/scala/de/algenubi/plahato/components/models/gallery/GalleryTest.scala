package de.algenubi.plahato.components.models.gallery

import de.algenubi.plahato.components.UnitTest

import scala.xml.XML

class GalleryTest extends UnitTest {


  val gallery = Gallery.fromXML(XML.loadString(GalleryTest.testXML))

  "gallery" should "contain 3 items" in {
    gallery.photos.length mustBe 3
  }

  "element 2" should "contain the correct values" in {
    val el2 = gallery.photos(1)
    el2.author mustBe "Kepler"
    el2.description mustBe "A star"
    el2.title mustBe "Star"
    el2.imasrc mustBe "star.jpg"
    el2.dateStr mustBe "24-02-2016"
  }
}

object GalleryTest {
    val testXML = """
<gallery>
	<item ima="moon.jpg">
		<title>Moon</title>
		<author>Einstein</author>
		<desc>The moon</desc>
	</item>
	<item ima="star.jpg">
		<title>Star</title>
		<author>Kepler</author>
		<desc>A star</desc>
    <date>24-02-2016</date>
    <location>Muenchen</location>
    <instrument>Telescope</instrument>
	</item>
	<item ima="cluster.jpg">
		<title>Cluster</title>
		<desc>A open cluster</desc>
	</item>
</gallery>
"""

}