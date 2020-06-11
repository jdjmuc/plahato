package de.algenubi.plahato.authorization.login

import javax.inject.Inject
import play.api.mvc._

import scala.concurrent.Future
import SecuredAction._

trait SecuredAction extends BaseController {

  def secured[A](validAction: Action[A])(invalidAction: Action[A]): Action[A] =
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

object SecuredAction {

  val SESSIONKEY = "sessionKey"

  val USER = "user"

}

