package gui

import game.ZooMap
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.paint.Color

class Renderer(gc: GraphicsContext) {

  def render(map: ZooMap): Unit = {
    gc.fill = Color.White
    gc.fillRect(0, 0, 600, 600)
    for (e <- map.entities; if e.stillHere) {
      gc.fill = Color.Black
      gc.fillText(e.icon.toString, e.x*10, e.y*10)
    }
  }
}
