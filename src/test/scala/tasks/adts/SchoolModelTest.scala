package tasks.adts

import org.junit.*
import org.junit.Assert.*
import tasks.adts.SchoolModel.*
import u03.extensionmethods.Sequences.*
import Sequence.*

class SchoolModelTest:

  // Choice of implementation to test
  val schoolADT: SchoolModule = BasicSchoolModule
  import schoolADT.*

  val school: School = emptySchool
  val john: Teacher = teacher("John")
  val math: Course = course("Math")
  val italian: Course = course("Italian")
  val school2: School = school.setTeacherToCourse(john, math)
  val school3: School = school2.setTeacherToCourse(john, italian)

  @Test def testCourses(): Unit =
    assertEquals(Cons("Math", Cons("Italian", Nil())), school3.courses)

  @Test def testTeachers(): Unit =
    assertEquals(Cons("John", Nil()), school3.teachers)