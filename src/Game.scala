import java.awt.Color
import java.awt.event.{WindowEvent, KeyEvent, KeyListener}
import javax.swing.JFrame

class Game {
  val frame = new JFrame("Scala Pong")
  var quitter = false
  var started = false
  val screenSize = 500
  val keyboard = scala.collection.mutable.Map(
    KeyEvent.VK_UP -> false,
    KeyEvent.VK_DOWN -> false,
    KeyEvent.VK_W -> false,
    KeyEvent.VK_S -> false,
    KeyEvent.VK_SPACE -> false)
  val gameObjects = Map(
    "leftPallet"->new Pallet(10, 200, 20, 100),
    "rightPallet"->new Pallet(455,200, 20, 100),
    "ball"->new Ball(250,250,10,10))
  val panel:Panel = new Panel(gameObjects)

  def init: Unit ={
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.setLocationRelativeTo(null)
    frame.setSize(screenSize, screenSize)

    panel.setFocusable(true)
    panel.addKeyListener(new KeyListener {
      override def keyTyped(e: KeyEvent): Unit = {}

      override def keyPressed(e: KeyEvent): Unit = {
        keyboard(e.getKeyCode) = true
      }

      override def keyReleased(e: KeyEvent): Unit = {
        keyboard(e.getKeyCode) = false
      }
    })

    frame.setContentPane(panel)
    frame.setVisible(true)
  }

  def start: Unit = {
    var timeBefore = System.currentTimeMillis()
    var deltaCumul = 0.
    var count = 0
    while(!quitter){
      count += 1
      val currentTime = System.currentTimeMillis()
      val delta= (currentTime - timeBefore).toDouble / 1000.
      deltaCumul += delta
      timeBefore = currentTime
      update(delta)
      draw
      if(deltaCumul > 1.){
        println(s"FPS: $count")
        deltaCumul -= 1.
        count = 0
      }
    }
    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
  }

  def update(delta:Double): Unit ={
    if(keyboard(KeyEvent.VK_UP))gameObjects("rightPallet").moveUp(delta)
    if(keyboard(KeyEvent.VK_DOWN)) gameObjects("rightPallet").moveDown(delta)
    if(keyboard(KeyEvent.VK_W)) gameObjects("leftPallet").moveUp(delta)
    if(keyboard(KeyEvent.VK_S)) gameObjects("leftPallet").moveDown(delta)

    if(keyboard(KeyEvent.VK_SPACE) || started) {
      started = true
      gameObjects("ball") match {
        case b: Ball => {
          if (b.goesDown) b.moveDown(delta)
          else b.moveUp(delta)

          if (b.goesRight) b.moveRight(delta)
          else b.moveLeft(delta)
        }
        case _ => throw new ClassCastException
      }
    }

    handleCollisions
  }

  def draw: Unit ={
    panel.repaint()
  }

  def handleCollisions: Unit ={
    val leftPallet = gameObjects("leftPallet")
    if(leftPallet.y < 0) leftPallet.y = 0
    if(leftPallet.y + leftPallet.height > panel.getSize().height) leftPallet.y = panel.getSize().height - leftPallet.height

    val rightPallet = gameObjects("rightPallet")
    if(rightPallet.y < 0)rightPallet.y = 0
    if(rightPallet.y + rightPallet.height > panel.getSize().height) rightPallet.y = panel.getSize().height - rightPallet.height

    gameObjects("ball") match{
      case b:Ball
        if b.x < leftPallet.x + leftPallet.width
          && b.y + b.height > leftPallet.y
          && b.y < leftPallet.y + leftPallet.height => b.goesRight = true
      case b:Ball
        if b.x + b.width > rightPallet.x
          && b.y + b.height > rightPallet.y
          && b.y < rightPallet.y + rightPallet.height => b.goesRight = false
      case b:Ball
        if b.y < 0 => b.goesDown = true
      case b:Ball
        if b.y + b.height > panel.getSize().height => b.goesDown = false
      case b:Ball
        if b.x < 0 => {
        println("Joueur 1 a perdu!")
        quitter = true
      }
      case b:Ball
        if b.x + b.width > panel.getSize().width => {
        println("Joueur 2 a perdu!")
        quitter = true
      }
      case b:Ball =>
      case _ => throw new ClassCastException
    }
  }
}
