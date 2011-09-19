package com.$domain_name$.$product_name$.$name$.env

import akka.dispatch.Dispatchers
import akka.actor.{Supervisor, Actor, Scheduler}
import akka.config.Supervision
import Supervision._
import Actor._
import com.mongodb.casbah.MongoConnection
import java.util.concurrent.TimeUnit
import dispatch.Http
import com.mongodb.ServerAddress
import com.mongodb.casbah.MongoConnection._
import com.$domain_name$.$product_name$.$name$.actor.$actor_name$

class DevelopmentEnvironment(env: String, http: Http) extends EnvironmentBasedActorRegistry(env, http) {
  override def mongoDbName: String = prop("mongoDbName", "$mongodb_db$")
}

class StagingEnvironment(env: String, http: Http) extends EnvironmentBasedActorRegistry(env, http) {
}

class QaEnvironment(env: String, http: Http) extends EnvironmentBasedActorRegistry(env, http) {
}

class DemoEnvironment(env: String, http: Http) extends EnvironmentBasedActorRegistry(env, http) {
}

class ProductionEnvironment(env: String, http: Http) extends EnvironmentBasedActorRegistry(env, http) {
  override def mongoUrl: String = prop("mongoUrl", "10.100.23.105,10.100.23.99")
}

class TestEnvironment(env: String, http: Http) extends EnvironmentBasedActorRegistry(env, http) {
  override def mongoDbName: String = prop("mongoDbName", "$mongodb_test_db$")
}

abstract class EnvironmentBasedActorRegistry(env: String, http: Http) {

  def mongoUrl: String = prop("mongoUrl", "$mongodb_host$")
  def mongoPort: Int = prop("mongoPort", $mongodb_port$)
  def mongoDbName: String = prop("mongoDbName", "$mongodb_db$")

  def host: String = prop("host", "$mongodb_host$")
  def port: Int = prop("port", 8140)

  val urlList = mongoUrl.split(",").toList.map(new ServerAddress(_))
  val db = urlList match {
    case List(s) => MongoConnection(s)(mongoDbName)
//    case s: List[String] => MongoConnection(s)(mongoDbName)
    case s: List[ServerAddress] => MongoConnection(s)(mongoDbName)
  }

  val myActor = actorOf(new $actor_name$(db("$mongodb_db$"))).start()

  val supervisor = Supervisor(
    SupervisorConfig(
      OneForOneStrategy(List(classOf[Exception]), 3, 1000),
      Supervise(
        myActor,
        Permanent) ::
      Nil))

  def prop(key: String, default: String): String = {
    if(System.getProperty(key) == null) default else System.getProperty(key)
  }

  def prop(key: String, default: Int): Int = {
    if(System.getProperty(key) == null) default else System.getProperty(key).toInt
  }

  def prop(key: String, default: Boolean): Boolean = {
    if(System.getProperty(key) == null) default else System.getProperty(key).toBoolean
  }
}