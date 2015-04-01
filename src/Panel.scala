import java.awt.{Color, Graphics}
import java.awt.event.{KeyEvent, KeyListener}
import javax.swing.JPanel

class Panel(var drawables:Map[String,Drawable]) extends JPanel{
  override def paintComponent(g: Graphics){
    g.setColor(new Color(200,220,200))
    g.fillRect(0,0,500,500)
    for((key,value) <- drawables){
      g.setColor(value.color)
      value.draw(g)
    }
  }
}
