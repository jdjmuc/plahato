package de.algenubi.plahato.authorization.login

import play.api.mvc._

import scala.concurrent.Future

object SecuredAction {


  val SESSIONKEY = "sessionKey"

  val USER = "user"

  def apply[A](validAction: Action[A])(invalidAction: Action[A]): Action[A] =
    Action.async(validAction.parser) {
      request =>
        val session = request.session
        (session.get(SESSIONKEY), session.get(USER)) match {
          case (Some(key), Some(user)) =>
            if (CurrentLogins.isValid(key)) {
              validAction(request)
            } else {
              invalidAction(request)
            }
          case (_, _) =>
            invalidAction(request)
        }
    }

}

