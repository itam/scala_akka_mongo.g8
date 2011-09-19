package com.$domain_name$.$product_name$.$name$

import actor.$actor_name$
import org.scalatra.ScalatraServlet
import com.recursivity.commons.validator.{NotNullOrNone, ValidationGroup, ClasspathMessageResolver}
import net.liftweb.json.JsonParser._
import akka.actor.Actor
import akka.actor.Actor._

class $servlet_name$ extends ScalatraServlet {

  val myActor = Actor.registry.actorsFor[$actor_name$](classOf[$actor_name$]).head

  get("/:id") {
    "GET with id: " + params("id")
  }

  get("/$name$") {
    "GET with var: " + params("var")
  }

  post() {
    myActor ! "post"
  }
}