package gui.rendering

import gui.style.Color
import TextContext._

object TextContext {
  case class TileCharacter(ch: Char, x: Int, y: Int, color: Color = Color.Black, isUnderlined: Boolean = false)
}

class TextContext {

  private case class Obstruction(x: Int, y: Int, width: Int, height: Int) {
    def inside(ox: Int, oy: Int): Boolean = {
      ox - x >= 0 && ox - x < width && oy - y >= 0 && oy - y < height
    }
  }

  private var obstructions: List[Obstruction] = Nil
  def addObstruction(x: Int, y: Int, width: Int, height: Int): Unit = obstructions ::= Obstruction(x, y, width, height)
  def clearObstructions(): Unit = obstructions = Nil

  def canWriteChar(x: Int, y: Int): Boolean = {
    obstructions.forall(!_.inside(x, y))
  }

  def fillChar(ch: Char, x: Int, y: Int, color: Color, isUnderlined: Boolean): Seq[TileCharacter] = {
    if (canWriteChar(x , y)) {
      Seq(TileCharacter(ch, x, y, color, isUnderlined))
    } else {
      Seq()
    }
  }


  def fillString(str: String, x: Int, y: Int, color: Color, isUnderlined: Boolean): Seq[TileCharacter] = {
    for ((ch, i) <- str.zipWithIndex; if canWriteChar(x + i, y)) yield {
      TileCharacter(ch, x + i, y, color, isUnderlined)
    }
  }

  def strokeLine(ch: Char, x0: Double, y0: Double, x1: Double, y1: Double, color: Color): Seq[TileCharacter] = {
    /*
     We want to know whether the 'y' value or the 'x' value has a greater change
     As that will determine our independent value
     */
    def greaterDifference: (Double, Double) = {
      if (y1 == y0) 0d -> 1d
      else if (x1 == x0) 1d -> 0d
      else if ((y1 - y0).abs <= (x1 - x0).abs) ((y1 - y0) / (x1 - x0)) -> 1d
      else 1d -> ((x1 - x0) / (y1 - y0))
    }

    def inOrder(n0: Double, n1: Double): (Double, Double) = {
      if (n0 > n1) n1 -> n0
      else n0 -> n1
    }

    val (lessX, greatX) = inOrder(x0, x1)
    val (lessY, greatY) = inOrder(y0, y1)

    var x: Double = lessX
    var y: Double = lessY
    val (yInc, xInc) = greaterDifference

    var ret: Seq[TileCharacter] = Seq()

    while (x.toInt <= greatX && y.toInt <= greatY) {
      ret :+= TileCharacter(ch, x.round.toInt, y.round.toInt, color, isUnderlined = false) //TODO: CAN THIS CAUSE SLOWDOWNS
      x += xInc
      y += yInc
    }

    ret
  }

  def strokeRect(ch: Char, x: Int, y: Int, width: Int, height: Int, color: Color): Seq[TileCharacter] = {
    strokeLine(ch, x, y, x+width-1, y, color) ++  // Top Line
    strokeLine(ch, x, y+1, x, y+height-2, color) ++  // Middle Part of Left Line
    strokeLine(ch, x+width-1, y+1, x+width-1, y+height-2, color) ++  // Middle Part of Right Line
    strokeLine(ch, x, y+height-1, x+width-1, y+height-1, color) // Bottom Line
  }

  def fillRect(ch: Char, x0: Int, y0: Int, width: Int, height: Int, color: Color): Seq[TileCharacter] = {
    (for (x <- x0 until width) yield {
      strokeLine(ch, x, y0, x, y0+height-1, color)
    }).flatten
  }
}
