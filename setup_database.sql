# USE <your_database_name>

CREATE TABLE CalendarEvent(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(250),
    note VARCHAR(250),
    start_time TIME,
    end_time TIME,
    date DATE,
    meta VARCHAR(250)
);