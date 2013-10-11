package edu.sdsmt.cs293.example7.database;

import edu.sdsmt.cs293.example7.database.Model.Course;

public interface ICourseControlListener
{
	public void selectCourse(Course course);
	public void insertCourse();
	public void insertCourse(Course course);
	public void deleteCourse(Course course);
	public void updateCourse(Course course);
}
