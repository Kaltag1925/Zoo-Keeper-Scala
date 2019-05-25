package game.updatelists

object MoveList extends UpdateList[HasMove](_.updateMove())

trait HasMove extends Updatable{
  def updateMove(): Boolean
}