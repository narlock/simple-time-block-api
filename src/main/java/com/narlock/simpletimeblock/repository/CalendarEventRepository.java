package com.narlock.simpletimeblock.repository;

import com.narlock.simpletimeblock.model.CalendarEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Integer> {

  // 1. Create a time-blocked event: insert a new entry into the table
  CalendarEvent save(CalendarEvent event);

  // 2. Overwrite an existing time-blocked event: update all fields of a specific entry in a table
  CalendarEvent saveAndFlush(CalendarEvent event);

  // 3. Update a specific set of fields in an entry
  @Modifying
  @Query(
      "UPDATE CalendarEvent ce "
          + "SET "
          + "ce.name = CASE WHEN :name IS NOT NULL THEN :name ELSE ce.name END, "
          + "ce.note = CASE WHEN :note IS NOT NULL THEN :note ELSE ce.note END, "
          + "ce.startTime = CASE WHEN :startTime IS NOT NULL THEN :startTime ELSE ce.startTime END, "
          + "ce.endTime = CASE WHEN :endTime IS NOT NULL THEN :endTime ELSE ce.endTime END, "
          + "ce.date = CASE WHEN :date IS NOT NULL THEN :date ELSE ce.date END "
          + "WHERE ce.id = :id")
  int updateEventFields(
      @Param("id") Integer id,
      @Param("name") String name,
      @Param("note") String note,
      @Param("startTime") LocalTime startTime,
      @Param("endTime") LocalTime endTime,
      @Param("date") LocalDate date);

  // 4. Delete an existing time-blocked event by id
  void deleteById(Integer id);

  // 5. Delete all events that match a given date
  void deleteByDate(LocalDate date);

  // 6. Retrieve all events that match a given date
  List<CalendarEvent> findByDate(LocalDate date);

  // 7. Retrieve a specific time-blocked event
  Optional<CalendarEvent> findById(Integer id);
}
