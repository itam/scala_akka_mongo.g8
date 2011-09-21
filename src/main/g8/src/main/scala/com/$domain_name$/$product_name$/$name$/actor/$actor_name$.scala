package com.$domain_name$.$product_name$.$name$.actor

import akka.dispatch.Dispatchers
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.MongoDBObject
import akka.actor.Actor._
import akka.routing._
import com.$domain_name$.$product_name$.$name$.{Fetch, Persist, Update}
import org.bson.types.ObjectId
import com.$domain_name$.$product_name$.$name$.model.$model_name$


object $actor_name${
  val dispatcher = Dispatchers.newExecutorBasedEventDrivenWorkStealingDispatcher("$actor_name$-dispatcher").build
}

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
      case f: Fetch[ObjectId] => {
        val dbo = collection.findOne(MongoDBObject("_id" -> f.value))

        self.channel ! grater[$model_name$].asObject(dbo.get)
      }
      case _ => println("received unknown msg")
    }
  })
}