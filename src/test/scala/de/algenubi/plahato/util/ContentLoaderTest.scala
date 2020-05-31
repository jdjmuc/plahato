package de.algenubi.plahato.util

import de.algenubi.plahato.components.UnitTest

class ContentLoaderTest extends UnitTest {

    "title" should "be extracted from path" in {
      val testCases = Seq(
        ("/main/index.html","index"),
        ("/main/index","index")
      )
      testCases foreach {
        case (path, expectedTitle) =>
          ContentLoader.getTitleFromPath(path) mustBe expectedTitle
      }
    }
}
