package de.algenubi.plahato.actions

import de.algenubi.plahato.components.factories.LanguageFactory
import de.algenubi.plahato.conf.SessionConfiguration.langProp
import de.algenubi.plahato.util.LogCapable
import play.api.mvc.{Action, AnyContent, BaseController, Request}

/**
 * Trait with an action for handling languages
 */
trait LanguageHandling extends BaseController with LogCapable {

  def setLanguage(lang: String): Action[AnyContent] = Action {
    request =>
      var session = request.session
      if (session.data.contains(langProp)) {
        logger.fine("Clearing previous language value from session")
        session = session - langProp        
      }
      session = session + (langProp -> lang)
      logger.fine(s"Setting lang in session to $lang")
      Redirect(request.headers("referer")).withSession(session)
  }

}

object LanguageHandling extends LogCapable {

  def getLanguage(request: Request[_]): String = {
    request.session.get(langProp) match {
      case Some(lang) => {
        logger.fine(s"Session defined language $lang")
        lang
      }
      case None => {
        val lang = LanguageFactory.languages.default
        logger.fine(s"No language defined in session. Using default language $lang")
        lang
      }
    }
  }
  
}

