package gui.conversion

import gui.style.Color

object Implicits {
  //// Conver In ////

  implicit def swingColor2UiColor(color: java.awt.Color): Color = {
    Color(color.getRed, color.getGreen, color.getBlue)
  }

  implicit def sfxColor2UiColor(color: scalafx.scene.paint.Color): Color = {
    Color(color.red, color.green, color.blue)
  }

  implicit def jfxColor2UiColor(color: javafx.scene.paint.Color): Color = {
    Color(color.getRed, color.getGreen, color.getBlue)
  }

  //// Convert Out ////

  implicit def uiColor2SwingColor(color: Color): java.awt.Color = {
    new java.awt.Color(color.r.toFloat, color.g.toFloat, color.b.toFloat, 1)
  }

  implicit def uiColor2SSFXColor(color: Color): scalafx.scene.paint.Color = {
    scalafx.scene.paint.Color(color.r, color.g, color.b, 1)
  }

  implicit def uiColor2JFXColor(color: Color): javafx.scene.paint.Color = {
    new javafx.scene.paint.Color(color.r, color.g, color.b, 1)
  }
}
