package com.$domain_name$.$product_name$.$name$

import _root_.net.liftweb.json.Serialization._
import _root_.net.liftweb.json.Serialization._
import actor.$actor_name$
import model.$model_name$
import org.scalatra.ScalatraServlet
import com.recursivity.commons.validator.{NotNullOrNone, ValidationGroup, ClasspathMessageResolver}
import net.liftweb.json.JsonParser._
import akka.actor.Actor
import akka.actor.Actor._
import javax.servlet.http.HttpServletResponse

case class Fetch[T](value: T)
case class Persist[T](value: T, id: Long)
case class Update[T](value: T, id: Long)

class $servlet_name$ extends ScalatraServlet {

  val myActor = Actor.registry.actorsFor[$actor_name$](classOf[$actor_name$]).head

  afterAll {
    contentType = "application/json"
  }

  notFound {
    contentType = "application/json"
    status(404)
  }

  post("/:id") {
    try {

      val requestBody = request.body
      val objectId = params("id").toLong

      myActor ! "post"
    } catch {
      case e: IllegalArgumentException => halt(HttpServletResponse.SC_NOT_FOUND)
    }
  }

  get("/:id") {
    try {

      val objectId = params("id").toLong

//      val result: Option[$model_name$] <- myActor !!! Fetch[Long](objectId)
      val result = for {
        r: $model_name$ <- myActor !!! Fetch[Long](objectId)
      } yield r

      val responseString = write(result.get)
      responseString
    } catch {
      case e: IllegalArgumentException => halt(HttpServletResponse.SC_NOT_FOUND)
    }
  }

  put("/:id") {

    try {

      val objectId = params("id").toLong
      val requestBody = request.body

      "PUT with id: " + params("id")
    } catch {
      case e: IllegalArgumentException => halt(HttpServletResponse.SC_NOT_FOUND)
    }
  }
}