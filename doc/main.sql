/*
 Navicat Premium Data Transfer

 Source Server         : sqlite
 Source Server Type    : SQLite
 Source Server Version : 3017000
 Source Schema         : main

 Target Server Type    : SQLite
 Target Server Version : 3017000
 File Encoding         : 65001

 Date: 17/07/2019 20:00:30
*/

PRAGMA foreign_keys = false;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS "course";
CREATE TABLE "course" (
  "id" integer PRIMARY KEY AUTOINCREMENT,
  "course_name" VARCHAR(64) NOT NULL,
  "teacher_id" INT UNSIGNED NOT NULL
);

-- ----------------------------
-- Table structure for sqlite_sequence
-- ----------------------------
DROP TABLE IF EXISTS "sqlite_sequence";
CREATE TABLE "sqlite_sequence" (
  "name",
  "seq"
);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS "student";
CREATE TABLE "student" (
  "id" INTEGER PRIMARY KEY AUTOINCREMENT,
  "class_num" VARCHAR(5) NOT NULL,
  "student_name" VARCHAR(30) NOT NULL,
  "begin_time" DATETIME NOT NULL,
  "course_id" INT NOT NULL
);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS "teacher";
CREATE TABLE "teacher" (
  "id" INTEGER PRIMARY KEY AUTOINCREMENT,
  "name" varchar(64) NOT NULL,
  "account" varchar(64) NOT NULL,
  "password" varchar(64) NOT NULL
);

-- ----------------------------
-- Auto increment value for course
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 3 WHERE name = 'course';

-- ----------------------------
-- Auto increment value for student
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 12 WHERE name = 'student';

-- ----------------------------
-- Auto increment value for teacher
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 6 WHERE name = 'teacher';

PRAGMA foreign_keys = true;
