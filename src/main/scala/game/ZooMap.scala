package game

import game.entities.Entity

import scala.collection.mutable

class ZooMap(val x: Int, val y: Int) {
  val entities = mutable.ListBuffer[Entity]()
}
