IF OBJECT_ID('tennis_clubs', 'U') IS NOT NULL
DROP TABLE tennis_clubs;

CREATE TABLE tennis_clubs (
                              tennis_club_id INT PRIMARY KEY IDENTITY(1,1),
                              name VARCHAR(50) NOT NULL,
                              address VARCHAR(100) NOT NULL
);
