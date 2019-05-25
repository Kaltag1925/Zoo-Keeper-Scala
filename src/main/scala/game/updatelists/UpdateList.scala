package game.updatelists

import scala.collection.parallel.immutable.ParVector

abstract class UpdateList[A <: Updatable](updateMethod: A => Boolean) {
  var updatees = ParVector[A]()
  def +=(a: A): Unit = updatees :+= a
  def update(): Unit = updatees = updatees.filter(updateMethod)
}

trait Updatable {
  def stillHere: Boolean
}

object UpdateList {
  private val lists = List(HungerList)

  def updatesLists(): Unit = {
    lists.foreach(_.update())
  }
}
