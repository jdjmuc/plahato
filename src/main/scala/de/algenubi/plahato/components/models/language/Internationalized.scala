package de.algenubi.plahato.components.models.language

import de.algenubi.plahato.components.factories.LanguageFactory

trait Internationalized {

  def languages = LanguageFactory.languages
    
  private var _lang: String = languages.default

  def lang: String = _lang

  def lang_=(curLang: String) {
    if (_lang != curLang) {
      if (!languages.has(curLang)) {
        throw new IllegalArgumentException("Language " + curLang + " is not available.")
      }
      _lang = curLang
      handleUpdate
    }
  }

  def getText(defText: String, altText: Map[String, String]): String = {
    if (!LanguageFactory.languages.isDefault(lang) && altText.contains(lang)) {
      altText(lang)
    } else {
      defText
    }
  }

  /**
   * to be implemented in user of this trait. Handles what needs to be done
   * when the language changes.
   */
  def handleUpdate() {
    // do nothing by default
  }
}