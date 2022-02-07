package airportProject

import airportProject.model._
import airportProject.service._

import scala.collection.MapView
import scala.io.BufferedSource
import scala.collection.mutable
import scala.collection.parallel.CollectionConverters._
import scala.collection.parallel.ParSeq
import scala.collection.mutable.ListBuffer


//implementer la scala 3 functionality that allows dealing with null (check at compile time that there are no null)

//fuzzy matching by levenshtein distance calculator algorithm, found here https://rosettacode.org/wiki/Levenshtein_distance#Scala
//needs to understand this better, ask teacher
def levenshtein(s1: String, s2: String): Int = {
  val memoizedCosts = mutable.Map[(Int, Int), Int]()

  def lev: ((Int, Int)) => Int = { case (k1, k2) =>
    memoizedCosts.getOrElseUpdate(
      (k1, k2),
      (k1, k2) match {
        case (i, 0) => i
        case (0, j) => j
        case (i, j) =>
          //what the hell is this doing??????
          ParSeq(
            1 + lev((i - 1, j)),
            1 + lev((i, j - 1)),
            lev((i - 1, j - 1))
              + (if (s1(i - 1) != s2(j - 1)) 1 else 0)
          ).min
      }
    )
  }

  lev((s1.length, s2.length))
  //why is this needed?
  memoizedCosts(s1.length, s2.length)
}

def cleanUpLanguage(
    text: String,
    languageList: List[String] = List(
      "ook",
      "lolcode",
      "brainfuck",
      "intercal",
      "arnoldc",
      "c",
      "c++",
      "c#"
    )
): String = {
  //fuzzy matching can result in incoherences
  def recursiveMatch(
      t: String,
      list: List[String],
      acc: (String, Int) = ("", Int.MaxValue)
  ): String = list.match {
    case Nil                                   => acc._1
    case s :: tail if (levenshtein(s, t) == 0) => s
    case s :: tail if (levenshtein(s, t) < acc._2) =>
      recursiveMatch(t, tail, (s, levenshtein(s, t)))
    case _ :: tail => recursiveMatch(t, tail, acc)
  }
  recursiveMatch(text, languageList.sortBy(_.length)(Ordering[Int].reverse))
}

def languageVoting(voteList: List[(Int, String)]): List[(String, Int)] = {
  def recursiveMatch(
      vL: List[(Int, String)],
      acc: mutable.Map[String, Int] = mutable.Map[String, Int]()
  ): List[(String, Int)] = vL.match {
    case Nil => acc.toList
    case (count, language) :: tail if (acc.contains(language)) =>
      recursiveMatch(tail, acc += ((language, (acc.get(language).get + count))))
    case (count, language) :: tail =>
      recursiveMatch(tail, acc += ((language, count)))
  }
  recursiveMatch(
    voteList.map((count, language) => (count, cleanUpLanguage(language)))
  )
}

/*
  //WORK FOR TODAY, IGNORE FOR
  val givenL: List[(Int, String)] = List(
    (1, "ook!"),
    (1, "lolcode"),
    (3, "intercal"),
    (1, "ook ook"),
    (1, "brainfuck"),
    (2, "ArnoldC")
  )
  val l: List[(String, Int)] = languageVoting(givenL)
  l.foreach((l, c) => printf("language :" + l + " has :" + c + " votes."))

 */

def Initialize() = {}

def main(args: Array[String]): Unit = {
  printf("project console entry point. TEMPORARY.")

  //input user choice between query and reports

  //for now, and to go faster, main is used as manual testing, while the core app
  //isn't done, and the work on gui hasn't started

  val query_code: String = ""
  val query_name: String = ""
  //in consideration with ui, boolean indicating if code or name
  val code_or_name: Boolean = false

  val query_name_fuzzy: String = ""
 val test: VerifyIntAsBool=0
}

def QueryWork(country_code_or_name: String, code_or_name: Boolean) = {
  code_or_name.match {
    case true  => {}
    case false => {}
  }
}
