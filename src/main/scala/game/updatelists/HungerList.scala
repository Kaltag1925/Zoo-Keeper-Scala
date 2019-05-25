package game.updatelists

import scala.collection.mutable

object HungerList extends UpdateList[HasHunger](_.updateHunger())

trait HasHunger extends Updatable {
  def hunger: Int
  def updateHunger(): Boolean
}
