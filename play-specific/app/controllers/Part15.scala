package controllers

import play.api._
import play.api.mvc._
import scalaz._
import scala.concurrent.Future
import services.UserService
import play.api.libs.concurrent.Execution.Implicits.defaultContext

/**
 * Original version, with `map` and `flatMap`.
 *
 * This is even using direct pattern matching on `Future` contents, to avoid double maps there.
 *
 * Note especially how far the `getOrElse` with the error is from the problem, in the outer maps.
 */
object Part15 extends Controller {

  def index = Action.async { request =>
    val data = request.queryString.mapValues(_.head)

    UserService.getUserName(data).map { username =>
      UserService.getUser(username).flatMap {
        case None => Future.successful(NotFound("User not found"))
        case Some(user) => {
          val email = UserService.getEmail(user)
          UserService.validateEmail(email).bimap(
            validatedEmail => {
              UserService.sendEmail(validatedEmail) map {
                case true => Ok("Mail successfully sent!")
                case false => InternalServerError("Failed to send email :(")
              }
            },
            errorMsg => Future.successful(InternalServerError(errorMsg))).fold(identity, identity) // This is annoying. We can do this with 'merge' in Scalaz 7.1
        }
      }
    } getOrElse Future.successful(BadRequest("Username missing from data!"))
  }

}
