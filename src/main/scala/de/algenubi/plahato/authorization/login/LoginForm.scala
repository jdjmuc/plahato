package de.algenubi.plahato.authorization.login

import de.algenubi.plahato.authorization.users.UserPass
import play.api.data.Form
import play.api.http.HeaderNames
import play.api.mvc.Results._
import play.api.mvc._
import play.twirl.api.Html

/**
  * Adds a login form to the application
  */
trait LoginForm extends BaseController with HeaderNames with play.api.i18n.I18nSupport {

  import play.api.data.Forms._

  val messagesAction: MessagesActionBuilder

  val loginForm = Form(
    mapping(
      "user" -> text,
      "password" -> text)
    (UserPass.apply)(UserPass.unapply)
  )

  /**
    * Template for the login form to be defined in the application
    *
    * @param form
    * @return
    */
  def loginFormTemplate(form: Form[UserPass])(implicit request: Request[_]): Html

  /**
    * Checks a login. Needs to be defined in the application
    * @param login user/pass for login
    * @return true if login is valid
    */
  def checkLogin(login: UserPass): Boolean

  /**
    * Continue to login secured area. TBD in application.
    * @return
    */
  def redirectSecurePage(request: Request[_]): Result

  /**
    * What to do when the login fails. TBD in application
    * @return
    */
  def failedLoginPage(request: Request[_]): Result

  /**
    * Action for the Login Form GET
    * @return
    */
  def loginGet: Action[AnyContent] = messagesAction {
    implicit request =>
      Ok(loginFormTemplate(loginForm))
  }

  /**
    * Action for the Login form POST
    */
  def loginPost = Action {
    implicit request: Request[AnyContent] =>
      loginForm.bindFromRequest.fold(
        formWithErrors => {
          BadRequest(loginFormTemplate(formWithErrors)).withSession(request.session)
        },
        login => {
          if (checkLogin(login)) {
            val key = CurrentLogins.newLogin
            val session = request.session + (SecuredAction.SESSIONKEY -> key) + (SecuredAction.USER -> login.user)
            redirectSecurePage(request).withSession(session)
          } else {
            failedLoginPage(request).withSession(request.session)
          }
        }
      )
  }

  /**
    * Logout action
    * @return 
    */
  def logout = Action {
    request =>
      val session = request.session
      val newSession = session.get(SecuredAction.SESSIONKEY) match {
        case Some(value) =>
          CurrentLogins.deleteSession(value)
          session - SecuredAction.SESSIONKEY - SecuredAction.USER
        case None =>
          session
      }
      redirectSecurePage(request).withSession(newSession)
  }

}
