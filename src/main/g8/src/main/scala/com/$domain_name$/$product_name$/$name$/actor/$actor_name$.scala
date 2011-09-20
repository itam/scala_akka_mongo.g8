package com.$domain_name$.$product_name$.$name$.actor

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject
import akka.actor.Actor_
import akka.routing._

class $actor_name$(poolSize: Int, collection: MongoCollection) extends Actor with DefaultActorPool
                               with FixedCapacityStrategy
                               with SmallestMailboxSelector {

  def receive = _route
  def limit = 10
  def partialFill = true
  def selectionCount = 1

  self.dispatcher = $actor_name$.dispatcher

  def instance = actorOf(new Actor() {
    def receive = {
      case "test" => println("You found my test case!")
      case "post" => {
        println("POSTing some random text.")
        collection.insert(MongoDBObject("randomText" -> "POSTING some random text"))
      }
      case _ => println("received unknown msg")
    }
  })
}