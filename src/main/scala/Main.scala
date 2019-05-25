import java.awt.{Dimension, Graphics}

import game.ZooMap
import game.entities.Animal
import game.updatelists.UpdateList
import gui.elements.{Label, MenuItem, Readout, ScrollList}
import gui.windows.{Frame, Menu}
import gui.Renderer
import gui.rendering.TextContext
import gui.rendering.TextContext.TileCharacter
import javax.swing.{JFrame, JPanel}
import scalafx.animation.{Animation, AnimationTimer}
import scalafx.application
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.canvas.Canvas
import scalafx.scene.input.{KeyCode, KeyEvent}
import scalafx.Includes._
import scalafx.scene.control.{SplitPane, TextField}
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color
import scalafx.scene.text.{Font, Text}
import gui.conversion.Implicits._
import scalafx.application.JFXApp.PrimaryStage

import scala.util.Random

object Main extends JFXApp {
  val zooMap = new ZooMap(10, 10)
  var renderer: Renderer = _
  var uiWindow: Frame = _
  // val zooCanvas = new Canvas(10, 20)
  //renderer = new Renderer(zooCanvas.graphicsContext2D)

  stage = new PrimaryStage() {
    title = "Zoo Keeper"
    scene = new Scene(600, 600) {
      val splitPane = new SplitPane()
      val sideBarTC = new TextContext()
      val mainMenu = new Menu(0, 0, 200, 600)

      val gameFont: Font = Font.loadFont(getClass
        .getResourceAsStream("/PxPlus_IBM_EGA9.ttf"), 14)

      val pane = new Pane()

      val wrongMenu = new Menu(0, 0, 200, 600)
      wrongMenu.children = List(new Label("Wrong Menu >:V", 0, 1, 20, 35))

      var animals = 0

      val rightMenu = new Menu(0, 0, 200, 600)
      rightMenu.children = List(new Label("Right Menu :D", 0, 1, 20, 10),
        new Readout("Number of Animals: %0", 0, 11, 20, 35, () => this.animals.toString)
      )

      val menuPane = new Pane()

      val listMenu = new Menu(0, 0, 200, 600)
      val scrollList = new ScrollList("Test", 0, 1, 10, 10)
      for (i <- 1 to 10) scrollList.addItem(s"Option #$i")
      listMenu.children = List(scrollList)

      mainMenu.children = List(
        new MenuItem("Wrong Menu", KeyCode.W, wrongMenu, 0, 1, 20, 1),
        new MenuItem("Correct Menu", KeyCode.C, rightMenu, 0, 2, 20, 1),
        new MenuItem("List Menu", KeyCode.L, listMenu, 0, 3, 20, 1),
        new MenuItem("Wrong Menu", KeyCode.O, wrongMenu, 0, 4, 20, 1)
      )


      uiWindow = new Frame(sideBarTC, 200, 600, mainMenu)

      onKeyPressed = (ke: KeyEvent) => {
        if (ke.code != KeyCode.A) {
          uiWindow.processKeyPress(ke.code)
        } else {
          animals += 1
        }
      }
      pane.children = List.fill(80)(new Text("Hello Cutie UwU") {
        layoutX = Random.nextInt(400)
        layoutY = Random.nextInt(600)
      })

      splitPane.items ++= List(pane, menuPane)
      content = splitPane

      def renderMenu(): Unit = {

        def drawTileCharacter(tch: TileCharacter): Text = {
          val text = new Text(tch.x * 9, tch.y * 14, tch.ch.toString)
          text.font = gameFont
          text.fill = tch.color
          text.underline = tch.isUnderlined
          text
        }

        menuPane.children = uiWindow.getTileInfo().map(drawTileCharacter)
      }

      def gameLoop(): Unit = {
        val nanosPerFrame = 1 / 60 * 1e9
        var lastUpdate = 0L
        val animationTimer = AnimationTimer(t =>
          if (t - lastUpdate > nanosPerFrame) {

            pane.children = List.fill(80)(new Text("Hello Cutie UwU") {
              layoutX = Random.nextInt(400)
              layoutY = Random.nextInt(600)
            })

            lastUpdate = t
            UpdateList.updatesLists()
//            renderer.render(zooMap)
            renderMenu()
          }
        )
        animationTimer.start()
      }

      gameLoop()
    }
  }


//  val seq = List.fill(500)(math.random)
//  val pars = List.fill(5)(List.fill(100)(math.random).par)
//
//  val start = System.nanoTime
//  seq.foreach(d => Thread.sleep(5))
//  println((System.nanoTime - start)/1e9)
//
//  val startPar = System.nanoTime
//  pars.foreach(_.filter(d => {
//    Thread.sleep(5)
//    d % 2 == 0
//  }))
//  println((System.nanoTime - startPar)/1e9)
}
