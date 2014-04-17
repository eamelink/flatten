package controllers

import play.api._
import play.api.mvc._
import scalaz._
import scala.concurrent.Future
import services.UserService
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scalaz._
import scalaz.Scalaz._
import scalaz.contrib.std.futureInstance

object Part16 extends Controller {

  def index = Action.async { request =>
    val data = request.queryString.mapValues(_.head)

    val result = for {
      username <- UserService.getUserName(data) \/> BadRequest("Username missing from request") |> Future.successful |> EitherT.apply
      user <- UserService.getUser(username).map { _ \/> NotFound("User not found") } |> EitherT.apply
      email = UserService.getEmail(user)
      validatedEmail <- UserService.validateEmail(email).leftMap(InternalServerError(_)) |> Future.successful |> EitherT.apply
      success <- UserService.sendEmail(validatedEmail).map { \/-(_) } |> EitherT.apply
    } yield {
      if(success) Ok("Mail successfully sent!")
      else InternalServerError("Failed to send email :(")
    }

    result.run.map { _.fold(identity, identity) }

  }

}

