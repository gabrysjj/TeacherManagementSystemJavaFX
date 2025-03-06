package org.example.projekt3;

public class Teacher implements ComparableTeacher{
    private String name;
    private String lastName;
    private TeacherCondition condition;
    private int yearOfBirth;
    private int salary;

    public Teacher(String name, String lastName, TeacherCondition condition, int yearOfBirth, int salary)
    {
        this.name = name;
        this.lastName = lastName;
        this.condition = condition;
        this.yearOfBirth = yearOfBirth;
        this.salary = salary;
    }
    public String getName()
    {
        return name;
    }
    public String getLastName()
    {
        return lastName;
    }
    public TeacherCondition getCondition()
    {
        return condition;
    }
    public int getYearOfBirth()
    {
        return yearOfBirth;
    }
    public int getSalary()
    {
        return salary;
    }
    public void setCondition(TeacherCondition condition)
    {
        this.condition = condition;
    }
    public void printing()
    {
        System.out.println( name + " " + lastName + ", " + condition + ", rok urodzenia: " + yearOfBirth + ", pensja: " + salary);
    }
    @Override
    public int compareTo(Teacher other)
    {
        return this.lastName.compareTo(other.lastName);
    }
    public void addSalary(double amount)
    {
        this.salary += amount;
    }
}
