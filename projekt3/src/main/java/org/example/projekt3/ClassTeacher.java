package org.example.projekt3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClassTeacher {
    private String groupName;
    private List<Teacher> teachers;
    private int maxTeachers;

    public ClassTeacher(String groupName, int maxTeachers)
    {
        this.groupName = groupName;
        this.maxTeachers = maxTeachers;
        this.teachers = new ArrayList<>();
    }
    public int getMaxTeachers()
    {
        return maxTeachers;
    }
    public List<Teacher> getTeachers()
    {
        return teachers;
    }
    public void addTeacher(Teacher teacher)
    {
        if (teachers.size() >= maxTeachers)
        {
            System.out.println("Nie można dodać nauczyciela: osiągnięto maksymalną pojemność grupy.");
            return;
        }
        if (teachers.stream().anyMatch(t -> t.getName().equals(teacher.getName()) && t.getLastName().equals(teacher.getLastName())))
        {
            System.out.println("Nauczyciel o tym imieniu i nazwisku już istnieje w grupie.");
            return;
        }
        teachers.add(teacher);
        System.out.println("Dodano nauczyciela.");
    }
    public void addSalary(Teacher teacher, double amount)
    {
        teacher.addSalary(amount);
    }
    public void removeTeacher(Teacher teacher)
    {
        if (!teachers.remove(teacher))
        {
            System.out.println("Nauczyciel nie istnieje.");
        }
    }
    public void changeCondition(Teacher teacher, TeacherCondition condition)
    {
        teacher.setCondition(condition);
    }
    public void search(String lastName) {
        Optional<Teacher> foundTeacher = teachers.stream()
                .filter(teacher -> teacher.getLastName().equalsIgnoreCase(lastName))
                .findFirst();
        if (foundTeacher.isPresent()) {
            foundTeacher.get().printing();
        } else {
            System.out.println("Nauczyciel o podanym nazwisku nie został znaleziony.");
        }
    }
    public void searchPartial(String lastNameFragment)
    {
        boolean foundAny = false;
        if (teachers.isEmpty())
        {
            System.out.println("Lista nauczycieli jest pusta.");
            return;
        }
        for (Teacher teacher : teachers) {
            if (teacher.getLastName().toLowerCase().contains( lastNameFragment.toLowerCase()) ||
                    teacher.getLastName().toLowerCase().contains( lastNameFragment.toLowerCase())) {
                teacher.printing();
                foundAny = true;
            }
        }

        if (!foundAny) {
            System.out.println("Brak nauczycieli pasujących do podanego kryterium.");
        }
    }

    public long countByCondition(TeacherCondition condition)
    {
        return teachers.stream()
                .filter(t -> t.getCondition() == condition)
                .count();
    }
    public void summary()
    {
        System.out.println(groupName);
        for(Teacher teacher : teachers)
        {
            teacher.printing();
        }
        System.out.println("\n");
    }
    public List<Teacher> sortByName()
    {
        //zrobic z compare to
        return teachers.stream()
                .sorted(Comparator.comparing(Teacher::getName))
                .collect(Collectors.toList());
    }
    public List<Teacher> sortBySalary()
    {
        return teachers.stream()
                .sorted(Comparator.comparing(Teacher::getSalary))
                .collect(Collectors.toList());
    }
    public Optional<Teacher> max()
    {
        return teachers.stream()
                .max(Comparator.comparing(Teacher::getSalary));
    }
}
