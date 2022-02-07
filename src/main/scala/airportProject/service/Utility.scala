package airportProject.service

import scala.util.matching.Regex

opaque type NonEmptyString = String
object NonEmptyString:
  def orNone(s: String): Option[NonEmptyString] =
    if (s.replace(" ", "").length > 0) Some(s) else None

opaque type Code2Letters = String
object Code2Letters:
  def apply(s: String): Code2Letters = s

object StringToBoolean:
  def apply(s: String): Option[Boolean] = if (s == "1") Some(true)
  else if (s == "0") Some(false)
  else s.toBooleanOption

trait SpecificLengthString:
  enum ContentType(val regex: Regex) {
    case UpperText extends ContentType("[A-Z]+".r)
    case UpperTextAndNumbers extends ContentType("[A-Z0-9]+".r)
    case LowerText extends ContentType("[a-z]+".r)
    case LowerTextAndNumbers extends ContentType("[a-z0-9]+".r)
    case Text extends ContentType("[a-zA-Z]+".r)
    case RealText extends ContentType("[a-zA-Z0-9-/:;,.!? ]+".r)
    case TextAndNumbers extends ContentType("[a-zA-Z0-9]+".r)
    case TextWithSpecialCharacters extends ContentType("[a-zA-Z-/]+".r)
  }
  //correctLengthRange is set to the input length as default for simple regex checks
  //correctLengthRange corresponds to both minLength and maxLength
  def apply(
      s: String,
      typeOfContent: ContentType,
      correctLengthRange: (Int, Int)
  ): Option[String] =
    if (
      (s.length >= correctLengthRange._1 && s.length <= correctLengthRange._2) && typeOfContent.regex
        .matches(s)
    )
      Some(s)
    else
      None
