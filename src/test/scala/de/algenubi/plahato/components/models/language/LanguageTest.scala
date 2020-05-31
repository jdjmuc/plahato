package de.algenubi.plahato.components.models.language

import de.algenubi.plahato.components.UnitTest
import de.algenubi.plahato.components.factories.LanguageFactory

class LanguageTest extends UnitTest with Internationalized {

  def testGetText: String = getText("Services", Map("de" -> "Dienste"))

  /**
   * Creates a new language
   */
  LanguageFactory.languages = Languages("en", Array("en", "de", "nl"))
  
  "Current language" should "set to default" in {
    languages.isDefault(lang)
  }

  "Text" should "be in English" in {
    testGetText mustBe("Services")
  }

  it should "be in German" in {
    lang = "de"
    testGetText mustBe("Dienste")
  }

  it should "be again in English, since Dutch not available" in {
    lang = "nl"
    testGetText mustBe("Services")
  }

  "Language set" should "fail" in {
    intercept[IllegalArgumentException] { lang = "fr" }
  }
}