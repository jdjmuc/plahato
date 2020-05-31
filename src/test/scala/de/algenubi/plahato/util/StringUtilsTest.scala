package de.algenubi.plahato.util

import de.algenubi.plahato.components.UnitTest

class StringUtilsTest extends UnitTest {

  "quotes" should "be removed" in {
    StringUtils.trimQuotes(""" "test" """) mustEqual "test"
    StringUtils.trimQuotes(""""test"""") mustEqual "test"
    StringUtils.trimQuotes("""""""") mustEqual ""
  }

}
