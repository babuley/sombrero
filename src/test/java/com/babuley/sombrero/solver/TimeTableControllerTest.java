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
import org.optaplanner.core.api.solver.SolverStatus;
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
    public void solve() throws InterruptedException {
        TimeTable problem = generateProblem();
        timeTableController.submitProblem(problem);
        timeTableController.solve();

        timeTableController.getSolverStatus();
        TimeTable solution = timeTableController.getTimeTable();
        while (solution.getSolverStatus() != SolverStatus.NOT_SOLVING) {
            Thread.sleep(20L);
            solution = timeTableController.getTimeTable();
        }

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

        Teacher may = new Teacher("B. May", math);
        Teacher curie = new Teacher("M. Curie", physics);
        Teacher polo = new Teacher("M. Polo", geo);
        Teacher jones = new Teacher("I. Jones", english);
        Teacher cruz = new Teacher("P. Cruz", spanish);

        StudentGroup sg1 = new StudentGroup( "Cauliflower", 10);
        StudentGroup sg2 = new StudentGroup( "Broccoli", 20);
        StudentGroup sg3 = new StudentGroup("Beans", 25);

        List<Lesson> lessonList = new ArrayList<>();
        lessonList.add(new Lesson(math, may, sg1));
        lessonList.add(new Lesson( physics, curie, sg1));
        lessonList.add(new Lesson( geo, polo, sg1));
        lessonList.add(new Lesson( english, jones, sg1));
        lessonList.add(new Lesson( spanish, cruz, sg1));

        lessonList.add(new Lesson( math, may, sg2));
        lessonList.add(new Lesson( chemistry, curie, sg2));
        lessonList.add(new Lesson( history, jones, sg2));
        lessonList.add(new Lesson(english,cruz, sg2));
        lessonList.add(new Lesson( french,curie, sg2));

        lessonList.add(new Lesson(ict , may, sg3));
        lessonList.add(new Lesson( pe, curie, sg3));
        lessonList.add(new Lesson( drawing, jones, sg3));
        lessonList.add(new Lesson( music, cruz, sg3));
        lessonList.add(new Lesson(pe, curie, sg3));
        return new TimeTable(timeslotList, roomList, lessonList);
    }

}