package game.entities

trait Entity {
  def x: Int
  def y: Int
  def icon: Char
  def stillHere: Boolean
}
