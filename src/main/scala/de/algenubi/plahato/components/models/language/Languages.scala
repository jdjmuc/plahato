package de.algenubi.plahato.components.models.language

/**
 * Stores th language
 *
 * @constructor Constructs the object with default language and available languages
 * @param _lang current language
 * @param availLanguages available languages (the first is the default language)
 */
case class Languages(val default: String = "en", 
    val available: Array[String] = Array[String]("en")) {
  
  def isDefault(lang: String): Boolean = lang == default
  
  def has(lang: String): Boolean = available.contains(lang)
}


