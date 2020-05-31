package de.algenubi.plahato.components.factories

import de.algenubi.plahato.components.models.language.Languages

/**
 * Contains the global languages object, which defines the available and default languages
 * for this website.
 */
object LanguageFactory {
  
  private var _languages = Languages()
  
  def languages_=(newVal: Languages) = _languages = newVal
  
  def languages: Languages = _languages
  
}