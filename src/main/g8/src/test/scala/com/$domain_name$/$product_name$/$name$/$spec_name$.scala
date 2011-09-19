package com.$domain_name$.$product_name$.$name$

import org.scalatra.specs2.ScalatraSpec
import org.specs2.matcher.ThrownExpectations
import org.specs2.specification.Step
import akka.event.EventHandler
import com.mongodb.casbah.{WriteConcern, MongoConnection}
import com.$domain_name$.$product_name$.$name$.boot.Initializer

class $spec_name$ extends ScalatraSpec with ThrownExpectations {
  def is = args(traceFilter = includeTrace("com.zub.ss*")) ^
    Step {
      addServlet(classOf[$servlet_name$], "/$name$/*")
    } ^
    p ^
    "Testing GET with an ID" ! getTest() ^
    end

  def getTest() =
    get("/$name$/100") {
      body must not be empty
      status must be equalTo 200
    }
}