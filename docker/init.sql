-- Create the schema
CREATE SCHEMA IF NOT EXISTS quiz;

-- Set the search path to the quiz schema
SET search_path TO quiz;

-- Create student table
CREATE TABLE student
(
    id          SERIAL PRIMARY KEY,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by  VARCHAR(255) DEFAULT 'username'        NOT NULL,
    modified_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_by VARCHAR(255) DEFAULT 'username'        NOT NULL,
    version     INT          DEFAULT 0                 NOT NULL,
    is_deleted  BOOLEAN      DEFAULT FALSE             NOT NULL,
    first_name  VARCHAR(50)                            NOT NULL,
    last_name   VARCHAR(50)                            NOT NULL,
    number      VARCHAR(25)                            NOT NULL
);

-- Create test table
CREATE TABLE test
(
    id          SERIAL PRIMARY KEY,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by  VARCHAR(255) DEFAULT 'username'        NOT NULL,
    modified_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_by VARCHAR(255) DEFAULT 'username'        NOT NULL,
    version     INT          DEFAULT 0                 NOT NULL,
    is_deleted  BOOLEAN      DEFAULT FALSE             NOT NULL,
    name        VARCHAR(100)                           NOT NULL
);

-- Create question table
CREATE TABLE question
(
    id          SERIAL PRIMARY KEY,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by  VARCHAR(255) DEFAULT 'username'        NOT NULL,
    modified_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_by VARCHAR(255) DEFAULT 'username'        NOT NULL,
    version     INT          DEFAULT 0                 NOT NULL,
    is_deleted  BOOLEAN      DEFAULT FALSE             NOT NULL,
    content     VARCHAR(255)                           NOT NULL,
    test_id     BIGINT                                 NOT NULL,
    FOREIGN KEY (test_id) REFERENCES test (id)
);

-- Create option table
CREATE TABLE option
(
    id          SERIAL PRIMARY KEY,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by  VARCHAR(255) DEFAULT 'username'        NOT NULL,
    modified_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_by VARCHAR(255) DEFAULT 'username'        NOT NULL,
    version     INT          DEFAULT 0                 NOT NULL,
    is_deleted  BOOLEAN      DEFAULT FALSE             NOT NULL,
    content     VARCHAR(255)                           NOT NULL,
    is_correct  BOOLEAN                                NOT NULL DEFAULT FALSE,
    question_id BIGINT                                 NOT NULL,
    FOREIGN KEY (question_id) REFERENCES question (id)
);

CREATE TABLE participation
(
    id                 SERIAL PRIMARY KEY,
    created_at         TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by         VARCHAR(255) DEFAULT 'username'        NOT NULL,
    modified_at        TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    modified_by        VARCHAR(255) DEFAULT 'username'        NOT NULL,
    version            INT          DEFAULT 0                 NOT NULL,
    is_deleted         BOOLEAN      DEFAULT FALSE             NOT NULL,
    student_id         BIGINT                                 NOT NULL,
    test_id            BIGINT                                 NOT NULL,
    question_id        BIGINT                                 NOT NULL,
    selected_option_id BIGINT,
    FOREIGN KEY (student_id) REFERENCES student (id),
    FOREIGN KEY (test_id) REFERENCES test (id),
    FOREIGN KEY (question_id) REFERENCES question (id),
    FOREIGN KEY (selected_option_id) REFERENCES option (id)

);

INSERT INTO test (id, name)
VALUES (1, 'Matematik Testi'),
       (2, 'Tarih Testi'),
       (3, 'Fizik Testi'),
       (4, 'Kimya Testi'),
       (5, 'Coğrafya Testi');

INSERT INTO question (id, content, test_id)
VALUES
-- Math test questions
(1, '1 + 1 kaç eder?', 1),
(2, '2 + 3 kaç eder?', 1),
(3, '10 / 2 kaç eder?', 1),
(4, '5 * 6 kaç eder?', 1),
(5, 'Karekök(16) kaçtır?', 1),

-- History test questions
(6, 'Osmanlı Devleti hangi tarihte kuruldu?', 2),
(7, 'İstanbul hangi yılda fethedildi?', 2),
(8, 'Birinci Dünya Savaşı hangi yılda başladı?', 2),
(9, 'Cumhuriyet hangi yıl ilan edildi?', 2),
(10, 'Lozan Antlaşması hangi yılda imzalandı?', 2),

-- Physics test questions
(11, 'Işık hızı nedir?', 3),
(12, 'Yer çekimi kuvveti nedir?', 3),
(13, 'Elektrik akımının birimi nedir?', 3),
(14, 'Newtonun üçüncü yasası nedir?', 3),
(15, 'Ses hızı kaç m/s dir?', 3),

-- Chemistry test questions
(16, 'Su molekülünün kimyasal formülü nedir?', 4),
(17, 'Karbon dioksitin formülü nedir?', 4),
(18, 'Hidroklorik asidin formülü nedir?', 4),
(19, 'Amonyak kimyasal formülü nedir?', 4),
(20, 'Sodyum klorürün kimyasal formülü nedir?', 4),

-- Geography test questions
(21, 'Dünyanın en büyük okyanusu hangisidir?', 5),
(22, 'Türkiye’nin en uzun nehri hangisidir?', 5),
(23, 'Avrupa kıtasının en yüksek dağı nedir?', 5),
(24, 'İstanbul boğazı hangi iki denizi birbirine bağlar?', 5),
(25, 'Sahra çölü hangi kıtada yer alır?', 5);

INSERT INTO Option (id, content, is_correct, question_id)
VALUES
-- Math test options
(1, '1', false, 1),
(2, '2', true, 1),
(3, '3', false, 1),
(4, '4', false, 2),
(5, '5', true, 2),
(6, '6', false, 2),
(7, '3', false, 3),
(8, '5', true, 3),
(9, '6', false, 3),
(10, '25', false, 4),
(11, '30', true, 4),
(12, '35', false, 4),
(13, '2', false, 5),
(14, '4', true, 5),
(15, '8', false, 5),

-- History test options
(16, '1281', false, 6),
(17, '1299', true, 6),
(18, '1300', false, 6),
(19, '1453', true, 7),
(20, '1400', false, 7),
(21, '1500', false, 7),
(22, '1914', true, 8),
(23, '1920', false, 8),
(24, '1938', false, 8),
(25, '1920', false, 9),
(26, '1923', true, 9),
(27, '1930', false, 9),
(28, '1918', false, 10),
(29, '1923', true, 10),
(30, '1930', false, 10),

-- Physics test options
(31, '300.000 km/s', true, 11),
(32, '150.000 km/s', false, 11),
(33, '500.000 km/s', false, 11),
(34, '9.81 m/s²', true, 12),
(35, '1.5 m/s²', false, 12),
(36, '5.6 m/s²', false, 12),
(37, 'Amper', true, 13),
(38, 'Volt', false, 13),
(39, 'Watt', false, 13),
(40, 'Eylemsizlik Yasası', true, 14),
(41, 'Termodinamik Yasası', false, 14),
(42, 'Enerji Korunumu', false, 14),
(43, '343 m/s', true, 15),
(44, '200 m/s', false, 15),
(45, '500 m/s', false, 15),

-- Chemistry test options
(46, 'H2O', true, 16),
(47, 'CO2', false, 16),
(48, 'O2', false, 16),
(49, 'CO2', true, 17),
(50, 'O2', false, 17),
(51, 'H2O', false, 17),
(52, 'HCl', true, 18),
(53, 'H2SO4', false, 18),
(54, 'NaCl', false, 18),
(55, 'NH3', true, 19),
(56, 'H2O', false, 19),
(57, 'CO2', false, 19),
(58, 'NaCl', true, 20),
(59, 'KCl', false, 20),
(60, 'MgCl2', false, 20),

-- Geography test questions
(61, 'Pasifik Okyanusu', true, 21),
(62, 'Atlantik Okyanusu', false, 21),
(63, 'Hint Okyanusu', false, 21),
(64, 'Kızılırmak', true, 22),
(65, 'Fırat', false, 22),
(66, 'Dicle', false, 22),
(67, 'Mont Blanc', true, 23),
(68, 'Elbruz Dağı', false, 23),
(69, 'Alpler', false, 23),
(70, 'Karadeniz ve Marmara Denizi', true, 24),
(71, 'Karadeniz ve Akdeniz', false, 24),
(72, 'Ege ve Marmara Denizi', false, 24),
(73, 'Afrika', true, 25),
(74, 'Asya', false, 25),
(75, 'Güney Amerika', false, 25);