package com.babuley.sombrero.solver;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.babuley.sombrero.controller.TimeTableController;
import com.babuley.sombrero.domain.*;
import com.babuley.sombrero.domain.rooms.Room;
import com.babuley.sombrero.domain.rooms.RoomType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = {
        "optaplanner.solver.termination.spent-limit=1h", // Effectively disable this termination in favor of the best-score-limit
        "optaplanner.solver.termination.best-score-limit=0hard/*soft"})
public class TimeTableControllerTest {

    @Autowired
    private TimeTableController timeTableController;

    @Test
    @Timeout(600_000)
    public void solve() {
        TimeTable problem = generateProblem();
        TimeTable solution = timeTableController.solve(problem);
        assertFalse(solution.getLessonList().isEmpty());
        for (Lesson lesson : solution.getLessonList()) {
            assertNotNull(lesson.getTimeslot());
            assertNotNull(lesson.getRoom());
        }
        assertTrue(solution.getScore().isFeasible());
    }

    private TimeTable generateProblem() {
        List<Timeslot> timeslotList = new ArrayList<>();
        timeslotList.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));
        timeslotList.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(9, 30), LocalTime.of(10, 30)));
        timeslotList.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(10, 30), LocalTime.of(11, 30)));
        timeslotList.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(13, 30), LocalTime.of(14, 30)));
        timeslotList.add(new Timeslot(DayOfWeek.MONDAY, LocalTime.of(14, 30), LocalTime.of(15, 30)));

        List<Room> roomList = new ArrayList<>();
        roomList.add(new Room("Room A", 30));
        roomList.add(new Room("Lab B", 20, RoomType.lab));
        roomList.add(new Room("Room C", 30));
        roomList.add(new Room("Room D", 15));

        Subject math = new Subject("Math");
        Subject physics = new Subject("Physics", true);
        Subject geo = new Subject("Geography", true);
        Subject chemistry = new Subject("Chemistry", true);
        Subject history = new Subject("History");

        //Lang
        Subject english = new Subject("English");
        Subject spanish = new Subject("Spanish");
        Subject french = new Subject("French");

        Subject pe = new Subject("PE", true);
        Subject drawing = new Subject("Drawing", true);
        Subject music = new Subject("Music", true);
        Subject ict = new Subject("ICT", true);

        Teacher may = new Teacher(1111L,"B. May", math);
        Teacher curie = new Teacher(1112L,"M. Curie", physics);
        Teacher polo = new Teacher(1113L,"M. Polo", geo);
        Teacher jones = new Teacher(1114L,"I. Jones", english);
        Teacher cruz = new Teacher(1115L,"P. Cruz", spanish);

        StudentGroup sg1 = new StudentGroup(10001L, "Cauliflower", 10);
        StudentGroup sg2 = new StudentGroup(10001L, "Broccoli", 20);
        StudentGroup sg3 = new StudentGroup(10001L, "Beans", 25);

        List<Lesson> lessonList = new ArrayList<>();
        lessonList.add(new Lesson(101L, math, may, sg1));
        lessonList.add(new Lesson(102L, physics, curie, sg1));
        lessonList.add(new Lesson(103L, geo, polo, sg1));
        lessonList.add(new Lesson(104L, english, jones, sg1));
        lessonList.add(new Lesson(105L, spanish, cruz, sg1));

        lessonList.add(new Lesson(201L, math, may, sg2));
        lessonList.add(new Lesson(202L, chemistry, curie, sg2));
        lessonList.add(new Lesson(203L, history, jones, sg2));
        lessonList.add(new Lesson(204L, english,cruz, sg2));
        lessonList.add(new Lesson(205L, french,curie, sg2));

        lessonList.add(new Lesson(301L, ict , may, sg3));
        lessonList.add(new Lesson(302L, pe, curie, sg3));
        lessonList.add(new Lesson(303L, drawing, jones, sg3));
        lessonList.add(new Lesson(304L, music, cruz, sg3));
        lessonList.add(new Lesson(305L, pe, curie, sg3));
        return new TimeTable(timeslotList, roomList, lessonList);
    }

}