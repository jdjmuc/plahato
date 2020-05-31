package de.algenubi.plahato.util

import de.algenubi.plahato.components.UnitTest

class ContentTypeTest extends UnitTest {

  "path" should "be content type HTML" in {
    ContentType("test/index.html") mustBe ContentType.Html
  }

  it should "be content type JPEG" in {
    ContentType("test/index.jpg") mustBe ContentType.Jpeg
  }

  it should "be content type PNG" in {
    ContentType("test/index.png") mustBe ContentType.Png
  }

  it should "be content type NOSUFFIX" in {
    ContentType("test/index") mustBe ContentType.NoSuffix
  }

  it should "be content type NOT SUPPORTED" in {
    ContentType("test/index.wmv") mustBe ContentType.NotSupported
  }
  
}