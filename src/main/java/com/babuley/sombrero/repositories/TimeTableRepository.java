package com.babuley.sombrero.repositories;

import com.babuley.sombrero.domain.Lesson;
import com.babuley.sombrero.domain.TimeTable;
import com.babuley.sombrero.domain.Timeslot;
import com.babuley.sombrero.domain.rooms.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TimeTableRepository {

    @Autowired
    private TimeslotRepository timeslotRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StudentGroupRepository studentGroupRepository;

    // There is only one time table, so there is only timeTableId (= problemId).
    public static final UUID SINGLETON_TIME_TABLE_ID = UUID.fromString("76f0e3b4-fa96-4f3e-bdd6-237ba92e3f79");

    public TimeTable findById(UUID id) {
        if (!SINGLETON_TIME_TABLE_ID.equals(id)) {
            throw new IllegalStateException("There is no timeTable with id (" + id + ").");
        }

        return new TimeTable(
                timeslotRepository.findAll(),
                roomRepository.findAll(),
                lessonRepository.findAll());
    }

    public void save(TimeTable timeTable) {
        for (Lesson lesson : timeTable.getLessonList()) {
            Lesson attached = lessonRepository.findById(lesson.getId()).get();
            attached.setTimeslot(lesson.getTimeslot());
            attached.setRoom(lesson.getRoom());
            lessonRepository.save(attached);
        }
    }

    public void saveProblem(TimeTable problem) {
        List<Timeslot> slots = problem.getTimeslotList();
        timeslotRepository.saveAll(slots);

        List<Room> rooms = problem.getRoomList();
        roomRepository.saveAll(rooms);

        List<Lesson> lessons = problem.getLessonList();
        lessons.stream().forEach(lesson -> {
            subjectRepository.save(lesson.getSubject());
        });
        lessons.stream().forEach(lesson -> {
            teacherRepository.save(lesson.getTeacher());
        });
        lessons.stream().forEach(lesson -> {
            studentGroupRepository.save(lesson.getStudentGroup());
        });
        lessonRepository.saveAll(lessons);
    }
}
