package com.$domain_name$.$product_name$.$name$.boot

import org.slf4j.LoggerFactory
import dispatch.{thread, Http}
import akka.util.AkkaLoader
import javax.servlet.{ServletContextEvent, ServletContextListener}
import akka.actor.{BootableActorLoaderService, Actor}
import com.$domain_name$.$product_name$.$name$.env._

class Initializer extends ServletContextListener {

  val logger = LoggerFactory.getLogger("com.$domain_name$.$product_name$.$name$.boot.Initializer");
  logger.info("Running Initializer")

  val env = System.getProperty("env")
  logger.info("Starting $name$ Core in env: " + env)
  if (env == null){
    logger.error("Env must not be null!")
    throw new IllegalArgumentException("Illegal environment")
  }

  val http = new Http with thread.Safety

  env match {

    case "dev" => new DevelopmentEnvironment(env, http)
    case "test" => new TestEnvironment(env, http)
    case "stage" => new StagingEnvironment(env, http)
    case "demo" => new DemoEnvironment(env, http)
    case "prod" => new ProductionEnvironment(env, http)
    case "qa" => new QaEnvironment(env, http)
    case _ => throw new IllegalArgumentException("Illegal environment")
  }

   lazy val loader = new AkkaLoader
   def contextDestroyed(e: ServletContextEvent): Unit = {
     Actor.registry.shutdownAll()
     http.shutdown()
     loader.shutdown
   }
   def contextInitialized(e: ServletContextEvent): Unit =
//     loader.boot(true, new BootableActorLoaderService with BootableRemoteActorService) //<--- Important
     loader.boot(true, new BootableActorLoaderService {}) // If you don't need akka-remote

}