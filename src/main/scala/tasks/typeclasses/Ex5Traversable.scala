package u04lab

import u03.Sequences.* 
import Sequence.*
import u03.Optionals.*
import Optional.*

/*  Exercise 5: 
 *  - Generalise by ad-hoc polymorphism logAll, such that:
 *  -- it can be called on Sequences but also on Optional, or others... 
 *  -- it does not necessarily call log, but any function with analogous type
 *  - Hint: introduce a type class Traversable[T[_]]], capturing the ability of calling a
 *    "consumer function" on all elements (with type A) of a datastructure T[A] 
 *    Note Traversable is a 2-kinded trait (similar to Filterable, or Monad)
 *  - Write givens for Traversable[Optional] and Traversable[Sequence]
 *  - Show you can use the generalisation of logAll to:
 *  -- log all elements of an Optional, or of a Traversable
 *  -- println(_) all elements of an Optional, or of a Traversable
 */

object Ex5Traversable:

  def log[A](a: A): Unit = println("The next element is: " + a)

  def logAll[T[_]: Traversable, A](t: T[A])(f: A => Unit): Unit =
    summon[Traversable[T]].consume(t)(f)

  trait Traversable[T[_]]:
    def consume[A](t: T[A])(f: A => Unit): Unit

  given Traversable[Optional] with
    def consume[A](o: Optional[A])(f: A => Unit): Unit = o match
      case Just(a) => f(a)
      case _ => ()

  given Traversable[Sequence] with
    def consume[A](s: Sequence[A])(f: A => Unit): Unit = s match
      case Cons(h, t) => f(h); consume(t)(f)
      case _ => ()

  @main def tryTraversable =
    val o1 = Just(5)
    logAll(o1)(log)
    logAll(o1)(println)

    val o2 = Empty()
    logAll(o2)(log)
    logAll(o2)(println)

    val s1 = Cons(10, Cons(20, Cons(30, Nil())))
    logAll(s1)(log)
    logAll(s1)(println)

    val s2 = Cons("a", Cons("b", Cons("c", Nil())))
    logAll(s2)(log)
    logAll(s2)(println)