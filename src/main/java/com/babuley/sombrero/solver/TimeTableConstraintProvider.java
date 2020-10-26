package com.babuley.sombrero.solver;

import com.babuley.sombrero.domain.*;
import com.babuley.sombrero.domain.rooms.Room;
import com.babuley.sombrero.domain.rooms.RoomType;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

public class TimeTableConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
                // Hard constraints
                roomConflict(constraintFactory),
                teacherConflict(constraintFactory),
                studentGroupConflict(constraintFactory),
                roomCapacityConflict(constraintFactory),
                // Soft constraints
                teacherSubjectConflict(constraintFactory),
                subjectSpecialRoomConflict(constraintFactory)
        };
    }

    private Constraint roomConflict(ConstraintFactory constraintFactory) {
        // A room can accommodate at most one lesson at the same time.

        // Select a lesson ...
        return constraintFactory.from(Lesson.class)
                // ... and pair it with another lesson ...
                .join(Lesson.class,
                        // ... in the same timeslot ...
                        Joiners.equal(Lesson::getTimeslot),
                        // ... in the same room ...
                        Joiners.equal(Lesson::getRoom),
                        // ... and the pair is unique (different id, no reverse pairs)
                        Joiners.lessThan(Lesson::getId))
                // then penalize each pair with a hard weight.
                .penalize("Room conflict", HardSoftScore.ONE_HARD);
    }

    private Constraint teacherConflict(ConstraintFactory constraintFactory) {
        // A teacher can teach at most one lesson at the same time.
        return constraintFactory.from(Lesson.class)
                .join(Lesson.class,
                        Joiners.equal(Lesson::getTimeslot),
                        Joiners.equal(Lesson::getTeacher),
                        Joiners.lessThan(Lesson::getId))
                .penalize("Teacher conflict", HardSoftScore.ONE_HARD);
    }

    private Constraint studentGroupConflict(ConstraintFactory constraintFactory) {
        // A student can attend at most one lesson at the same time.
        return constraintFactory.from(Lesson.class)
                .join(Lesson.class,
                        Joiners.equal(Lesson::getTimeslot),
                        Joiners.equal(Lesson::getStudentGroup),
                        Joiners.lessThan(Lesson::getId))
                .penalize("Student group conflict", HardSoftScore.ONE_HARD);
    }

    private Constraint roomCapacityConflict(ConstraintFactory constraintFactory) {
        // A room can take only a limited size student group
        return constraintFactory.from(Lesson.class)
                .filter( (lesson)  -> {
                    Room r = lesson.getRoom();
                    StudentGroup sg = lesson.getStudentGroup();
                    return  sg.getCapacity() > r.getCapacity();
                })
               .penalize("Room capacity conflict", HardSoftScore.ONE_HARD);
    }

    private Constraint teacherSubjectConflict(ConstraintFactory constraintFactory) {
        //A teacher ought to deliver lesson's only on their subject
        return constraintFactory.from(Lesson.class)
                .filter( lesson -> {
                    Teacher t = lesson.getTeacher();
                    Subject sbj = lesson.getSubject();
                    return !sbj.getName().equalsIgnoreCase(t.getSubject().getName());
                }).penalize("Teacher subject conflict", HardSoftScore.ONE_SOFT);
    }

    private Constraint subjectSpecialRoomConflict(ConstraintFactory constraintFactory) {
        //A teacher ought to deliver lesson's only on their subject
        return constraintFactory.from(Lesson.class)
                .filter( lesson -> {
                    Room room = lesson.getRoom();
                    boolean needsLab = lesson.getSubject().needsLab();
                    return needsLab && room.getRoomType().equals(RoomType.room);
                }).penalize("Special room needed conflict", HardSoftScore.ONE_SOFT);
    }

}