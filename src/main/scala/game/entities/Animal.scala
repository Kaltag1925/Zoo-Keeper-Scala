package game.entities

import game.updatelists.{HasMove, HasHunger, HungerList}

class Animal(private var _x: Int = 0,
             private var _y: Int = 0) extends Entity with HasHunger with HasMove {
  val icon = 'A'

  //<editor-fold desc="Inherited from Entity">

  def x: Int = _x

  def y: Int = _y
  //</editor-fold>


  //<editor-fold desc="Inherited from HasHunger">

  private var _hunger: Int = 100
  def hunger: Int = _hunger
  //private def hunger_=(newHunger: Int): Unit = _hunger = newHunger
  override def updateHunger(): Boolean = {
    _hunger -= 1
    println(hunger)
    _stillHere = _hunger > 0
    _stillHere
  }
  //</editor-fold>


  //<editor-fold desc="Inherited from HasMove">

  override def updateMove(): Boolean = {
    var stillMoving = false
    stillHere && stillMoving
  }
  //</editor-fold>

  //<editor-fold desc="Inherited from Updatable / Entity">

  private var _stillHere = true
  override def stillHere: Boolean = _stillHere
  //private def stillHere_=(newStillHere: Boolean): Unit = _stillHere = newStillHere
  //</editor-fold>
}

object Animal {
  def apply(): Animal = {
    val animal = new Animal(_x = 5, _y = 5)
    HungerList += animal
    animal
  }
}
