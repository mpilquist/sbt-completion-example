import sbt._
import sbt.complete.Parser

sealed trait Direction
case object North extends Direction
case object East extends Direction
case object South extends Direction
case object West extends Direction

case class Position(x: Int, y: Int)

sealed trait Action extends (Position => Position)
case object Stay extends Action {
  def apply(from: Position) = from
}
case class Go(direction: Direction) extends Action {
  def apply(from: Position) = direction match {
    case North => Position(from.x, from.y - 1)
    case East => Position(from.x + 1, from.y)
    case South => Position(from.x, from.y + 1)
    case West => Position(from.x - 1, from.y)
  }
}

object Main extends App {
  val parser = {
    import Parser._
    val ws = charClass(_.isWhitespace)+
    val direction =
      (token("north") ^^^ North) |
      (token("east") ^^^ East) |
      (token("south") ^^^ South) |
      (token("west") ^^^ West)
    val action =
      token("stay") ^^^ Stay |
      ((token("go") ~> (ws ~> direction)) map Go.apply)
    action
  }
  val reader = new FullReader(None, parser)
  val console = ConsoleLogger(Console.out)
  var pos = Position(0, 0)
  while (true) {
    reader.readLine("> ") foreach { line =>
      val parsed = Parser.parse(line, parser)
      parsed match {
        case Right(action) =>
          pos = action.apply(pos)
          console.out.println(pos.toString)
        case Left(e) => console.error(e)
      }
    }
  }
}
