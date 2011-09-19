package com.$domain_name$.$product_name$.$name$.actor

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject
import akka.actor.Actor

class $actor_name$(collection: MongoCollection) extends Actor {
  def receive = {
    case "test" => println("You found my test case!")
    case "post" => {
      println("POSTing some random text.")
      collection.insert(MongoDBObject("randomText" -> "POSTING some random text"))
    }
    case _ => println("received unknown msg")
  }
}